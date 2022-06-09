/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierMgmtDBDAOSearchRlaneCdRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.integration ;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Son Le
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class JooCarrierMgmtDBDAOSearchRlaneCdRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * search Rlane
	  * </pre>
	  */
	public JooCarrierMgmtDBDAOSearchRlaneCdRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining4.joocarriermgmt.integration ").append("\n"); 
		query.append("FileName : JooCarrierMgmtDBDAOSearchRlaneCdRSQL").append("\n"); 
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
		query.append("Select VSL_SLAN_CD as rlane_cd" ).append("\n"); 
		query.append("FROM MDM_REV_LANE " ).append("\n"); 
		query.append("WHERE 1 = 1 " ).append("\n"); 
		query.append("     AND DECODE (DELT_FLG, 'Y','D','A') = 'A' " ).append("\n"); 
		query.append("ORDER BY RLANE_CD" ).append("\n"); 

	}
}