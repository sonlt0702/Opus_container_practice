/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.js
*@FileTitle : Joo Carrier
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.23
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.23 
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */

    /**
     * @extends 
     * @class ESM_DOU_0108 : ESM_DOU_0108 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */
    function ESM_DOU_0108() {
    	this.processButtonClick		= tprocessButtonClick;
    	this.setSheetObject 		= setSheetObject;
    	this.loadPage 				= loadPage;
    	this.initSheet 				= initSheet;
    	this.initControl            = initControl;
    	this.doActionIBSheet 		= doActionIBSheet;
    	this.setTabObject 			= setTabObject;
    	this.validateForm 			= validateForm;
    }
    // common global variable
    var sheetObjects = new Array();
    var sheetCnt = 0;
    var comboObjects = new Array();
    var comboCnt = 0;
    var tabObjects=new Array();
    var tabCnt=0 ;
    var beforetab=1;
    var checkFirstTimeRetrieve = true;

    // Event handler processing by button click event */
    document.onclick = processButtonClick;
    // Event handler processing by button name */
    function processButtonClick() {
        /** *** setting sheet object **** */
        var sheetObject = getCurrentSheet();
        /** **************************************************** */
        var formObj = document.form;
        try {
            var srcName = ComGetEvent("name");
            switch (srcName) {
                case "btn_Retrieve":
                	if (checkFirstTimeRetrieve && GetCheckOverThreeMonth()) {
                		if (confirm("Year Month over 3 months, do you realy want to get data?")) {
                			doActionIBSheet(sheetObjects[0], formObj, IBSEARCH);
                            doActionIBSheet(sheetObjects[1], formObj, IBSEARCH);
                            checkFirstTimeRetrieve = false;
                    	}
                	} else {
                		doActionIBSheet(sheetObjects[0], formObj, IBSEARCH);
                        doActionIBSheet(sheetObjects[1], formObj, IBSEARCH);
                	}
                    break;
                case "btn_New": // clear all search conditions and data on sheet
                    initPeriod();
                    clearAllData()
                    comboObjects[0].SetSelectIndex(0);
                    comboObjects[1].SetSelectIndex(-1);
                    comboObjects[2].SetSelectIndex(-1);
                    comboObjects[2].SetEnable(0);
                    
                    break;
                case "btn_vvd_from_back":
                    addMonth(formObj.s_fr_acct_yrmon, -1);
                    // Clear data on Grid of Summary & Details tab
                    clearAllData()
                    break;
                case "btn_vvd_from_next":
                    if (!GetCheckConditionPeriod()){
                        ComShowCodeMessage("COM132003");
                        return;
                    }
                    addMonth(formObj.s_fr_acct_yrmon, +1);
                    // Clear data on Grid of Summary & Details tab
                    clearAllData()
                    break;
                case "btn_vvd_to_back":
                    if (!GetCheckConditionPeriod()){
                        ComShowCodeMessage("COM132003");
                        return;
                    }
                    addMonth(formObj.s_to_acct_yrmon, -1);
                    // Clear data on Grid of Summary & Details tab
                    clearAllData()
                    break;
                case "btn_vvd_to_next":
                    addMonth(formObj.s_to_acct_yrmon, +1);
                    // Clear data on Grid of Summary & Details tab
                    clearAllData()
                    break;
                case "btn_DownExcel":
            		doActionIBSheet(sheetObject,formObj,IBDOWNEXCEL);
            		break;
            } // end switch
        } catch (e) {
            if (e == "[object Error]") {
                ComShowMessage(OBJECT_ERROR);
            } else {
                ComShowMessage(e.message);
            }
        }
    }
    /**
     * return current sheet on screen
     */
    function getCurrentSheet(){
        var sheetObj=null;
        if(beforetab == 0){
            sheetObj=sheetObjects[0];
        }else{
            sheetObj=sheetObjects[1];
        }
        
        return sheetObj;
    }
    /**
     * clear all data on summary & detail tab
     */
    function clearAllData() {
    	sheetObjects[0].RemoveAll();
    	sheetObjects[1].RemoveAll();
    }
    /**
     * registering IBSheet Object as list adding process for list in case of needing
     * batch processing with other items defining list on the top of source
     */
    function setSheetObject(sheet_obj) {
        sheetObjects[sheetCnt++] = sheet_obj;
    }
    /**
     * registering IBCombo Object as list param : combo_obj adding process for list
     * in case of needing batch processing with other items defining list on the top
     * of source
     */
    function setComboObject(combo_obj) {
        comboObjects[comboCnt++] = combo_obj;
    }
    /**
     * registering IBTab Object as list
     * adding process for list in case of needing batch processing with other items
     * defining list on the top of source
     */
    function setTabObject(tab_obj){
        tabObjects[tabCnt++]=tab_obj;
    }
    
    /**
     * initializing sheet implementing onLoad event handler in body tag adding
     * first-served functions after loading screen.
     */
    function loadPage() {
    	// initializing IBSheet
        for (i = 0; i < sheetObjects.length; i++) {
            ComConfigSheet(sheetObjects[i]);
            initSheet(sheetObjects[i], i + 1);
            ComEndConfigSheet(sheetObjects[i]);
        }
        // initializing IBMDITab
        for(k=0;k<tabObjects.length;k++){
            initTab(tabObjects[k],k+1);
            tabObjects[k].SetSelectedIndex(0); // select tab summary
        }
        
        // initializing IBMultiCombo
        for (var k = 0; k < comboObjects.length; k++) {
            initCombo(comboObjects[k], k + 1);
        }
        initControl();
        resizeSheet();
        // auto load data when load page
        doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
        doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
    }
    function initControl() {
    	// generate sub sum
    	subSum();
    	// disable combo box Lane & Trade
    	s_rlane_cd.SetEnable(false);
    	s_trd_cd.SetEnable(false);
    	// initial from date - to date
        initPeriod();
    }
    /**
     * initializing Tab
     * setting Tab items
     */
    function initTab(tabObj , tabNo) {
         switch(tabNo) {
             case 1:
                with (tabObj) {
                    var cnt=0 ;
                    InsertItem( "Summary" , "");
                    InsertItem( "Detail" , "");
                }
             break;
         }
    }
    /**
     * Event when clicking Tab
     * activating selected tab items
     */
    function tab1_OnChange(tabObj , nItem)
    {
        var objs=document.all.item("tabLayer");
        objs[nItem].style.display="Inline";
        objs[beforetab].style.display="none";
        //--------------- important --------------------------//
        objs[beforetab].style.zIndex=objs[nItem].style.zIndex -1 ;
        //------------------------------------------------------//
        beforetab=nItem;
        resizeSheet();
    }

    /**
     * setting Combo basic info
     * 
     * @param comboObj
     * @param comboIndex
     *            Number
     */
    function initCombo(comboObj, comboNo) {
	    	switch (comboObj.options.id) {
	    		// combo box Partner
	    		case "s_jo_crr_cd":
	    			with (comboObj) {
			            SetMultiSelect(1);
			            ValidChar(2, 1); // Upper case + number
			            SetDropHeight(80);
		        	}
	    			// load data Partner into combo box Partner
	    			var comboItems = partners.split("|");
	    			for (var i=0 ; i < comboItems.length ; i++) {
	    				comboObj.InsertItem(i, comboItems[i], comboItems[i]);
	    			}
	    			break;
	    		default:
	    			with (comboObj) {
		            ValidChar(2, 1); // Upper case + number
		            SetDropHeight(80);
	        	}
	    	}
    }
    /**
     * initial "From Date" and "To Date"
     */
    function initPeriod(){
        var formObj=document.form;
        
        //get current date
        var tmpToYm = ComGetNowInfo("ymd","-"); 
        // set date into "To Date" by format year-month
        formObj.s_to_acct_yrmon.value=JooGetDateFormat(tmpToYm,"ym");
        
        // get previous date (before 2 month)
        var tmpFrYm = ComGetDateAdd(formObj.s_to_acct_yrmon.value+"-01","M", -2);
        // set date into "From Date" by format year-month
        formObj.s_fr_acct_yrmon.value=JooGetDateFormat(tmpFrYm,"ym"); 
    }
    /**
	 * Format date
	 * 
	 * @param srcCharValue
	 * @param sFormat
	 * @returns {String}
	 */
	function JooGetDateFormat(obj, sFormat){
		var sVal = String(getArgValue(obj));
		sVal = sVal.replace(/\/|\-|\.|\:|\ /g,"");
		
	    if (ComIsEmpty(sVal)) return "";
	    
		var retValue = "";
		switch(sFormat){
		    	
			case "ymd":		//yyyy-mm-dd 10	            
			case "mdy":		//mm-dd-yyyy 10
				retValue = sVal.substring(0,8);
				break;     	            
		    case "ym":		//yyyy-mm 7
		    case "yw":		//yyyy-mm 7
		    case "hms":     //hh:mm:ss 8
				retValue = sVal.substring(0,6);
				break;     	            
		    case "yyyy":     //yyyy 4   	            
		    case "hm":      //hh:mm 5
				retValue = sVal.substring(0,4);
				break;
		    case "ymdhms":     //yyyy-mm-dd hh:mm:ss 19
				retValue = sVal.substring(0,14);
				break;    	            
		    case "ymdhm":     //yyyy-mm-dd hh:mm 16
				retValue = sVal.substring(0,12);
				break;
			}
	
		retValue = ComGetMaskedValue(retValue,sFormat);
		return retValue;
	}
    /**
     * add month click
     * increase iMonth month
     * 
     * @param obj
     * @param iMonth
     * @return
     */
    function addMonth(obj, iMonth){
    	 if (obj.value != "") {
    		 obj.value=ComGetDateAdd(obj.value+"-01", "M", iMonth).substring(0, 7);
    	 }
    }
    /**
     * handle check "To Date" must greater than "From Date"
     */
    function GetCheckConditionPeriod(){
        var formObj=document.form;
        var frDt=formObj.s_fr_acct_yrmon.value.replaceStr("-","")+"01";
        var toDt=formObj.s_to_acct_yrmon.value.replaceStr("-","")+"01";
        if (ComGetDaysBetween(frDt, toDt) <= 0){
            return false;
        }   
        return true;
    }
    /**
     * handle check date between "From Date" and "To Date" must over three month
     * 
     */
    function GetCheckOverThreeMonth(){
        var formObj=document.form;
        var frDt=formObj.s_fr_acct_yrmon.value.replaceStr("-","")+"01";
        var toDt=formObj.s_to_acct_yrmon.value.replaceStr("-","")+"01";
        if (ComGetDaysBetween(frDt, toDt) >= 90){
            return true;
        }   
        return false;
    }
    /**
     * setting sheet initial values and header param : sheetObj, sheetNo adding case
     * as numbers of counting sheets
     */
    function initSheet(sheetObj, sheetNo) {
        var cnt = 0;
        switch (sheetObj.id) {
            case "t1sheet1": // t1sheet1 init
                with(sheetObj){
                    var HeadTitle1="|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
                    var HeadTitle2="|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name";
                    SetConfig( { SearchMode:2, MergeSheet:5, Page:20, DataRowMerge:0 } );
                    var info    = { Sort:0, ColMove:1, HeaderCheck:1, ColResize:1 };
                    var headers = [ { Text:HeadTitle1, Align:"Center"} ,  { Text:HeadTitle2, Align:"Center"}];
                    InitHeaders(headers, info);
                    
                    var cols = [ 
                           {Type:"Status",    Hidden:1, Width:0,    Align:"Center",  ColMerge:1,   SaveName: "ibflag" },
                           {Type:"Text",      Hidden:0, Width:50,   Align:"Center",  ColMerge:0,   SaveName: "jo_crr_cd",       KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:70,   Align:"Center",  ColMerge:0,   SaveName: "rlane_cd",        KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:90,   Align:"Center",  ColMerge:1,   SaveName: "inv_no",          KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:120,  Align:"Center",  ColMerge:1,   SaveName: "csr_no",          KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:90,   Align:"Center",  ColMerge:1,   SaveName: "apro_flg",        KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:50,   Align:"Center",  ColMerge:0,   SaveName: "locl_curr_cd",    KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:120,  Align:"Right",   ColMerge:0,   SaveName: "inv_rev_act_amt", KeyField:0,   CalcLogic:"",   Format:"",   PointCount:2,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:120,  Align:"Right",   ColMerge:0,   SaveName: "inv_exp_act_amt", KeyField:0,   CalcLogic:"",   Format:"",   PointCount:2,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:90,   Align:"Left",    ColMerge:0,   SaveName: "prnr_ref_no",     KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },   
                           {Type:"Text",      Hidden:0, Width:200,  Align:"Left",    ColMerge:0,   SaveName: "cust_vndr_eng_nm",KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 }
                            ];
                     
                    InitColumns(cols);
                    SetEditable(0);
                    SetCountPosition(); // hidden count row
                    SetWaitImageVisible(0);
                 }
                break;
            case "t2sheet1": // t2sheet1 init
                with(sheetObj){
                    var HeadTitle1="|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
                    var HeadTitle2="|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
                    var headCount=ComCountHeadTitle(HeadTitle1);
                    SetConfig( { SearchMode:2, MergeSheet:5, Page:20, DataRowMerge:0 } );
                    var info    = { Sort:1, ColMove:1, HeaderCheck:1, ColResize:1 };
                    var headers = [ { Text:HeadTitle1, Align:"Center"} ,  { Text:HeadTitle2, Align:"Center"}];
                    InitHeaders(headers, info);
                    
                    var cols = [ 
                           {Type:"Status",    Hidden:1, Width:0,    Align:"Center",  ColMerge:1,   SaveName: "ibflag" },
                           {Type:"Text",      Hidden:0, Width:55,   Align:"Center",  ColMerge:0,   SaveName: "jo_crr_cd",          KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:55,   Align:"Center",  ColMerge:0,   SaveName: "rlane_cd",           KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:80,   Align:"Center",  ColMerge:1,   SaveName: "inv_no",             KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:120,  Align:"Center",  ColMerge:1,   SaveName: "csr_no",             KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:65,   Align:"Center",  ColMerge:1,   SaveName: "apro_flg",           KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Combo",     Hidden:0, Width:120,  Align:"Center",  ColMerge:0,   SaveName: "re_divr_cd",         KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:55,   Align:"Center",  ColMerge:0,   SaveName: "jo_stl_itm_cd",      KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:45,   Align:"Center",  ColMerge:0,   SaveName: "locl_curr_cd",       KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:120,  Align:"Right",   ColMerge:0,   SaveName: "inv_rev_act_amt",    KeyField:0,   CalcLogic:"",   Format:"",   PointCount:2,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:120,  Align:"Right",   ColMerge:0,   SaveName: "inv_exp_act_amt",    KeyField:0,   CalcLogic:"",   Format:"",   PointCount:2,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:80,   Align:"Center",  ColMerge:0,   SaveName: "prnr_ref_no",        KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
                           {Type:"Text",      Hidden:0, Width:150,  Align:"Left",    ColMerge:0,   SaveName: "cust_vndr_eng_nm",   KeyField:0,   CalcLogic:"",   Format:"",   PointCount:0,   UpdateEdit:0,   InsertEdit:0 }
                            ];
                     
                    InitColumns(cols);
                    SetColProperty(0, "re_divr_cd", {ComboText:"|Rev|Exp", ComboCode:"|R|E"});
                    SetEditable(0);
                    SetCountPosition(); // hidden count row
                    SetWaitImageVisible(0);
                 }
                break;
        }
    }
    // handling sheet process
    function doActionIBSheet(sheetObj, formObj, sAction, cRow) {
        sheetObj.ShowDebugMsg(false);
        var sheetID=sheetObj.id;
        switch (sAction) {
            case IBSEARCH: // retrieve
                if ( sheetID == "t1sheet1"){
                    formObj.f_cmd.value=SEARCH01;
                    ComOpenWait(true);
                    sheetObj.DoSearch("ESM_DOU_0108GS.do", FormQueryString(formObj));
                    
                }else if ( sheetID == "t2sheet1"){
                	formObj.f_cmd.value=SEARCH02;
                    ComOpenWait(true);
                    sheetObj.DoSearch("ESM_DOU_0108GS.do", FormQueryString(formObj));
                }
                break; 
            case IBDOWNEXCEL:	//down excel
        		// check sheet have to data
    			if(sheetObj.RowCount() < 1){
    				ComShowCodeMessage("COM132501");
    			}else{
    				// download file excel except row hidden and check box
    				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj), FileName : "myFile.xlsx", Merge: 1});
    			}
    			break;
        }
    }
    function t1sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
    	/*************Total Sum Summary****************/
    	var formObj = document.form;
        if (sheetObj.RowCount() > 0) {
        	// get data total sum
        	formObj.f_cmd.value=SEARCH03;
        	var sXml = sheetObj.GetSearchData("ESM_DOU_0108GS.do", FormQueryString(formObj))
        	var currNo = ComGetEtcData(sXml, "currNo")*1; // parseInt
        	// insert and set value of total sum for each currency
        	for (var i=0; i< currNo; i++) {
        		var rowIdx = sheetObj.DataInsert(-1);
        		
        		sheetObj.SetCellValue(rowIdx, "locl_curr_cd", ComGetEtcData(sXml, "curr" + i));
        		sheetObj.SetCellFontBold(rowIdx,"locl_curr_cd",1);
        		
        		sheetObj.SetCellValue(rowIdx, "inv_rev_act_amt", ComGetEtcData(sXml, "rev" + i));
        		sheetObj.SetCellFontBold(rowIdx,"inv_rev_act_amt",1);
        		
        		sheetObj.SetCellValue(rowIdx, "inv_exp_act_amt", ComGetEtcData(sXml, "exp" + i));
        		sheetObj.SetCellFontBold(rowIdx,"inv_exp_act_amt",1);
        		
        		sheetObj.SetRowBackColor(rowIdx,"#FCDCEE");
        	}
        	// select first row again
        	sheetObj.SetSelectRow(2);
        }
        /*************End Total Sum****************/
        ComOpenWait(false);
        
    }

    function t2sheet1_OnSearchEnd(sheetObj, ErrMsg) {
    	/*************Total Sum Detail****************/
    	var formObj = document.form;
        if (sheetObj.RowCount() > 0) {
        	// get data total sum
        	formObj.f_cmd.value=SEARCH03;
        	var sXml = sheetObj.GetSearchData("ESM_DOU_0108GS.do", FormQueryString(formObj))
        	var currNo = ComGetEtcData(sXml, "currNo")*1; // parseInt
        	// insert and set value of total sum for each currency
        	for (var i=0; i< currNo; i++) {
        		var rowIdx = sheetObj.DataInsert(-1);
        		
        		sheetObj.SetCellValue(rowIdx, "locl_curr_cd", ComGetEtcData(sXml, "curr" + i));
        		sheetObj.SetCellFontBold(rowIdx,"locl_curr_cd",1);
        		
        		sheetObj.SetCellValue(rowIdx, "inv_rev_act_amt", ComGetEtcData(sXml, "rev" + i));
        		sheetObj.SetCellFontBold(rowIdx,"inv_rev_act_amt",1);
        		
        		sheetObj.SetCellValue(rowIdx, "inv_exp_act_amt", ComGetEtcData(sXml, "exp" + i));
        		sheetObj.SetCellFontBold(rowIdx,"inv_exp_act_amt",1);
        		
        		sheetObj.SetRowBackColor(rowIdx,"#FCDCEE");
        	}
        	// select first row again
        	sheetObj.SetSelectRow(2);
        }
        /*************End Total Sum****************/
        ComOpenWait(false);
    }
    /**
     * handling process for input validation
     */
    function validateForm(sheetObj, formObj, sAction) {
        return true;
    }
    /**
     * listener event double click on tab summary
     * system automatically change to detail tab and select to the right row for user checking
     * if the details tab has no data before, then system has to retrieve data first
     */
    function t1sheet1_OnDblClick(sheetObj, Row, Col) {
    	var inv = sheetObj.GetCellValue(Row, "inv_no");
    	var code = sheetObj.GetCellValue(Row, "prnr_ref_no");
    	// prevent double click into total sum
    	if (inv != "") {
	    	tabObjects[0].SetSelectedIndex(1);
	    	var idx = 2;
	    	for (var i = 1; i < sheetObjects[1].LastRow(); i++) {
	    		var inv1 = sheetObjects[1].GetCellValue(i, "inv_no");
	    		var code1 = sheetObjects[1].GetCellValue(i, "prnr_ref_no");
	    		if (inv == inv1 && code == code1) {
	    			idx = i;
	    			break;
	    		}
	    	}
	    	sheetObjects[1].SetSelectRow(idx);
    	}
    }
    /**
     * These records are summary data for 1 invoice on both tab
     */
    function subSum() {
    	var info1 = [{StdCol:3, SumCols:"7|8", CaptionText: " "}];
    	var info2 = [{StdCol:3, SumCols:"9|10", CaptionText: " "}];
    	sheetObjects[0].ShowSubSum(info1);
    	sheetObjects[1].ShowSubSum(info2);
    	
    }
    /**
     * handle check all for combo box Partner
     */
    function s_jo_crr_cd_OnCheckClick(comboObj, index, code) {
    	// if check ALL then uncheck remaining items
        if(index==0) {          
            var bChk=comboObj.GetItemCheck(index);
            if(bChk){
                for(var i=1 ; i < comboObj.GetItemCount() ; i++) {
                    comboObj.SetItemCheck(i,0);
                }
            }
        } else {
            //if check remaining items then uncheck item ALL
            var bChk=comboObj.GetItemCheck(index);
            if (bChk) {
                comboObj.SetItemCheck(0,0);
            }
        }
        //auto check ALL if no item is checked
        var checkCnt=0;
        var count = parseInt(comboObj.GetItemCount());
        for(var i = 1 ; i <  count; i++) {
            if(comboObj.GetItemCheck(i)) {
                checkCnt++;
            }
        }
        if(checkCnt == 0) {
            comboObj.SetItemCheck(0, true, false);
        }
    }
    /**
     * handle combo box Partner when on change
     * 
     */
    function s_jo_crr_cd_OnChange(comboObj, oldIndex, oldText, oldCode, newIndex, newText, newCode) {
        if (comboObj.GetSelectCode() == "") {
            return;
        }
        // clear data and enable combo box Lane
        s_rlane_cd.SetEnable(1);
        s_rlane_cd.RemoveAll();
        // clear data and disable combo box Trade
        s_trd_cd.RemoveAll();
        s_trd_cd.SetEnable(0);
        // load data for combo box Lane
    	var formObj = document.form;
    	formObj.f_cmd.value = SEARCH04;
    	var sXml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(formObj));
    	var rlane = ComGetEtcData(sXml, "rlane");
    	var comboItems = rlane.split("|");
		for (var i=0 ; i < comboItems.length ; i++) {
			s_rlane_cd.InsertItem(i, comboItems[i], comboItems[i]);
		}
    }

    /**
     * handle combo box Lane when on change
     * 
     */
    function s_rlane_cd_OnChange(comboObj, oldIndex, oldText, oldCode, newIndex, newText, newCode) {
    	// clear data and enable combo box Trade
    	s_trd_cd.SetEnable(1);
    	s_trd_cd.RemoveAll();
    	// load data for combo box Trade
     	var formObj = document.form;
     	formObj.f_cmd.value = SEARCH05;
     	var sXml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(formObj));
     	var trade = ComGetEtcData(sXml, "trade");
     	var comboItems = trade.split("|");
 		for (var i=0 ; i < comboItems.length ; i++) {
 			s_trd_cd.InsertItem(i, comboItems[i], comboItems[i]);
 		}
    }
    
    function resizeSheet() {
        if(beforetab == 0){
            ComResizeSheet(sheetObjects[0]);
        }else{
            ComResizeSheet(sheetObjects[1]);
        }
    }