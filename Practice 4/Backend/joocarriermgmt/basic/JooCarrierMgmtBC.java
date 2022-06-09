/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierMgmtBC.java
*@FileTitle : Joo Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.01
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.01 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.basic;

import java.util.List;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.vo.JooCarrierVO;

/**
 * ALPS-Doutraining4 Business Logic Command Interface<br>
 * - ALPS-Doutraining4에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Son Le
 * @since J2EE 1.6
 */

public interface JooCarrierMgmtBC {

	/**
	 * Search all carrier from DB
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<JooCarrierVO> searchCarrierVO(JooCarrierVO jooCarrierVO) throws EventException;
	/**
	 * Search carrier code from DB
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<JooCarrierVO> searchCrrCd(JooCarrierVO jooCarrierVO) throws EventException;
	
	/**
	 * Search Rlane code from DB
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<JooCarrierVO> searchRLaneCd(JooCarrierVO jooCarrierVO) throws EventException;
	/**
	 * Search Customer code from DB
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<JooCarrierVO> searchCustomerCode(JooCarrierVO jooCarrierVO) throws EventException;
	
	/**
	 * Modify all carrier from DB (insert, update, delete)
	 * 
	 * @param JooCarrierVO[] jooCarrierVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageCarrierVO(JooCarrierVO[] jooCarrierVO,SignOnUserAccount account) throws EventException;
}