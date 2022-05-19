package com.clt.apps.opus.esm.clv.doutraining2.codemgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeDtlVO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtDtlVO;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtMstVO;

public interface CodeMgmtBC {

	/**
	 * Search Code Management master
	 * 
	 * @param codeVO
	 * @return List<CodeVO>
	 * @throws EventException
	 */
	public List<CodeVO> searchCodeMgmt(CodeVO codeVO) throws EventException;
	
	/**
	 * Search Code Management detail
	 * 
	 * @param codeDtlVO
	 * @return List<CodeDtlVO>
	 * @throws EventException
	 */
	public List<CodeDtlVO> searchCodeMgmtDtl(CodeDtlVO codeDtlVO) throws EventException;

	/**
	 *
	 * Modify Code Management master(insert, update, delete) 
	 * 
	 * @param CodeVO[] codeVOs
	 * @param SignOnUserAccount a
	 * @exception EventException
	 */
	public void multiCodeMgmtMst(CodeVO[] codeVOs, SignOnUserAccount a) throws EventException;
	
	/**
	 * Modify Code Management master(insert, update, delete)
	 * 
	 * @param CodeDtlVO[] codeDtlVOs
	 * @param SignOnUserAccount a
	 * @exception EventException
	 */
	public void multiCodeMgmtDtl(CodeDtlVO[] codeDtlVOs, SignOnUserAccount a) throws EventException;
	
}
