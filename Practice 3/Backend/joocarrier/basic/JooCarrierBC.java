/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierBC.java
*@FileTitle : Joo Carrier
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.23
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.23 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining3.joocarrier.basic;

import java.util.List;

import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierDetailVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.SearchParamsVO;

/**
 * ALPS-Doutraining3 Business Logic Command Interface<br>
 * - ALPS-Doutraining3에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Son Le
 * @since J2EE 1.6
 */

public interface JooCarrierBC {

	/**
	 * [SearchJooCarrierSummary].<br>
	 * Search data for summary table from database
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<JooCarrierVO> searchJooCarrierSummary(JooCarrierVO jooCarrierVO) throws EventException;
	/**
	 * [searchJooCarrierDetail].<br>
	 * Search data for detail table from database
	 * 
	 * @param JooCarrierDetailVO	jooCarrierDetailVO
	 * @return List<JooCarrierDetailVO>
	 * @exception EventException
	 */
	public List<JooCarrierDetailVO> searchJooCarrierDetail(JooCarrierDetailVO jooCarrierDetailVO) throws EventException;
	/**
	 * [searchTotalSum].<br>
	 * Get total sum by Currency
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception EventException
	 */
	public List<JooCarrierVO> searchTotalSum(JooCarrierVO jooCarrierVO) throws EventException;
	/**
	 * [directDownExcel].<br>
	 * Direct down excel
	 * 
	 * @param JooCarrierVO	jooCarrierVO
	 * @return List<Object>
	 * @exception EventException
	 */
	public List<Object> directDownExcel(JooCarrierVO jooCarrierVO) throws EventException;
	/**
	 * [SearchPartner]<br>
	 * Load data Partner from database
	 * 
	 * @return List<SearchParamsVO>
	 * @exception EventException
	 */
	public List<SearchParamsVO> searchPartner() throws EventException;
	/**
	 * [SearchRlane]<br>
	 * Load data Rlane from database
	 * 
	 * @return List<SearchParamsVO>
	 * @exception EventException
	 */
	public List<SearchParamsVO> searchRlane(SearchParamsVO searchParamsVO) throws EventException;
	/**
	 * [SearchTrade]<br>
	 * Load data Trade from database
	 * 
	 * @return List<SearchParamsVO>
	 * @exception EventException
	 */
	public List<SearchParamsVO> searchTrade(SearchParamsVO searchParamsVO) throws EventException;
}