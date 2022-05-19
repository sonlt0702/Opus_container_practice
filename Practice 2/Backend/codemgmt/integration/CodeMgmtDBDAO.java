package com.clt.apps.opus.esm.clv.doutraining2.codemgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeDtlVO;
import com.clt.apps.opus.esm.clv.doutraining2.codemgmt.vo.CodeVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.syscommon.management.opus.codemanagement.integration.CodeManagementDBDAOComIntgCdCSQL;
import com.clt.syscommon.management.opus.codemanagement.integration.CodeManagementDBDAOComIntgCdDSQL;
import com.clt.syscommon.management.opus.codemanagement.integration.CodeManagementDBDAOComIntgCdDtlDSQL;
import com.clt.syscommon.management.opus.codemanagement.integration.CodeManagementDBDAOmodifyCodeMgmtMstUSQL;
import com.clt.syscommon.management.opus.codemanagement.integration.CodeManagementDBDAOsearchDupChkCodeMgmtMstRSQL;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtMstVO;


public class CodeMgmtDBDAO extends DBDAOSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Search data from Code management master.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<CodeVO> searchCodeMgmt(CodeVO codeVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<CodeVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(codeVO != null){
				Map<String, String> mapVO = codeVO .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAOCodeVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CodeVO .class);
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
		 * Search data from Code management detail.<br>
		 * 
		 * @param CodeDtlVO codeDtlVO
		 * @return List<CodeDtlVO>
		 * @exception DAOException
		 */
		 @SuppressWarnings("unchecked")
		public List<CodeDtlVO> searchCodeMgmtDtl(CodeDtlVO codeDtlVO) throws DAOException {
			DBRowSet dbRowset = null;
			List<CodeDtlVO> list = null;
			//query parameter
			Map<String, Object> param = new HashMap<String, Object>();
			//velocity parameter
			Map<String, Object> velParam = new HashMap<String, Object>();

			try{
				if(codeDtlVO != null){
					Map<String, String> mapVO = codeDtlVO .getColumnValues();
				
					param.putAll(mapVO);
					velParam.putAll(mapVO);
				}
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAOCodeDtlVORSQL(), param, velParam);
				list = (List)RowSetUtil.rowSetToVOs(dbRowset, CodeDtlVO .class);
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
			 * 
			 * Delete code management master.<br>
			 * 
			 * @param List<CodeVO> delModels
			 * @throws DAOException
			 */
			public void removeCodeMgmt(List<CodeVO> delModels) throws DAOException,Exception {
				try {
					SQLExecuter sqlExe = new SQLExecuter("");
					int delCnt[] = null;
					if(delModels.size() > 0){
						delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeVODSQL(), delModels,null);
						for(int i = 0; i < 1; i++){
							if(delCnt[i]== Statement.EXECUTE_FAILED)
								throw new DAOException("Fail to Delete No"+ i + " SQL");
						}
					}
				} catch (SQLException se) {
					log.error(se.getMessage(), se);
					throw new DAOException(new ErrorHandler(se).getMessage());
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
					throw new DAOException(new ErrorHandler(ex).getMessage());
				}
			}
			/**
			 * 
			 * Delete code management detail.<br>
			 * 
			 * @param List<CodeDtlVO> delModels
			 * @throws DAOException
			 */
			public void removeCodeMgmtDtl(List<CodeDtlVO> delModels) throws DAOException,Exception {
				try {
					SQLExecuter sqlExe = new SQLExecuter("");
					int delCnt[] = null;
					if(delModels.size() > 0){
						delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeDtlVODSQL(), delModels,null);
						for(int i = 0; i < 1; i++){
							if(delCnt[i]== Statement.EXECUTE_FAILED)
								throw new DAOException("Fail to Delete No"+ i + " SQL");
						}
					}
				} catch (SQLException se) {
					log.error(se.getMessage(), se);
					throw new DAOException(new ErrorHandler(se).getMessage());
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
					throw new DAOException(new ErrorHandler(ex).getMessage());
				}
			}
			/**
			 * Code Management master - Save - Update<br>
			 * 
			 * @param List<CodeVO> updModels
			 * @throws SQLException 
			 */
			public void modifyCodeMgmt(List<CodeVO> updModels) throws SQLException, Exception {
				try	{
					SQLExecuter sqlExe = new SQLExecuter("");
					int updCnt[] = null;
					if(updModels.size() > 0){
						updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeVOUSQL(), updModels, null);
						for(int i = 0; i < updCnt.length; i++){
							if(updCnt[i]== Statement.EXECUTE_FAILED)
								throw new DAOException("Fail to update No"+ i + " SQL");
						}
					}		
				}catch (SQLException se){
					log.error(se.getMessage(), se);
					throw new DAOException(new ErrorHandler(se).getMessage());
				}catch (Exception ex){
					log.error(ex.getMessage(), ex);
					throw new DAOException(new ErrorHandler(ex).getMessage());
				}
			}
			/**
			 * Code Management detail - Save - Update<br>
			 * 
			 * @param List<CodeDtlVO> updModels
			 * @throws SQLException 
			 */
			public void modifyCodeMgmtDtl(List<CodeDtlVO> updModels) throws SQLException, Exception {
				try	{
					SQLExecuter sqlExe = new SQLExecuter("");
					int updCnt[] = null;
					if(updModels.size() > 0){
						updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeDtlVOUSQL(), updModels, null);
						for(int i = 0; i < updCnt.length; i++){
							if(updCnt[i]== Statement.EXECUTE_FAILED)
								throw new DAOException("Fail to update No"+ i + " SQL");
						}
					}		
				}catch (SQLException se){
					log.error(se.getMessage(), se);
					throw new DAOException(new ErrorHandler(se).getMessage());
				}catch (Exception ex){
					log.error(ex.getMessage(), ex);
					throw new DAOException(new ErrorHandler(ex).getMessage());
				}
			}
			/**
			 * 
			 * Add data into database code management master.<br>
			 * 
			 * @param List<CodeVO> insModels
			 * @throws DAOException 
			 */
			public void addCodeVO(List<CodeVO> insModels) throws DAOException,Exception {
				try {
					SQLExecuter sqlExe = new SQLExecuter("");
					int insCnt[] = null;
					if(insModels.size() > 0){
						insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeVOCSQL(), insModels,null);
						for(int i = 0; i < insCnt.length; i++){
							if(insCnt[i]== Statement.EXECUTE_FAILED)
								throw new DAOException("Fail to insert No"+ i + " SQL");
						}
					}
				} catch (SQLException se) {
					log.error(se.getMessage(),se);
					throw new DAOException(new ErrorHandler(se).getMessage());
				}catch(Exception ex){
					log.error(ex.getMessage(),ex);
					throw new DAOException(new ErrorHandler(ex).getMessage());
				}		
			}
			
			/**
			 * 
			 * Add data into database code management detail.<br>
			 * 
			 * @param List<CodeDtlVO> insModels
			 * @throws DAOException 
			 */
			public void addCodeDtlVO(List<CodeDtlVO> insModels) throws DAOException,Exception {
				try {
					SQLExecuter sqlExe = new SQLExecuter("");
					int insCnt[] = null;
					if(insModels.size() > 0){
						insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeDtlVOCSQL(), insModels,null);
						for(int i = 0; i < insCnt.length; i++){
							if(insCnt[i]== Statement.EXECUTE_FAILED)
								throw new DAOException("Fail to insert No"+ i + " SQL");
						}
					}
				} catch (SQLException se) {
					log.error(se.getMessage(),se);
					throw new DAOException(new ErrorHandler(se).getMessage());
				}catch(Exception ex){
					log.error(ex.getMessage(),ex);
					throw new DAOException(new ErrorHandler(ex).getMessage());
				}		
			}
			/**
			 * Check duplicate code management master when insert
			 * 
			 * @param inputVO
			 * @return Duplicate Flag (Y || N)
			 * @throws DAOException
			 */
			public String CheckDplCodeVO(CodeVO inputVO) throws DAOException {
				DBRowSet dbRowset = null;
				String dupFlg = "";
				//query parameter
				Map<String, Object> param = new HashMap<String, Object>();
				//velocity parameter
				Map<String, Object> velParam = new HashMap<String, Object>();
				
				try{
					if( inputVO != null ){
						Map<String, String> mapVO = inputVO .getColumnValues();
						 
						param.putAll(mapVO);
						velParam.putAll(mapVO);
					}
					dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAOCheckDplCodeVORSQL(), param, velParam);
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
			
			/**
			 * Check duplicate code management detail when insert
			 * 
			 * @param inputVO
			 * @return Duplicate Flag (Y || N)
			 * @throws DAOException
			 */
			public String CheckDplCodeDtlVO(CodeDtlVO inputVO) throws DAOException {
				DBRowSet dbRowset = null;
				String dupFlg = "";
				//query parameter
				Map<String, Object> param = new HashMap<String, Object>();
				//velocity parameter
				Map<String, Object> velParam = new HashMap<String, Object>();
				
				try{
					if( inputVO != null ){
						Map<String, String> mapVO = inputVO .getColumnValues();
						 
						param.putAll(mapVO);
						velParam.putAll(mapVO);
					}
					dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAOCheckDplCodeDtlVORSQL(), param, velParam);
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
