/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTraining3SC.java
*@FileTitle : Joo Carrier
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.23
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.23 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining3;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.basic.JooCarrierBC;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.basic.JooCarrierBCImpl;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.event.EsmDou0108Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierDetailVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.SearchParamsVO;


/**
 * ALPS-DouTraining3 Business Logic ServiceCommand - ALPS-DouTraining3 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author Son Le
 * @see JooCarrierDBDAO
 * @since J2EE 1.6
 */

public class DouTraining3SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * DouTraining3 system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("DouTraining3SC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * DouTraining3 system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("DouTraining3SC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-DouTraining3 system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("EsmDou0108Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) {
				eventResponse = searchJooCarrierSummary(e);
			} else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initDataPartner();
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchJooCarrierDetail(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH03)) {
				eventResponse = searchTotalSum(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH04)) {
				eventResponse = searchRlane(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH05)) {
				eventResponse = searchTrade(e);
			} else if (e.getFormCommand().isCommand(FormCommand.COMMAND01)) {
				eventResponse = directDownExcel(e);
			}
		}
		return eventResponse;
	}
	/**
	 * ESM_DOU_0108 : [searchJooCarrierSummary]<br>
	 * Search data for summary tab.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchJooCarrierSummary(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		JooCarrierBC command = new JooCarrierBCImpl();

		try{
			List<JooCarrierVO> list = command.searchJooCarrierSummary(event.getJooCarrierVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0108 : [searchJooCarrierDetail]<br>
	 * Search data for detail tab.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchJooCarrierDetail(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		JooCarrierBC command = new JooCarrierBCImpl();

		try{
			List<JooCarrierDetailVO> list = command.searchJooCarrierDetail(event.getJooCarrierDetailVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}

	/**
	 * ESM_DOU_0108 : [searchTotalSum]<br>
	 * Get total sum into ETC Data by Currency.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchTotalSum(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		JooCarrierBC command = new JooCarrierBCImpl();

		try{
			List<JooCarrierVO> list = command.searchTotalSum(event.getJooCarrierVO());
			eventResponse.setETCData("currNo", "" + list.size());
			for (int i = 0; i< list.size(); i++) {
				eventResponse.setETCData("curr"+i, list.get(i).getLoclCurrCd());
				eventResponse.setETCData("rev"+i, list.get(i).getInvRevActAmt());
				eventResponse.setETCData("exp"+i, list.get(i).getInvExpActAmt());
			}
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0108 : [searchTotalSum]<br>
	 * Get total sum into ETC Data by Currency.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse directDownExcel(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		JooCarrierBC command = new JooCarrierBCImpl();
		
		try{
			List<Object> list = command.directDownExcel(event.getJooCarrierVO());
			eventResponse.setCustomData("vos", list.get(0));
			eventResponse.setCustomData("fileName", "excel.xls");
			eventResponse.setCustomData("title", list.get(1));
			eventResponse.setCustomData("columns", list.get(2));
			eventResponse.setCustomData("isZip", false);
			
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0108 : [initDataPartner]<br>
	 * Load data Partner into eventResponse use ETC Data.<br>
	 * 
	 * 
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse initDataPartner() throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		JooCarrierBC command = new JooCarrierBCImpl();

		try{
			List<SearchParamsVO> list = command.searchPartner();
			StringBuilder partners = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					partners.append(list.get(i).getJoCrrCd());
					if (i < list.size() - 1){
						partners.append("|");
					}	
				}
			}
			eventResponse.setETCData("partners", partners.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0108 : [searchRlane]<br>
	 * Load data Rlane use ETC Data.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchRlane(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		JooCarrierBC command = new JooCarrierBCImpl();
		
		try{
			List<SearchParamsVO> list = command.searchRlane(event.getSearchParamsVO());
			StringBuilder rlane = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					rlane.append(list.get(i).getRlaneCd());
					if (i < list.size() - 1){
						rlane.append("|");
					}	
				}
			}
			eventResponse.setETCData("rlane", rlane.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * ESM_DOU_0108 : [searchTrade]<br>
	 * Load data Trade use ETC Data.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchTrade(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		JooCarrierBC command = new JooCarrierBCImpl();
		
		try{
			List<SearchParamsVO> list = command.searchTrade(event.getSearchParamsVO());
			StringBuilder trade = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					trade.append(list.get(i).getTrdCd());
					if (i < list.size() - 1){
						trade.append("|");
					}	
				}
			}
			eventResponse.setETCData("trade", trade.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
}