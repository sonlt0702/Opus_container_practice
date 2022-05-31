/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : EsmDou0108Event.java
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

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierDetailVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.SearchParamsVO;


/**
 * ESM_DOU_0108 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  ESM_DOU_0108HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author Son Le
 * @see ESM_DOU_0108HTMLAction 참조
 * @since J2EE 1.6
 */

public class EsmDou0108Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	JooCarrierVO jooCarrierVO = null;
	
	/** Table Value Object Multi Data 처리 */
	JooCarrierVO[] jooCarrierVOs = null;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	JooCarrierDetailVO jooCarrierDetailVO = null;
	
	/** Table Value Object Multi Data 처리 */
	JooCarrierDetailVO[] jooCarrierDetailVOs = null;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	SearchParamsVO searchParamsVO = null;
	
	/** Table Value Object Multi Data 처리 */
	SearchParamsVO[] searchParamsVOs = null;

	public EsmDou0108Event(){}
	
	public void setJooCarrierVO(JooCarrierVO jooCarrierVO){
		this. jooCarrierVO = jooCarrierVO;
	}

	public void setJooCarrierVOS(JooCarrierVO[] jooCarrierVOs){
		this. jooCarrierVOs = jooCarrierVOs;
	}

	public JooCarrierVO getJooCarrierVO(){
		return jooCarrierVO;
	}

	public JooCarrierVO[] getJooCarrierVOS(){
		return jooCarrierVOs;
	}

	public JooCarrierDetailVO getJooCarrierDetailVO() {
		return jooCarrierDetailVO;
	}

	public void setJooCarrierDetailVO(JooCarrierDetailVO jooCarrierDetailVO) {
		this.jooCarrierDetailVO = jooCarrierDetailVO;
	}

	public JooCarrierDetailVO[] getJooCarrierDetailVOs() {
		return jooCarrierDetailVOs;
	}

	public void setJooCarrierDetailVOs(JooCarrierDetailVO[] jooCarrierDetailVOs) {
		this.jooCarrierDetailVOs = jooCarrierDetailVOs;
	}

	public SearchParamsVO getSearchParamsVO() {
		return searchParamsVO;
	}

	public void setSearchParamsVO(SearchParamsVO searchParamsVO) {
		this.searchParamsVO = searchParamsVO;
	}

	public SearchParamsVO[] getSearchParamsVOs() {
		return searchParamsVOs;
	}

	public void setSearchParamsVOs(SearchParamsVO[] searchParamsVOs) {
		this.searchParamsVOs = searchParamsVOs;
	}

}