/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmt2BC.java
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

import java.util.List;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.vo.ErrMsgVO;

/**
 * ALPS-Doutraining2 Business Logic Command Interface<br>
 * - ALPS-Doutraining2에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Son Le
 * @since J2EE 1.6
 */

public interface ErrMsgMgmt2BC {

	/**
	 * Search Error Message management.<br>
	 * 
	 * @param ErrMsgVO	errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException;
	
	/**
	 * Insert, Update, Delete Error Message management<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageErrMsg(ErrMsgVO[] errMsgVO,SignOnUserAccount account) throws EventException;
}