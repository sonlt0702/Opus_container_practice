/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0109.js
*@FileTitle : Joo Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.01
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.01 
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
	 * @class ESM_DOU_0109 : ESM_DOU_0109 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
	 */
    function ESM_DOU_0109() {
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

    // Event handler processing by button click event */
    document.onclick = processButtonClick;
    // Event handler processing by button name */
    function processButtonClick() {
    	/** *** setting sheet object **** */
    	var sheetObject1 = sheetObjects[0];
    	/** **************************************************** */
    	var formObj = document.form;
    	try {
    		var srcName = ComGetEvent("name");
    		if (srcName == null) {
    			return;
    		}
    		switch (srcName) {
    			case "btn_Retrieve":
    				doActionIBSheet(sheetObject1, formObj, IBSEARCH);
    				break;
    			case "btn_New":
    				resetForm(formObj);
    				break;
    			case "btn_Save":
    				doActionIBSheet(sheetObject1, formObj, IBSAVE);
    				break;
    			case "btn_DownExcel":
    				if (sheetObject1.RowCount() < 1) {// no data
    					ComShowCodeMessage("COM132501");
    				} else {
    					sheetObject1.Down2Excel({DownCols: makeHiddenSkipCol(sheetObject1), FileName : "myFile.xlsx", Merge: 1});
    				}
    				break;
    			case "btns_calendar1":
    			case "btns_calendar2":
                    var cal=new ComCalendar();
                    if(srcName == "btns_calendar1"){
                        cal.select(formObj.s_cre_dt_fm, 'yyyy-MM-dd');
                    }else{
                        cal.select(formObj.s_cre_dt_to, 'yyyy-MM-dd');
                    }
                    break;
    			case "btn_RowDelete":
    				doActionIBSheet(sheetObject1, formObj, IBDELETE);
    				break;
    			case "btn_RowAdd":
    				doActionIBSheet(sheetObject1, formObj, IBINSERT);
    				break;
    			default :
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
	 * rest form when click new button
	 */
    function resetForm(formObj){
    	formObj.reset();
    	sheetObjects[0].RemoveAll();
    	s_jo_crr_cd.SetSelectIndex(0);
    }
    /**
	 * registering IBSheet Object as list adding process for list in case of
	 * needing batch processing with other items defining list on the top of
	 * source
	 */
    function setSheetObject(sheet_obj) {
    	sheetObjects[sheetCnt++] = sheet_obj;
    }
    /**
	 * registering IBCombo Object as list param : combo_obj adding process for
	 * list in case of needing batch processing with other items defining list
	 * on the top of source
	 */
    function setComboObject(combo_obj) {
    	comboObjects[comboCnt++] = combo_obj;
    }
    /**
	 * initializing sheet implementing onLoad event handler in body tag adding
	 * first-served functions after loading screen.
	 */
    function loadPage() {
    	// generate Grid Layout
    	for (i = 0; i < sheetObjects.length; i++) {
    		ComConfigSheet(sheetObjects[i]);
    		initSheet(sheetObjects[i], i + 1);
    		ComEndConfigSheet(sheetObjects[i]);
    	}
    	// //generate dropdown list data
    	for ( var k = 0; k < comboObjects.length; k++) {
    		initCombo(comboObjects[k], k + 1);
    	}
    	initControl();
    	
    	// auto search data after loading finish.
    	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
    }
    /**
	 * init control
	 */
    function initControl() {
    	
    }
    /**
	 * setting Combo basic info param : comboObj, comboNo initializing sheet
	 */
    function initCombo(comboObj, comboNo) {
    	var formObj = document.form
    	switch (comboNo) {
    	case 1:
    		with (comboObj) {
    			SetMultiSelect(1);
    	        SetDropHeight(200);
    	        ValidChar(2,1);
    		}
    		var comboItems = crrCombo.split("|");
    		for (var i=0 ; i < comboItems.length ; i++) {
				comboObj.InsertItem(i, comboItems[i], comboItems[i]);
			}
    		break;
    	}
    }

    function s_jo_crr_cd_OnCheckClick(comboObj, index, code) {
        if(index==0) {          
            var bChk=comboObj.GetItemCheck(index);
            if(bChk){
                for(var i=1 ; i < comboObj.GetItemCount() ; i++) {
                    comboObj.SetItemCheck(i,0);
                }
            }
        } else {
            // ALL 이 아닌 다른 Item 체크.
            var bChk=comboObj.GetItemCheck(index);
            if (bChk) {
                comboObj.SetItemCheck(0,0);
            }
        }
        // Combo Item이 전부 Uncheck 일때 자동으로 All 선택이 되도록 한다.
        var checkCnt=0;
        var count = parseInt(comboObj.GetItemCount());
        for(var i = 1 ; i <  count; i++) {
            if(comboObj.GetItemCheck(i)) {
                checkCnt++;
            }
        }
        if(checkCnt == 0) {
            comboObj.SetItemCheck(0,true, null, null, false);
        }
    }
    /**
	 * setting sheet initial values and header param : sheetObj, sheetNo adding
	 * case as numbers of counting sheets
	 */
    function initSheet(sheetObj, sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    	case 1: // sheet1 init
    		with (sheetObj) {
	    		var HeadTitle = "STS|Chk|Carrier|Rev. Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";
				var headCount = ComCountHeadTitle(HeadTitle);
	
				SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});
	
				var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
				var headers = [ { Text : HeadTitle, Align : "Center" }];
				InitHeaders(headers, info);
	
				var cols = [ 
		             { Type : "Status",   Hidden : 1, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
		             { Type : "CheckBox", Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "del_chk" }, 
		             { Type : "Popup", 	  Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "jo_crr_cd",   KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1 }, 
		             { Type : "Combo", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "rlane_cd",    KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1 }, 
		             { Type : "Popup", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "vndr_seq",    KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
		             { Type : "Popup", 	  Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "cust_cnt_cd", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
		             { Type : "Popup", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "cust_seq",    KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
		             { Type : "Popup",	  Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "trd_cd",      KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
		             { Type : "Combo", 	  Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "delt_flg",    KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
		             { Type : "Text", 	  Hidden : 0, Width : 150, Align : "Center", ColMerge : 0, SaveName : "cre_dt",      KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }, 
		             { Type : "Text", 	  Hidden : 0, Width : 180, Align : "Left",   ColMerge : 0, SaveName : "cre_usr_id",  KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }, 
		             { Type : "Text", 	  Hidden : 0, Width : 150, Align : "Center", ColMerge : 0, SaveName : "upd_dt",      KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }, 
		             { Type : "Text", 	  Hidden : 0, Width : 180, Align : "Left",   ColMerge : 0, SaveName : "upd_usr_id",  KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 }
		             ];
	
				InitColumns(cols);
				SetEditable(1);
				SetColProperty("rlane_cd", { ComboText : rlanCombo, ComboCode : rlanCombo });
				SetColProperty("delt_flg", { ComboText : "N|Y", ComboCode : "N|Y" });
				SetWaitImageVisible(0);
				resizeSheet();
    		}
    		break;
    	}
    }
    function resizeSheet(){
        ComResizeSheet(sheetObjects[0]);
    }
    
    function doActionIBSheet(sheetObj, formObj, sAction) {
    	sheetObj.ShowDebugMsg(false);
    	if (!validateForm(sheetObj, formObj, sAction)) {
    		return
    	}
    	switch (sAction) {
    	case IBSEARCH: // retrieve
    		formObj.f_cmd.value = SEARCH;
    		ComOpenWait(true);
    		sheetObj.DoSearch("ESM_DOU_0109GS.do", FormQueryString(formObj) );
    		break;
    	case IBSAVE: // retrieve
    		formObj.f_cmd.value = MULTI;
    		sheetObj.DoSave("ESM_DOU_0109GS.do", FormQueryString(formObj));
    		break;
    	case IBINSERT: // Row Add button event
    		sheetObj.DataInsert(-1);
    		break;
    	case IBDELETE: // Row Delete button event
    		for( var i = sheetObj.LastRow(); i >= sheetObj.HeaderRows(); i-- ) {
    			if(sheetObj.GetCellValue(i, "del_chk") == 1){
    				sheetObj.SetRowHidden(i, 1);
    				sheetObj.SetRowStatus(i,"D");
    			}
    		}
    		break;
    	}
    }

    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
    }

    function sheet1_OnBeforeSave() {
    	ComOpenWait(true);
    }

    function sheet1_OnSaveEnd(sheetObj, Code, Msg, StCode, StMsg) {
    	if (Code >= 0) {
    		doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
    	} else {
    		ComOpenWait(false);
    	}
    }
    /**
	 * handling process for input validation
	 */
    function validateForm(sheetObj, formObj, sAction) {
    	sheetObj.ShowDebugMsg(false);
    	switch (sAction) {
    	case IBSEARCH: // retrieve
    		var creDtFm = form.s_cre_dt_fm;
            var creDtTo = form.s_cre_dt_to;
            if(creDtFm.value != "" && creDtTo.value != "" && creDtFm.value > creDtTo.value) {
                ComShowCodeMessage("COM132003");
                ComSetFocus(creDtFm);
                return false;
            }
            if(!ComChkObjValid(creDtFm)) {return false;}
            if(!ComChkObjValid(creDtTo)) {return false;}
    		break;
    	}
    	return true;
    }
    /**
	 * This function handling when sheet1 on change. Event fires when the cell
	 * editing is completed and the previous value has been updated.
	 * 
	 * @param sheetObj :
	 *            Object - Object sheet.
	 * @param Row :
	 *            Long - Row index of the cell.
	 * @param Col :
	 *            Long - Column index of the cell.
	 * @param Value :
	 *            string - Updated value.
	 * @param OldValue :
	 *            string - Value before update.
	 * @param RaiseFlag:
	 *            Integer - Event fire option, value: 0 manual editing|1
	 *            method|2 paste.
	 */
    function sheet1_OnChange(sheetObj, Row, Col, Value, OldValue, RaiseFlag){
    	var formObj=document.form ;
    	var colName=sheetObj.ColSaveName(Col);
    	
    	if(Value == ""){
    		return;
    	}
    	// check duplicate data
    	if(colName == "jo_crr_cd" || colName == "rlane_cd"){
    		if(sheetObj.GetCellValue(Row,"jo_crr_cd") != "" && sheetObj.GetCellValue(Row,"rlane_cd") != ""){
    			// check on UI side
    			if(sheetObj.ColValueDup("jo_crr_cd|rlane_cd") > -1){
    				ComShowCodeMessage("COM12115", "The Carrier and Rev.Lane");
    				sheetObj.SetCellValue(Row, Col,OldValue,0);
    				sheetObj.SelectCell(Row, Col);
    				return;
    			}
    		}
    	}
    }
    /**
	 * Handling event when click popup.
	 * 
	 * @param sheetObj
	 * @param Row
	 * @param Col
	 */
	function sheet1_OnPopupClick(sheetObj,Row, Col){
		var colName = sheetObj.ColSaveName(Col);
		
		switch(colName){
			case "jo_crr_cd":
				/**
				 * This function open the pop-up.
				 * 
				 * @param sUrl:
				 *            {string} - Required, pop-up address to be called.
				 * @param iWidth:
				 *            {int} - Required, the width of the pop-up window
				 * @param iHeight:
				 *            {int} - Required, the height of the pop-up window
				 * @param sFunc:
				 *            {string} - Required, function return data to
				 *            parent window.
				 * @param sDisplay:
				 *            {string} - Required, column of the grid in the
				 *            pop-up window is hidden, value: 1 visible|0
				 *            hidden.
				 * @param bModal:
				 *            {bool} - Selection, is the pop-up modal?
				 */
				ComOpenPopup('/opuscntr/COM_ENS_0N1.do', 800, 500, 'setJoCrrCd', '1,0,1', true, false, Row, Col);
				break;
			case "vndr_seq":
				ComOpenPopup('/opuscntr/COM_COM_0007.do', 900, 520, 'setVndrCd', '1,0,1', true, false, Row, Col);
				break;
			case "cust_cnt_cd":
			case "cust_seq":
				ComOpenPopup('/opuscntr/Customer_POPUP.do', 800, 500, 'setCustCd', '1,0,1', true, false, Row, Col);
				break;
			
			case "trd_cd":
				ComOpenPopup('/opuscntr/COM_COM_0012.do', 800, 500, 'setTrdCd', '1,0,1', true, false, Row, Col);
				break;
			
		}
	}
	/**
	 * This function return data for cell pop-up column carrier.
	 */
	function setJoCrrCd(aryPopupData, row, col){
		var sheetObject=sheetObjects[0];
		sheetObject.SetCellValue(row,col,aryPopupData[0][3]);
	}

	/**
	 * This function return data for cell pop-up column vendor code.
	 */
	function setVndrCd(aryPopupData, row, col){
		var sheetObject=sheetObjects[0];
		sheetObject.SetCellValue(row,col,aryPopupData[0][2]);
	}

	/**
	 * This function return data for cell pop-up column customer code.
	 */
	function setCustCd(aryPopupData, row, col){
		var sheetObject=sheetObjects[0];
		sheetObject.SetCellValue(row,"cust_cnt_cd",aryPopupData[0][2]);
		sheetObject.SetCellValue(row,"cust_seq",aryPopupData[0][3]);
	}

	/**
	 * This function return data for cell pop-up column trade.
	 */
	function setTrdCd(aryPopupData, row, col){
		var sheetObject=sheetObjects[0];
		sheetObject.SetCellValue(row,col,aryPopupData[0][3]);
	}