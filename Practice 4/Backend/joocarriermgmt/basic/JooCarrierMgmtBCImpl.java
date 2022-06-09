/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierMgmtBCImpl.java
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

import java.util.ArrayList;
import java.util.List;

import com.bluecast.util.DuplicateKeyException;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.integration.JooCarrierMgmtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.vo.JooCarrierVO;

/**
 * ALPS-DouTraining4 Business Logic Command Interface<br>
 * - ALPS-DouTraining4에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Son Le
 * @since J2EE 1.6
 */
public class JooCarrierMgmtBCImpl extends BasicCommandSupport implements JooCarrierMgmtBC {

	// Database Access Object
	private transient JooCarrierMgmtDBDAO dbDao = null;

	/**
	 * JooCarrierMgmtBCImpl 객체 생성<br>
	 * JooCarrierMgmtDBDAO를 생성한다.<br>
	 */
	public JooCarrierMgmtBCImpl() {
		dbDao = new JooCarrierMgmtDBDAO();
	}
	@Override
	public List<JooCarrierVO> searchCarrierVO(JooCarrierVO jooCarrierVO) throws EventException {
		try {
			return dbDao.searchCarrierVO(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	@Override
	public void manageCarrierVO(JooCarrierVO[] jooCarrierVO, SignOnUserAccount account) throws EventException{
		String errFlg = "";
		String dupFlg = "";
		String carrier = "";
		String rlane = "";
		try {
			List<JooCarrierVO> insertVoList = new ArrayList<JooCarrierVO>();
			List<JooCarrierVO> updateVoList = new ArrayList<JooCarrierVO>();
			List<JooCarrierVO> deleteVoList = new ArrayList<JooCarrierVO>();
			for ( int i=0; i<jooCarrierVO .length; i++ ) {
				if ( jooCarrierVO[i].getIbflag().equals("I")){
					jooCarrierVO[i].setCreUsrId(account.getUsr_id());
					jooCarrierVO[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(jooCarrierVO[i]);
				} else if ( jooCarrierVO[i].getIbflag().equals("U")){
					jooCarrierVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(jooCarrierVO[i]);
				} else if ( jooCarrierVO[i].getIbflag().equals("D")){
					deleteVoList.add(jooCarrierVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				//Checking Duplication
				for( int idx=0; idx<insertVoList.size(); idx++ ){
					dupFlg = dbDao.CheckDupCrr(insertVoList.get(idx));
					if( "Y".equals(dupFlg) ){
						errFlg = "Y";
						carrier = insertVoList.get(idx).getJoCrrCd();
						rlane = insertVoList.get(idx).getRlaneCd();
						break;
					}
				}
				if( !"Y".equals(errFlg) ){
					dbDao.addManageCarrierVOS(insertVoList);
				} else{
					throw new DuplicateKeyException(new ErrorHandler("ERR00000", new String[]{carrier + " and " + rlane}).getMessage());
				}
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyManageCarrierVOS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeManageCarrierVOS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	@Override
	public List<JooCarrierVO> searchRLaneCd(JooCarrierVO jooCarrierVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchRLaneCd(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	@Override
	public List<JooCarrierVO> searchCrrCd(JooCarrierVO jooCarrierVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchCrrCd(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	@Override
	public List<JooCarrierVO> searchCustomerCode(JooCarrierVO jooCarrierVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchCustomerCode(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	
}