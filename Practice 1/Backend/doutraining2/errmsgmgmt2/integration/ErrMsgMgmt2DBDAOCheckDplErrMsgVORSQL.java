/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmt2DBDAOCheckDplErrMsgVORSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.24
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.24 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Son Le
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class ErrMsgMgmt2DBDAOCheckDplErrMsgVORSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * check dupicate error message
	  * </pre>
	  */
	public ErrMsgMgmt2DBDAOCheckDplErrMsgVORSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("err_msg_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining2.errmsgmgmt2.integration").append("\n"); 
		query.append("FileName : ErrMsgMgmt2DBDAOCheckDplErrMsgVORSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("SELECT  DECODE(SIGN(COUNT(ERR_MSG_CD)),1,'Y','N') DUP_FLG" ).append("\n"); 
		query.append("FROM    COM_ERR_MSG" ).append("\n"); 
		query.append("WHERE   ERR_MSG_CD = @[err_msg_cd]" ).append("\n"); 

	}
}