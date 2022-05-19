package com.clt.apps.opus.esm.clv.doutraining2.codemgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeDtlVO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtDtlVO;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtMstVO;

public class DOU_TRN_0003HTMLAction extends HTMLActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		// TODO Auto-generated method stub
		FormCommand command = FormCommand.fromRequest(request);
		DouTrn0003Event event = new DouTrn0003Event();
		
		if(command.isCommand(FormCommand.SEARCH01)) {
			CodeVO codeVO  = new CodeVO();
			codeVO.fromRequest(request, "s_");
			event.setCodeVO(codeVO);
		}
		else if(command.isCommand(FormCommand.SEARCH02)) {
			CodeDtlVO codeDtlVO = new CodeDtlVO();
			codeDtlVO.setIntgCdId(JSPUtil.getParameter(request, "codeID", ""));
			event.setCodeDtlVO(codeDtlVO);
		}
		else if(command.isCommand(FormCommand.MULTI01)) {
			event.setCodeVOs((CodeVO[])getVOs(request, CodeVO .class, ""));
		} else if(command.isCommand(FormCommand.MULTI02)) {
			event.setCodeDtlVOs((CodeDtlVO[])getVOs(request, CodeDtlVO .class, ""));
		}

		return  event;
	}

}
