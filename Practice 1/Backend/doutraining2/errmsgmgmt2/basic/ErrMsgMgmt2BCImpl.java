/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmt2BCImpl.java
*@FileTitle : Error Message Management2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.basic;

import java.util.ArrayList;
import java.util.List;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.integration.ErrMsgMgmt2DBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.vo.ErrMsgVO;

/**
 * ALPS-douTraining2 Business Logic Command Interface<br>
 * - ALPS-douTraining2에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Son Le
 * @since J2EE 1.6
 */
public class ErrMsgMgmt2BCImpl extends BasicCommandSupport implements ErrMsgMgmt2BC {

	// Database Access Object
	private transient ErrMsgMgmt2DBDAO dbDao = null;

	/**
	 * ErrMsgMgmt2BCImpl 객체 생성<br>
	 * ErrMsgMgmt2DBDAO를 생성한다.<br>
	 */
	public ErrMsgMgmt2BCImpl() {
		dbDao = new ErrMsgMgmt2DBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchErrMsg(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageErrMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			for ( int i=0; i<errMsgVO .length; i++ ) {
				if ( errMsgVO[i].getIbflag().equals("I")){
					errMsgVO[i].setCreUsrId(account.getUsr_id());
					insertVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("U")){
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("D")){
					deleteVoList.add(errMsgVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageErrMsgS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageErrMsgS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageErrMsgS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
}