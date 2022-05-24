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
    var sheetObjects=new Array(); // create array contain sheets
    var sheetCnt=0; // index of sheet in array
    document.onclick=processButtonClick; // listener event onclick on page
    // function handle click buttons, navigate to doActionIBSheet (same Dispatcher)
    function processButtonClick() {
    	/** *** setting sheet object **** */
    	var sheetObject1 = sheetObjects[0];
    	/** **************************************************** */
    	// get form
    	var formObj = document.form;
    	try {
    		// get name of button
    		var srcName = ComGetEvent("name");
    		// check not name then end
    		if (srcName == null) {
    			return;
    		}
    		// navigation
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
    			ComShowCodeMessage('COM12111');
    		} else {
    			ComShowMessage(e.message);
    			
    		}
    	}
    }
    // set up page
    function loadPage(){
    	//generate Grid Layout
    	for (i = 0; i < sheetObjects.length; i++) {
    		// set up default sheet by standard
    		ComConfigSheet(sheetObjects[i]);
    		// initial sheet
    		initSheet(sheetObjects[i], i + 1);
    		// set up default sheet by standard
    		ComEndConfigSheet(sheetObjects[i]);
    	}
    	
    	//auto search data after loading finish.
    	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
    }
    // initial sheets
    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    	case 1: // sheet1 init
    		with (sheetObj) {
    			// initial column name join by "|"
    			var HeadTitle = "|Del|Msg Cd|Msg Type|Msg Level|Message|Description";
    			// configure some property of sheet
    			sheetObj.SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});
    			// configure property of header
    			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
    			// configure align header
    			var headers = [ { Text : HeadTitle, Align : "Center" }];
    			// initial header
    			InitHeaders(headers, info);
    			// configure properties of column to save data
    			var cols = [ 
    	             { Type : "Status",   Hidden : 1, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
    	             { Type : "DelCheck", Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "DEL" }, 
    	             { Type : "Text", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_msg_cd", KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1, EditLen: 8 }, 
    	             { Type : "Combo", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_tp_cd",  KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1, ComboText:"Server|UI|Both", 	 ComboCode:"S|U|B" }, 
    	             { Type : "Combo", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "err_lvl_cd", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1, ComboText:"ERR|WARNING|INFO", ComboCode:"E|W|I" }, 
    	             { Type : "Text", 	  Hidden : 0, Width : 600, Align : "Left", 	 ColMerge : 0, SaveName : "err_msg", 	KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 }, 
    	             { Type : "Text", 	  Hidden : 0, Width : 100, Align : "Left", 	 ColMerge : 0, SaveName : "err_desc", 	KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 } 
    	             ];
    			// initial column save data
    			InitColumns(cols);
    			// set table can edit? 1: yes, 0: no
    			SetEditable(1);
    			// set image wait when search of save data, 1: yes, 0: no
    			SetWaitImageVisible(0);
    			// initial height of table fit screen
    			resizeSheet();
    		}
    		break;
    	}

    }

    function resizeSheet() {
    	// initial height of table fit screen
    	ComResizeSheet(sheetObjects[0]);
    }
    // set sheets into array
    function setSheetObject(sheet_obj) {
    	sheetObjects[sheetCnt++] = sheet_obj;
    }
    // handle action and send request to server
    function doActionIBSheet(sheetObj,formObj,sAction) {
    	// navigate action 
    	switch (sAction) {
    	case IBSEARCH: // retrieve
    		// set FormCommand is SEARCH to send HTMLAction
    		formObj.f_cmd.value = SEARCH;
    		// open image waiting
    		ComOpenWait(true);
    		// call method Search to connect to server
    		sheetObj.DoSearch("DOU_TRN_0002GS.do", FormQueryString(formObj) );
    		break;
    	case IBSAVE: // retrieve
    		// set FormCommand is MULTI to send HTMLAction
    		formObj.f_cmd.value = MULTI;
    		// call method Save to connect to server
    		ComOpenWait(true);
    		sheetObj.DoSave("DOU_TRN_0002GS.do", FormQueryString(formObj));
    		break;
    	case IBINSERT: //Row Add button event
    		// insert a new row into sheet, default insert below row select
    		sheetObj.DataInsert();
    		break;
    	case IBDOWNEXCEL:	//엑셀다운로드
    		// check sheet have to data
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}else{
				// download file excel except row hidden and check box
				//sheetObj.Down2Excel({DownCols: "Msg Cd"|"Msg Type"|"Msg Level"|"Message"|"Description", SheetDesign:1, Merge:1});
				//sheetObj.Down2Excel({DownCols: "err_msg_cd|err_tp_cd|err_lvl_cd|err_msg|err_desc", FileName : "myFile.xlsx", SheetDesign:1});
				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj), FileName : "myFile.xlsx"});
			}
			break;
    	}
    }
    // listener event onChange on sheet to validate
    function sheet1_OnChange(sheetObj,Row,Col){
     // only check column 2
   	 if(Col == 2){
   		 	// get value of cell
			var code=sheetObj.GetCellValue(Row, Col);
			// check if value then validate
			if (code != '') {
				// validate format error message
				if (code.length != 8 || !ComIsAlphabet(code.slice(0,3), "u") || !ComIsNumber(code.slice(3))) {
		    		ComShowCodeMessage('COM132302');
		    		// reset cell value
					sheetObj.SetCellValue(Row, Col,"");
					return;
		    	}
				// check duplicate client side
				for(var int=1; int < sheetObj.RowCount(); int++) {
					// get error of each row
			    	var orlcode=sheetObj.GetCellValue(int, Col);
			    	// check duplicate
					if(code != '' && int != Row && code == orlcode){
						 ComShowCodeMessage('COM131302',code);
						// reset cell value
						 sheetObj.SetCellValue(Row, Col,"");
						 return;
					 }
				 }
			}
   	 }
    }
    // when end Search then close image waiting
    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
    }
    
    function sheet1_OnSaveEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
    }


	/* 개발자 작업  끝 */