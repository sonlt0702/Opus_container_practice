/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108HTMLAction.java
*@FileTitle : Joo Carrier
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.23
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.23 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining3.joocarrier.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierDetailVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.SearchParamsVO;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.doutraining3 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 DouTraining3SC로 실행요청<br>
 * - DouTraining3SC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author Son Le
 * @see DouTraining3Event 참조
 * @since J2EE 1.6
 */

public class ESM_DOU_0108HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * ESM_DOU_0108HTMLAction 객체를 생성
	 */
	public ESM_DOU_0108HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 DouTraining3Event로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		EsmDou0108Event event = new EsmDou0108Event();
		

		if(command.isCommand(FormCommand.SEARCH01)) {
			event.setJooCarrierVO((JooCarrierVO)getVO(request, JooCarrierVO .class, "s_"));
		} else if (command.isCommand(FormCommand.SEARCH02)) {
			event.setJooCarrierDetailVO((JooCarrierDetailVO)getVO(request, JooCarrierDetailVO .class, "s_"));
		} else if (command.isCommand(FormCommand.SEARCH03)) {
			event.setJooCarrierVO((JooCarrierVO)getVO(request, JooCarrierVO .class, "s_"));
		} else if (command.isCommand(FormCommand.SEARCH04)) {
			event.setSearchParamsVO((SearchParamsVO)getVO(request, SearchParamsVO .class, "s_"));
		} else if (command.isCommand(FormCommand.SEARCH05)) {
			event.setSearchParamsVO((SearchParamsVO)getVO(request, SearchParamsVO .class, "s_"));
		} else if (command.isCommand(FormCommand.COMMAND01)) {
			event.setJooCarrierVO((JooCarrierVO)getVO(request, JooCarrierVO .class, "s_"));
		}
		return  event;
	}

	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * HttpRequest의 attribute에 HttpRequest 파싱 수행결과 값 저장<br>
	 * HttpRequest 파싱 수행결과 값 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}