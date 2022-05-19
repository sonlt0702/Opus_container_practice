/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0002.js
*@FileTitle : Error Message Management2
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
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
     * @class DOU_TRN_0002 : DOU_TRN_0002 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */
    function DOU_TRN_0002() {
    	this.processButtonClick		= tprocessButtonClick;
    	this.setSheetObject 		= setSheetObject;
    	this.loadPage 				= loadPage;
    	this.initSheet 				= initSheet;
    	this.initControl            = initControl;
    	this.doActionIBSheet 		= doActionIBSheet;
    	this.setTabObject 			= setTabObject;
    	this.validateForm 			= validateForm;
    }
    
   	/* 개발자 작업	*/
    var sheetObjects=new Array();
    var sheetCnt=0;
    document.onclick=processButtonClick;

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
    			case "btn_Save":
            		doActionIBSheet(sheetObject1,formObj, IBSAVE);
            		break;
            	case "btn_DownExcel":
            		doActionIBSheet(sheetObject1,formObj,IBDOWNEXCEL);
            		break;
            	case "btn_Add":
            		doActionIBSheet(sheetObject1,formObj, IBINSERT);
            		break;
    			default :
    				break;}
    	} catch (e) {
    		if (e == "[object Error]") {
    			ComShowCodeMessage('COM132304');
    		} else {
    			ComShowMessage(e.message);
    			
    		}
    	}
    }

    function loadPage(){
    	//generate Grid Layout
    	for (i = 0; i < sheetObjects.length; i++) {
    		ComConfigSheet(sheetObjects[i]);
    		initSheet(sheetObjects[i], i + 1);
    		ComEndConfigSheet(sheetObjects[i]);
    	}
    	
    	//auto search data after loading finish.
    	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
    }

    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    	case 1: // sheet1 init
    		with (sheetObj) {

    			var HeadTitle = "|Del|Msg Cd|Msg Type|Msg Level|Message|Description";
    			var headCount = ComCountHeadTitle(HeadTitle);
    			// (headCount, 0, 0, true);

    			sheetObj.SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});

    			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
    			var headers = [ { Text : HeadTitle, Align : "Center" }];
    			InitHeaders(headers, info);

    			var cols = [ 
    	             { Type : "Status", Hidden : 1, Width : 50, Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
    	             { Type : "DelCheck", Hidden : 0, Width : 50, Align : "Center", ColMerge : 0, SaveName : "DEL" }, 
    	             { Type : "Text", Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_msg_cd", KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1, EditLen: 8 }, 
    	             { Type : "Combo", Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_tp_cd", KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1, ComboText:"Server|UI|Both", ComboCode:"S|U|B" }, 
    	             { Type : "Combo", Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_lvl_cd", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1, ComboText:"ERR|WARNING|INFO", ComboCode:"E|W|I" }, 
    	             { Type : "Text", Hidden : 0, Width : 600, Align : "Left", ColMerge : 0, SaveName : "err_msg", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
    	             { Type : "Text", Hidden : 0, Width : 100, Align : "Left", ColMerge : 0, SaveName : "err_desc", KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 } 
    	             ];

    			InitColumns(cols);
    			SetEditable(1);
    			SetWaitImageVisible(0);
    			resizeSheet();
    		}
    		break;
    	}

    }

    function resizeSheet() {
    	ComResizeSheet(sheetObjects[0]);
    }

    function setSheetObject(sheet_obj) {
    	sheetObjects[sheetCnt++] = sheet_obj;
    }

    function doActionIBSheet(sheetObj,formObj,sAction) {
    	sheetObj.ShowDebugMsg();
    	switch (sAction) {
    	case IBSEARCH: // retrieve
    		formObj.f_cmd.value = SEARCH;
    		ComOpenWait(true);
    		sheetObj.DoSearch("DOU_TRN_0002GS.do", FormQueryString(formObj) );
    		break;
    	case IBSAVE: // retrieve
    		formObj.f_cmd.value = MULTI;
    		sheetObj.DoSave("DOU_TRN_0002GS.do", FormQueryString(formObj));
    		break;
    	case IBINSERT: //Row Add button event
    		sheetObj.DataInsert();
    		break;
    	case IBDOWNEXCEL:	//엑셀다운로드
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}else{
				//sheetObj.Down2Excel({DownCols: "Msg Cd"|"Msg Type"|"Msg Level"|"Message"|"Description", SheetDesign:1, Merge:1});
				//sheetObj.Down2Excel({DownCols: "err_msg_cd|err_tp_cd|err_lvl_cd|err_msg|err_desc", FileName : "myFile.xlsx", SheetDesign:1});
				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj), FileName : "myFile.xlsx"});
			}
			break;
    	}
    }
    
    function sheet1_OnChange(sheetObj,Row,Col){
   	 if(Col == 2){
			var code=sheetObj.GetCellValue(Row, Col);
			var formObj = document.form;
			if (code != '') {
				validateMsgCode(code, sheetObj, Row, Col);
				checkDuplicateMsgCode(code, sheetObj, Row, Col);
				formObj.s_err_msg_cd.value = "";
			}
   	 }
    }
    function validateMsgCode(code, sheetObj, Row, Col) {
    	if (code.length != 8) {
    		ComShowCodeMessage('COM132304',code);
			sheetObj.SetCellValue(Row, Col,"");
			return;
    	}
    	var strCode=code.slice(0,3);
		var numCode=code.slice(3);
		if (!strCode.match(/^[A-Z]+$/)) {
			ComShowCodeMessage('COM132302',strCode);
			sheetObj.SetCellValue(Row, Col,"");
			return;
		}
		if (!numCode.match(/^[0-9]+$/)) {
			ComShowCodeMessage('COM132303',numCode);
			sheetObj.SetCellValue(Row, Col,"");
			return;
		}
    }
    function checkDuplicateMsgCode(code, sheetObj, Row, Col) {
    	var formObj = document.form;
		formObj.f_cmd.value= COMMAND01;
		formObj.s_err_msg_cd.value = code;
		var check = sheetObj.GetSearchData("DOU_TRN_0002GS.do", FormQueryString(formObj));
		if (!check) {
			ComShowCodeMessage('COM131302',code);
			sheetObj.SetCellValue(Row, Col,"");
			formObj.s_err_msg_cd.value = "";
			return;
		}
    }

    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
    }


	/* 개발자 작업  끝 */