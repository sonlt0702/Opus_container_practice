/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : EsmDou0109Event.java
*@FileTitle : Joo Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.01
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.01 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.vo.JooCarrierVO;


/**
 * ESM_DOU_0109 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  ESM_DOU_0109HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author Son Le
 * @see ESM_DOU_0109HTMLAction 참조
 * @since J2EE 1.6
 */

public class EsmDou0109Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	JooCarrierVO jooCarrierVO = null;
	
	/** Table Value Object Multi Data 처리 */
	JooCarrierVO[] jooCarrierVOs = null;

	public EsmDou0109Event(){}
	
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

}