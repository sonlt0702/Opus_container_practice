package com.clt.apps.opus.esm.clv.doutraining2.codemgmt.event;

import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeDtlVO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeVO;
import com.clt.framework.support.layer.event.EventSupport;

public class DouTrn0003Event extends EventSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CodeVO codeVO =null;
	
	CodeVO[] codeVOs =null;
	
	CodeDtlVO codeDtlVO = null;
	
	CodeDtlVO[] codeDtlVOs = null;


	public DouTrn0003Event() {}

	public CodeVO getCodeVO() {
		return codeVO;
	}

	public void setCodeVO(CodeVO codeVO) {
		this.codeVO = codeVO;
	}

	public CodeVO[] getCodeVOs() {
		return codeVOs;
	}

	public void setCodeVOs(CodeVO[] codeVOs) {
		this.codeVOs = codeVOs;
	}
	public CodeDtlVO getCodeDtlVO() {
		return codeDtlVO;
	}

	public void setCodeDtlVO(CodeDtlVO codeDtlVO) {
		this.codeDtlVO = codeDtlVO;
	}

	public CodeDtlVO[] getCodeDtlVOs() {
		return codeDtlVOs;
	}

	public void setCodeDtlVOs(CodeDtlVO[] codeDtlVOs) {
		this.codeDtlVOs = codeDtlVOs;
	}
}
