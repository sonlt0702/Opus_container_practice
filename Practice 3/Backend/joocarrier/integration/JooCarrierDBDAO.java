/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierDBDAO.java
*@FileTitle : Joo Carrier
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.23
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.23 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining3.joocarrier.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.basic.JooCarrierBCImpl;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierDetailVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.JooCarrierVO;
import com.clt.apps.opus.esm.clv.doutraining3.joocarrier.vo.SearchParamsVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;


/**
 * ALPS JooCarrierDBDAO <br>
 * - ALPS-DouTraining3 system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author Son Le
 * @see JooCarrierBCImpl 참조
 * @since J2EE 1.6
 */
public class JooCarrierDBDAO extends DBDAOSupport {

	/**
	 * [searchJooCarrier]<br>
	 * Connect to database to search data summary
	 * 
	 * @param JooCarrierVO jooCarrierVO
	 * @return List<JooCarrierVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<JooCarrierVO> searchJooCarrier(JooCarrierVO jooCarrierVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<JooCarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(jooCarrierVO != null){
				Map<String, String> mapVO = jooCarrierVO .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
				// get parameter Partner and compile to List
				List<String> listPartners = new ArrayList<>();
				if (!jooCarrierVO.getJoCrrCd().equals("ALL") && jooCarrierVO.getJoCrrCd() != "") {
					String[] joCrrCds = jooCarrierVO.getJoCrrCd().split(",");
					for (int i = 0; i< joCrrCds.length; i++) {
						listPartners.add(joCrrCds[i]);
					}
					param.put("jo_crr_cds", listPartners);
					velParam.put("jo_crr_cds", listPartners);
				} else {
					param.put("jo_crr_cds", "ALL");
					velParam.put("jo_crr_cds", "ALL");
				}
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOJooCarrierVORSQL(), param, velParam);
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
		 * [searchJooCarrierDetail]<br>
		 * Connect to database to search data datail
		 * 
		 * @param JooCarrierDetailVO jooCarrierDetailVO
		 * @return List<JooCarrierDetailVO>
		 * @exception DAOException
		 */
		 @SuppressWarnings("unchecked")
		public List<JooCarrierDetailVO> searchJooCarrierDetail(JooCarrierDetailVO jooCarrierDetailVO) throws DAOException {
			DBRowSet dbRowset = null;
			List<JooCarrierDetailVO> list = null;
			//query parameter
			Map<String, Object> param = new HashMap<String, Object>();
			//velocity parameter
			Map<String, Object> velParam = new HashMap<String, Object>();

			try{
				if(jooCarrierDetailVO != null){
					Map<String, String> mapVO = jooCarrierDetailVO .getColumnValues();
				
					param.putAll(mapVO);
					velParam.putAll(mapVO);
					// get parameter Partner and compile to List
					List<String> listPartners = new ArrayList<>();
					if (!jooCarrierDetailVO.getJoCrrCd().equals("ALL") && jooCarrierDetailVO.getJoCrrCd() != "") {
						String[] joCrrCds = jooCarrierDetailVO.getJoCrrCd().split(",");
						for (int i = 0; i< joCrrCds.length; i++) {
							listPartners.add(joCrrCds[i]);
						}
						param.put("jo_crr_cds", listPartners);
						velParam.put("jo_crr_cds", listPartners);
					} else {
						param.put("jo_crr_cds", "ALL");
						velParam.put("jo_crr_cds", "ALL");
					}
				}
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOJooCarrierDetailVORSQL(), param, velParam);
				list = (List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierDetailVO .class);
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
			 * [searchTotalSum]<br>
			 * Connect to database to get total sum by currency
			 * 
			 * @param JooCarrierVO jooCarrierVO
			 * @return List<JooCarrierVO>
			 * @exception DAOException
			 */
			 @SuppressWarnings("unchecked")
			public List<JooCarrierVO> searchTotalSum(JooCarrierVO jooCarrierVO) throws DAOException {
				DBRowSet dbRowset = null;
				List<JooCarrierVO> list = null;
				//query parameter
				Map<String, Object> param = new HashMap<String, Object>();
				//velocity parameter
				Map<String, Object> velParam = new HashMap<String, Object>();

				try{
					if(jooCarrierVO != null){
						Map<String, String> mapVO = jooCarrierVO .getColumnValues();
					
						param.putAll(mapVO);
						velParam.putAll(mapVO);
						// get parameter Partner and compile to List
						List<String> listPartners = new ArrayList<>();
						if (!jooCarrierVO.getJoCrrCd().equals("ALL") && jooCarrierVO.getJoCrrCd() != "") {
							String[] joCrrCds = jooCarrierVO.getJoCrrCd().split(",");
							for (int i = 0; i< joCrrCds.length; i++) {
								listPartners.add(joCrrCds[i]);
							}
							param.put("jo_crr_cds", listPartners);
							velParam.put("jo_crr_cds", listPartners);
						} else {
							param.put("jo_crr_cds", "ALL");
							velParam.put("jo_crr_cds", "ALL");
						}
					}
					dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOJooCarrierTotalSumRSQL(), param, velParam);
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
			  * [searchPartner]<br>
			  * Connect to DB to get data Partner
			  *
			  * @return List<JooCarrierVO>
			  * @exception DAOException
			  */
			 @SuppressWarnings("unchecked")
			 public List<SearchParamsVO> searchPartner() throws DAOException {
				 DBRowSet dbRowset = null;
				 List<SearchParamsVO> list = null;
				 try{
					 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOSearchPartnerRSQL(), null, null);
					 list = (List)RowSetUtil.rowSetToVOs(dbRowset, SearchParamsVO .class);
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
			  * [searchRlane]<br>
			  * Connect to DB to get data Rlane
			  * 
			  * @param SearchParamsVO searchParamsVO
			  * @return List<SearchParamsVO>
			  * @exception DAOException
			  */
			 @SuppressWarnings("unchecked")
			 public List<SearchParamsVO> searchRlane(SearchParamsVO searchParamsVO) throws DAOException {
				 DBRowSet dbRowset = null;
				 List<SearchParamsVO> list = null;
				 //query parameter
				 Map<String, Object> param = new HashMap<String, Object>();
				 //velocity parameter
				 Map<String, Object> velParam = new HashMap<String, Object>();
				 try{
					 if(searchParamsVO != null){
						// get parameter Partner and compile to List
							List<String> listPartners = new ArrayList<>();
							if (!searchParamsVO.getJoCrrCd().equals("ALL")) {
								String[] joCrrCds = searchParamsVO.getJoCrrCd().split(",");
								for (int i = 0; i< joCrrCds.length; i++) {
									listPartners.add(joCrrCds[i]);
								}
								param.put("jo_crr_cds", listPartners);
								velParam.put("jo_crr_cds", listPartners);
							} else {
								param.put("jo_crr_cds", "ALL");
								velParam.put("jo_crr_cds", "ALL");
							}
						}
					 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOSearchRlaneRSQL(), param, velParam);
					 list = (List)RowSetUtil.rowSetToVOs(dbRowset, SearchParamsVO .class);
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
			  * [searchTrade]<br>
			  * Connect to DB to get data Trade
			  * 
			  * @param SearchParamsVO searchParamsVO
			  * @return List<SearchParamsVO>
			  * @exception DAOException
			  */
			 @SuppressWarnings("unchecked")
			 public List<SearchParamsVO> searchTrade(SearchParamsVO searchParamsVO) throws DAOException {
				 DBRowSet dbRowset = null;
				 List<SearchParamsVO> list = null;
				 //query parameter
				 Map<String, Object> param = new HashMap<String, Object>();
				 //velocity parameter
				 Map<String, Object> velParam = new HashMap<String, Object>();
				 try{
					 if(searchParamsVO != null){
						// get parameter Partner and compile to List
						 List<String> listPartners = new ArrayList<>();
						 if (!searchParamsVO.getJoCrrCd().equals("ALL")) {
							 String[] joCrrCds = searchParamsVO.getJoCrrCd().split(",");
							 for (int i = 0; i< joCrrCds.length; i++) {
								 listPartners.add(joCrrCds[i]);
							 }
							 param.put("jo_crr_cds", listPartners);
							 velParam.put("jo_crr_cds", listPartners);
						 } else {
							 param.put("jo_crr_cds", "ALL");
							 velParam.put("jo_crr_cds", "ALL");
						 }
						 Map<String, String> mapVO = searchParamsVO .getColumnValues();
							
						 param.putAll(mapVO);
						 velParam.putAll(mapVO);
					 }
					 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOSearchTradeRSQL(), param, velParam);
					 list = (List)RowSetUtil.rowSetToVOs(dbRowset, SearchParamsVO .class);
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
			  * [searchTrade]<br>
			  * Connect to DB to get data Trade
			  * 
			  * @param SearchParamsVO searchParamsVO
			  * @return List<SearchParamsVO>
			  * @exception DAOException
			  */
			 @SuppressWarnings({ "unchecked" })
			 public List<Object> directDownExcel(JooCarrierVO jooCarrierVO) throws DAOException {
				 DBRowSet dbRowset = null;
				 List<Object> list = new ArrayList<>();
				 //query parameter
				 Map<String, Object> param = new HashMap<String, Object>();
				 //velocity parameter
				 Map<String, Object> velParam = new HashMap<String, Object>();
				 try{
					 if(jooCarrierVO != null){
						// get parameter Partner and compile to List
							List<String> listPartners = new ArrayList<>();
							if (!jooCarrierVO.getJoCrrCd().equals("ALL") && jooCarrierVO.getJoCrrCd() != "") {
								String[] joCrrCds = jooCarrierVO.getJoCrrCd().split(",");
								for (int i = 0; i< joCrrCds.length; i++) {
									listPartners.add(joCrrCds[i]);
								}
								param.put("jo_crr_cds", listPartners);
								velParam.put("jo_crr_cds", listPartners);
							} else {
								param.put("jo_crr_cds", "ALL");
								velParam.put("jo_crr_cds", "ALL");
							}
						 Map<String, String> mapVO = jooCarrierVO .getColumnValues();
						 
						 param.putAll(mapVO);
						 velParam.putAll(mapVO);
					 }
					 if ("t1sheet1".equals(jooCarrierVO.getSheetId())) {
						 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOJooCarrierVORSQL(), param, velParam);
						 list.add((List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierVO .class));
					 } else {
						 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new JooCarrierDBDAOJooCarrierDetailVORSQL(), param, velParam);
						 list.add((List)RowSetUtil.rowSetToVOs(dbRowset, JooCarrierDetailVO .class));
					 }
					 int len = dbRowset.getMetaData().getColumnCount();
					 String[] titles = new String[len];
					 String[] columns = new String[len];
					 for (int i=1;i<=len;i++) {
						 titles[i-1] = dbRowset.getMetaData().getColumnName(i);
						 columns[i-1] = dbRowset.getMetaData().getColumnName(i).toLowerCase();
					 }
					 list.add(titles);
					 list.add(columns);
				 } catch(SQLException se) {
					 log.error(se.getMessage(),se);
					 throw new DAOException(new ErrorHandler(se).getMessage());
				 } catch(Exception ex) {
					 log.error(ex.getMessage(),ex);
					 throw new DAOException(new ErrorHandler(ex).getMessage());
				 }
				 return list;
			 }
}