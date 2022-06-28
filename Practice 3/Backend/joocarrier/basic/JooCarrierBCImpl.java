/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierBCImpl.java
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


import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.integration.JooCarrierDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierDetailVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.SearchParamsVO;

/**
 * ALPS-DouTraining3 Business Logic Command Interface<br>
 * - ALPS-DouTraining3에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Son Le
 * @since J2EE 1.6
 */
public class JooCarrierBCImpl extends BasicCommandSupport implements JooCarrierBC {
	// Database Access Object
	private transient JooCarrierDBDAO dbDao = null;

	/**
	 * JooCarrierBCImpl 객체 생성<br>
	 * JooCarrierDBDAO를 생성한다.<br>
	 */
	public JooCarrierBCImpl() {
		dbDao = new JooCarrierDBDAO();
	}
	
	@Override
	public List<JooCarrierVO> searchJooCarrierSummary(JooCarrierVO jooCarrierVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchJooCarrier(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	@Override
	public List<JooCarrierDetailVO> searchJooCarrierDetail(JooCarrierDetailVO jooCarrierDetailVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchJooCarrierDetail(jooCarrierDetailVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<JooCarrierVO> searchTotalSum(JooCarrierVO jooCarrierVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchTotalSum(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<SearchParamsVO> searchPartner() throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchPartner();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<SearchParamsVO> searchRlane(SearchParamsVO searchParamsVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchRlane(searchParamsVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<SearchParamsVO> searchTrade(SearchParamsVO searchParamsVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.searchTrade(searchParamsVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<Object> directDownExcel(JooCarrierVO jooCarrierVO)
			throws EventException {
		// TODO Auto-generated method stub
		try {
			return dbDao.directDownExcel(jooCarrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
}