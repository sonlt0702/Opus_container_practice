package com.clt.apps.opus.esm.clv.doutraining2.codemgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.bluecast.util.DuplicateKeyException;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeDtlVO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtDtlVO;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtMstVO;

public class CodeMgmtBCImpl extends BasicCommandSupport implements CodeMgmtBC {
	


	// Database Access Object
	private transient CodeMgmtDBDAO dbDao = null;
	
	/**
	 * CodeMgmtBCImpl 객체 생성<br>
	 * CodeMgmtDBDAO를 생성한다.<br>
	 */
	public CodeMgmtBCImpl() {
		dbDao = new CodeMgmtDBDAO();
	}

	@Override
	public List<CodeVO> searchCodeMgmt(CodeVO codeVO) throws EventException {
		try {
			return dbDao.searchCodeMgmt(codeVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}

	@Override
	public List<CodeDtlVO> searchCodeMgmtDtl(CodeDtlVO codeDtlVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchCodeMgmtDtl(codeDtlVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	@Override
	public void multiCodeMgmtMst(CodeVO[] codeVOs, SignOnUserAccount account)
			throws EventException {
		// TODO Auto-generated method stub	
		String errFlg = "";
		String dupFlg = "";
		try {
			List<CodeVO> insertVoList = new ArrayList<CodeVO>();
			List<CodeVO> updateVoList = new ArrayList<CodeVO>();
			List<CodeVO> deleteVoList = new ArrayList<CodeVO>();

			for ( int i=0; i<codeVOs .length; i++ ) {
				if ( codeVOs[i].getIbflag().equals("I")){
					codeVOs[i].setCreUsrId(account.getUsr_id());
					codeVOs[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(codeVOs[i]);
				} else if ( codeVOs[i].getIbflag().equals("U")){
					codeVOs[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(codeVOs[i]);
				} else if ( codeVOs[i].getIbflag().equals("D")){
					deleteVoList.add(codeVOs[i]);
				}				
			} 

			if ( insertVoList.size() > 0 ) {
				//Checking Duplication
				for( int idx=0; idx<insertVoList.size(); idx++ ){
					dupFlg = dbDao.CheckDplCodeVO(insertVoList.get(idx));
					if( "Y".equals(dupFlg) ){
						errFlg = "Y";
					}
				}
				if( !"Y".equals(errFlg) ){
					dbDao.addCodeVO(insertVoList);
				} else{
					throw new DuplicateKeyException(new ErrorHandler("COM12115",new String[]{"Master Code"}).getMessage());
				}
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyCodeMgmt(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeCodeMgmt(deleteVoList);
			}
			
		} catch(DuplicateKeyException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(new ErrorHandler("COM12115",new String[]{"Master Code"}).getMessage());
		} catch(DAOException ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		} catch(Exception ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		}
	}

	@Override
	public void multiCodeMgmtDtl(CodeDtlVO[] codeDtlVOs, SignOnUserAccount account)
			throws EventException {
		// TODO Auto-generated method stub
		String errFlg = "";
		String dupFlg = "";
		try {
			List<CodeDtlVO> insertVoList = new ArrayList<CodeDtlVO>();
			List<CodeDtlVO> updateVoList = new ArrayList<CodeDtlVO>();
			List<CodeDtlVO> deleteVoList = new ArrayList<CodeDtlVO>();

			for ( int i=0; i<codeDtlVOs .length; i++ ) {
				if ( codeDtlVOs[i].getIbflag().equals("I")){
					codeDtlVOs[i].setCreUsrId(account.getUsr_id());
					codeDtlVOs[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(codeDtlVOs[i]);
				} else if ( codeDtlVOs[i].getIbflag().equals("U")){
					codeDtlVOs[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(codeDtlVOs[i]);
				} else if ( codeDtlVOs[i].getIbflag().equals("D")){
					deleteVoList.add(codeDtlVOs[i]);
				}				
			} 

			if ( insertVoList.size() > 0 ) {
				//Checking Duplication
				for( int idx=0; idx<insertVoList.size(); idx++ ){
					dupFlg = dbDao.CheckDplCodeDtlVO(insertVoList.get(idx));
					if( "Y".equals(dupFlg) ){
						errFlg = "Y";
					}
				}
				if( !"Y".equals(errFlg) ){
					dbDao.addCodeDtlVO(insertVoList);
				} else{
					throw new DuplicateKeyException(new ErrorHandler("COM12115",new String[]{"Detail Code"}).getMessage());
				}
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyCodeMgmtDtl(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeCodeMgmtDtl(deleteVoList);
			}
			
		} catch(DuplicateKeyException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(new ErrorHandler("COM12115",new String[]{"Detail Code"}).getMessage());
		} catch(DAOException ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		} catch(Exception ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		}
	}

}
