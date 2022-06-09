/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierMgmtDBDAO.java
*@FileTitle : Joo Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.01
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.01 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.basic.JooCarrierMgmtBCImpl;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.vo.JooCarrierVO;
import com.clt.apps.opus.fns.joo.training.joocarriermgmt.integration.JooCarrierMgmtDBDAOSearchCrrCdRSQL;
import com.clt.apps.opus.fns.joo.training.joocarriermgmt.integration.JooCarrierMgmtDBDAOSearchRLaneCdRSQL;


/**
 * ALPS JooCarrierMgmtDBDAO <br>
 * - ALPS-DouTraining4 system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author Son Le
 * @see JooCarrierMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class JooCarrierMgmtDBDAO extends DBDAOSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Connect to DB to get all data carrier by condition
	 * 
	 * @param JooCarrierVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<JooCarrierVO> searchCarrierVO(JooCarrierVO jooCarrierVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<JooCarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(jooCarrierVO != null){
				Map<String, String> mapVO = jooCarrierVO .getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(null != jooCarrierVO.getJoCrrCd()){
					String[] crr_cd = jooCarrierVO.getJoCrrCd().split(",");
					for(int i = 0; i < crr_cd.length; i++){
						obj_list_no.add(crr_cd[i]);
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierMgmtDBDAOJooCarrierVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	

	/**
	 * Insert the new records into DB
	 * 
	 * @param List<JooCarrierVO> jooCarrierVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addManageCarrierVOS(List<JooCarrierVO> jooCarrierVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(jooCarrierVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new JooCarrierMgmtDBDAOJooCarrierVOCSQL(), jooCarrierVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	/**
	 * Update the records in DB
	 * 
	 * @param List<JooCarrierVO> jooCarrierVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifyManageCarrierVOS(List<JooCarrierVO> jooCarrierVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(jooCarrierVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new JooCarrierMgmtDBDAOJooCarrierVOUSQL(), jooCarrierVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to Update No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	
	/**
	 * Delete the records in DB
	 * 
	 * @param List<JooCarrierVO> jooCarrierVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removeManageCarrierVOS(List<JooCarrierVO> jooCarrierVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(jooCarrierVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new JooCarrierMgmtDBDAOJooCarrierVODSQL(), jooCarrierVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	/**
	 * Connect to DB Carrier to get RLane code
	 * 
	 * @param JooCarrierVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<JooCarrierVO> searchRLaneCd(JooCarrierVO jooCarrierVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<JooCarrierVO> list = new ArrayList<JooCarrierVO>();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierMgmtDBDAOSearchRLaneCdRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	 /**
	  * search carrier code
	  * 
	  * @param JooCarrierVO jooCarrierVO
	  * @return List<JooCarrierVO>
	  * @exception DAOException
	  */
	 @SuppressWarnings("unchecked")
	 public List<JooCarrierVO> searchCrrCd(JooCarrierVO jooCarrierVO) throws DAOException {
		 DBRowSet dbRowset = null;
		 List<JooCarrierVO> list = new ArrayList<JooCarrierVO>();
		 //query parameter
		 Map<String, Object> param = new HashMap<String, Object>();
		 //velocity parameter
		 Map<String, Object> velParam = new HashMap<String, Object>();
		 
		 try{
			 if(jooCarrierVO != null){
				Map<String, String> mapVO = jooCarrierVO .getColumnValues();
				param.putAll(mapVO);
				
				velParam.putAll(mapVO);
			 }
			 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierMgmtDBDAOSearchCrrCdRSQL(), param, velParam);
			 list = (List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierVO .class);
		 } catch(SQLException se) {
			 log.error(se.getMessage(),se);
			 throw new DAOException(new ErrorHandler(se).getMessage());
		 } catch(Exception ex) {
			 log.error(ex.getMessage(),ex);
			 throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	 }
	 /**
	  * search customer code
	  * 
	  * @param JooCarrierVO jooCarrierVO
	  * @return List<JooCarrierVO>
	  * @exception DAOException
	  */
	 @SuppressWarnings("unchecked")
	 public List<JooCarrierVO> searchCustomerCode(JooCarrierVO jooCarrierVO) throws DAOException {
		 DBRowSet dbRowset = null;
		 List<JooCarrierVO> list = new ArrayList<JooCarrierVO>();
		 //query parameter
		 Map<String, Object> param = new HashMap<String, Object>();
		 //velocity parameter
		 Map<String, Object> velParam = new HashMap<String, Object>();
		 
		 try{
			 if(jooCarrierVO != null){
				 Map<String, String> mapVO = jooCarrierVO .getColumnValues();
				 param.putAll(mapVO);
				 
				 velParam.putAll(mapVO);
			 }
			 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierMgmtDBDAOSearchCustomerCodeRSQL(), param, velParam);
			 list = (List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierVO .class);
		 } catch(SQLException se) {
			 log.error(se.getMessage(),se);
			 throw new DAOException(new ErrorHandler(se).getMessage());
		 } catch(Exception ex) {
			 log.error(ex.getMessage(),ex);
			 throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	 }
	 /**
		 * Check duplicate carrier code when insert
		 * 
		 * @param jooCarrierVO
		 * @return Duplicate Flag (Y || N)
		 * @throws DAOException
		 */
		public String CheckDupCrr(JooCarrierVO jooCarrierVO) throws DAOException {
			DBRowSet dbRowset = null;
			String dupFlg = "";
			//query parameter
			Map<String, Object> param = new HashMap<String, Object>();
			//velocity parameter
			Map<String, Object> velParam = new HashMap<String, Object>();
			
			try{
				if( jooCarrierVO != null ){
					Map<String, String> mapVO = jooCarrierVO .getColumnValues();
					 
					param.putAll(mapVO);
					velParam.putAll(mapVO);
				}
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierMgmtDBDAOChkDupCrrRSQL(), param, velParam);
				if ( dbRowset!=null && dbRowset.next() ){
					dupFlg = dbRowset.getString(1);
				}
			} catch(SQLException se) {
				log.error(se.getMessage(),se);
				throw new DAOException(new ErrorHandler(se).getMessage());
			} catch(Exception ex) {
				log.error(ex.getMessage(),ex);
				throw new DAOException(new ErrorHandler(ex).getMessage());
			}
			
			return dupFlg;
		}
}