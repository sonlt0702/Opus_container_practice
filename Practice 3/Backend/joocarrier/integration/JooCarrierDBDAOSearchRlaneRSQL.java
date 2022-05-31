/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierDBDAOSearchRlaneRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.30
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.30 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining3.joocarrier.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Son Le
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class JooCarrierDBDAOSearchRlaneRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * search Rlane
	  * </pre>
	  */
	public JooCarrierDBDAOSearchRlaneRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining3.joocarrier.integration").append("\n"); 
		query.append("FileName : JooCarrierDBDAOSearchRlaneRSQL").append("\n"); 
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
		query.append("SELECT DISTINCT" ).append("\n"); 
		query.append("	RLANE_CD" ).append("\n"); 
		query.append("FROM JOO_STL_TGT" ).append("\n"); 
		query.append("WHERE 1=1" ).append("\n"); 
		query.append("#if (${jo_crr_cds}!=''  && ${jo_crr_cds} != 'ALL')" ).append("\n"); 
		query.append("AND JO_CRR_CD  IN ( #foreach($key IN ${jo_crr_cds})#if($velocityCount < $jo_crr_cds.size()) '$key', #else '$key' #end #end)" ).append("\n"); 
		query.append("#end" ).append("\n"); 

	}
}