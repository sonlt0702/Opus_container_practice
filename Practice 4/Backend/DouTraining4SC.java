/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTraining4SC.java
*@FileTitle : Joo Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.01
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.01 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining4;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.basic.JooCarrierMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.basic.JooCarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.event.CustomerPopupEvent;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.event.EsmDou0109Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.vo.JooCarrierVO;


/**
 * ALPS-DouTraining4 Business Logic ServiceCommand - ALPS-DouTraining4 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author Son Le
 * @see JooCarrierMgmtDBDAO
 * @since J2EE 1.6
 */

public class DouTraining4SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * DouTraining4 system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("DouTraining4SC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * DouTraining4 system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("DouTraining4SC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-DouTraining4 system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("EsmDou0109Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCarrierVO(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageCarrierVO(e);
			} else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initData(e);
			}
		}  else if (e.getEventName().equalsIgnoreCase("CustomerPopupEvent")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCustomerCode(e);
			}
		}
		return eventResponse;
	}
	/**
	 * ESM_DOU_0109 : [searchCarrierVO]<br>
	 * Search list data on Grid
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCarrierVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0109Event event = (EsmDou0109Event)e;
		JooCarrierMgmtBC command = new JooCarrierMgmtBCImpl();

		try{
			List<JooCarrierVO> list = command.searchCarrierVO(event.getJooCarrierVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0109 : [searchCustomerCode]<br>
	 * Search list Customer code on Popup Customer
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCustomerCode(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		CustomerPopupEvent event = (CustomerPopupEvent)e;
		JooCarrierMgmtBC command = new JooCarrierMgmtBCImpl();
		
		try{
			List<JooCarrierVO> list = command.searchCustomerCode(event.getJooCarrierVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0109 : [manageCarrierVO]<br>
	 * Modify data (insert, update, delete)
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageCarrierVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0109Event event = (EsmDou0109Event)e;
		JooCarrierMgmtBC command = new JooCarrierMgmtBCImpl();
		try{
			begin();
			command.manageCarrierVO(event.getJooCarrierVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
	/**
	 * This method for initial data
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse initData(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0109Event event = (EsmDou0109Event)e;
		JooCarrierMgmtBC command = new JooCarrierMgmtBCImpl();

		try{
			List<JooCarrierVO> rlaneCds = command.searchRLaneCd(event.getJooCarrierVO());
			StringBuilder rlaneCdsBuilder = new StringBuilder();
			if(null != rlaneCds && rlaneCds.size() > 0){
				for(int i =0; i < rlaneCds.size(); i++){
					rlaneCdsBuilder.append(rlaneCds.get(i).getRlaneCd());
					if (i < rlaneCds.size() - 1){
						rlaneCdsBuilder.append("|");
					}	
				}
			}
			List<JooCarrierVO> crrCds = command.searchCrrCd(event.getJooCarrierVO());
			StringBuilder crrCdsBuilder = new StringBuilder();
			if(null != crrCds && crrCds.size() > 0){
				for(int i =0; i < crrCds.size(); i++){
					crrCdsBuilder.append(crrCds.get(i).getJoCrrCd());
					if (i < crrCds.size() - 1){
						crrCdsBuilder.append("|");
					}	
				}
			}
			eventResponse.setETCData("rlaneCds", rlaneCdsBuilder.toString());
			eventResponse.setETCData("crrCds", crrCdsBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
}