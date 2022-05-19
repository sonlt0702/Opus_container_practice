/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : douTraining2SC.java
*@FileTitle : Error Message Management2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining2;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.basic.CodeMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.event.DouTrn0003Event;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeDtlVO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeVO;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.basic.ErrMsgMgmt2BC;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.basic.ErrMsgMgmt2BCImpl;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.event.DouTrn0002Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.vo.ErrMsgVO;


/**
 * ALPS-douTraining2 Business Logic ServiceCommand - ALPS-douTraining2 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author Son Le
 * @see ErrMsgMgmt2DBDAO
 * @since J2EE 1.6
 */

public class douTraining2SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * douTraining2 system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("douTraining2SC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * douTraining2 system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("douTraining2SC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-douTraining2 system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC handle events
		if (e.getEventName().equalsIgnoreCase("DouTrn0002Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchErrMsg(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageErrMsg(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND01)) {
				eventResponse = checkErrMsg(e);
			}
		}
		// SC handle events
		if (e.getEventName().equalsIgnoreCase("DouTrn0003Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) {
				eventResponse = searchCodeMgmt(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchCodeMgmtDtl(e);
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI01)) {
				eventResponse = manageCodeMgmt(e);
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI02)) {
				eventResponse = manageCodeMgmt(e);
			}
		}
		return eventResponse;
	}
	/**
	 * DOU_TRN_0002 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		ErrMsgMgmt2BC command = new ErrMsgMgmt2BCImpl();

		try{
			List<ErrMsgVO> list = command.searchErrMsg(event.getErrMsgVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * DOU_TRN_0002 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse checkErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		ErrMsgMgmt2BC command = new ErrMsgMgmt2BCImpl();

		try{
			List<ErrMsgVO> list = command.searchErrMsg(event.getErrMsgVO());
			if (list.size()>0) {
				eventResponse.setETCData("duplicate", "true");
			} else {
				eventResponse.setETCData("duplicate", "false");
			}
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * DOU_TRN_0002 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		ErrMsgMgmt2BC command = new ErrMsgMgmt2BCImpl();
		try{
			begin();
			command.manageErrMsg(event.getErrMsgVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("COM12191").getUserMessage());
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
	 * DOU_TRN_0003 : Code Management<br>
	 * Search data in code management master<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCodeMgmt(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event)e;
		CodeMgmtBCImpl command = new CodeMgmtBCImpl();

		try{
			List<CodeVO> list = command.searchCodeMgmt(event.getCodeVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * DOU_TRN_0003 : Code Management<br>
	 * Search data in code management detail<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCodeMgmtDtl(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event)e;
		CodeMgmtBCImpl command = new CodeMgmtBCImpl();

		try{
			List<CodeDtlVO> list = command.searchCodeMgmtDtl(event.getCodeDtlVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * DOU_TRN_0003 : Code Management<br>
	 * Handle CUD in database Code management<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */ 
	private EventResponse manageCodeMgmt(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event)e;
		CodeMgmtBCImpl command = new CodeMgmtBCImpl();
		try{
			begin();
			if(event.getCodeVOs()!=null){
				command.multiCodeMgmtMst(event.getCodeVOs(), account);
			}
			if(event.getCodeDtlVOs()!=null){
				command.multiCodeMgmtDtl(event.getCodeDtlVOs(), account);
			}
			eventResponse.setUserMessage(new ErrorHandler("COM12191").getUserMessage());
			commit();
		} catch (EventException ex) {
        	log.error("error:"+ex.toString(), ex);
            rollback();
            throw new EventException(ex.getMessage(), ex);
        } catch (Exception ex) {
        	log.error("error:"+ex.toString(), ex);
            rollback();
            throw new EventException(new ErrorHandler("COM12192", new String[]{"Data"}).getMessage(), ex);
        }
		return eventResponse;
	}
}