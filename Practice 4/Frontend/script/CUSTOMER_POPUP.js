/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CUSTOMER_POPUP.js
*@FileTitle : Customer Popup
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.07
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.07 
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
     * @class CUSTOMER_POPUP : CUSTOMER_POPUP 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */
    function CUSTOMER_POPUP() {
    	this.processButtonClick		= tprocessButtonClick;
    	this.setSheetObject 		= setSheetObject;
    	this.loadPage 				= loadPage;
    	this.initSheet 				= initSheet;
    	this.initControl            = initControl;
    	this.doActionIBSheet 		= doActionIBSheet;
    	this.setTabObject 			= setTabObject;
    	this.validateForm 			= validateForm;
    }
    
    /* 공통전역변수 */
    var ipageNo=1 ;
    var sheetObjects=new Array();
    var sheetCnt=0;
    var selectVal;
    /* 업무별전역변수는 아래 부분에 추가 선언하여 사용한다. */
    /* 버튼클릭이벤트를 받아 처리하는 이벤트핸들러 정의 */
    document.onclick=processButtonClick;
    	/* 버튼 네임으로 구분하여 프로세스를 분기처리하는 이벤트핸들러 */    
    	function processButtonClick(){
    		/***** 탭당 시트가 2개 이상인 경우엔 추가 시트변수 지정하여 사용한 *****/
    		var sheetObject=sheetObjects[0];
    		/*******************************************************/
    		var formObject=document.form;
    		try {
    			var srcName=ComGetEvent("name");
    			/***********************************************************************************************************
               		이미지 클릭 이벤트 처리, popup도 역시 이곳에서 함
                	공통코드: CoFormControl.js 에 정의 되어있습니다. 이 변수를 통하여 ServiceCommand에서 분기 합니다.        
    			 **********************************************************************************************************/
    			/*
               		이곳에 document.form 혹은 document.form[0]식으로 코딩하시는 것을 삼가해 주십시오.
               		메뉴가 적용되면 left_menu.jsp에 form 이 존재할 것이기 때문에 더이상 form[0]이 아닙니다.
               		(순서상도 form[1]이 되겠죠?) 
               		그리고 위에서 with(document.form)라고 했기때문에 (브라우저의 DOM 객체중 특정부분만 잡는다는 의미니깐!)
                	document.form.f_cmd.value=INSERT;   이런식의 코딩은 지양해주십시오.
    			 */
    			switch(srcName) {
                	case "btn_Retrieve":
                		doActionIBSheet(sheetObject,formObject,IBSEARCH);
                		break;
                	case "btn_New":
                		sheetObject.RemoveAll();
    		            formObject.reset();
                		break;
                	case "btn_Close":
                		ComClosePopup(); 
                		break;
                	case "btn_OK":
                		comPopupOK();
                		break;
                	case "btn2_Down_Excel":
                		if(sheetObj.RowCount() < 1){
                			ComShowCodeMessage("COM132501");
                		}else{
                			sheetObject.Down2Excel({ DownCols: makeHiddenSkipCol(sheetObject),Merge:false,TreeLevel:false});
                		} 	    	    	
            	        break;
    			} // end switch
    		}catch(e) {            
    			/*
            		자바 스크립트 에러가 발생할시 오동작이 납니다. 고객에게 이 경우 아래의 메세지가 뿌려지게 해야합니다.
            		물론 화면에서 다음의 메세지를 보시면 무조건 '자바스크립트 에러구나'라고 확인하실수 가 있습니다.
    			 */
    			if( e == "[object Error]") {
    				ComShowMessage(OBJECT_ERROR);
    			} else {
    				ComShowMessage(e.message);
    			}
    		}
    	}
        /**
         * IBSheet Object를 배열로 등록
         * comSheetObject(id)에서 호출한다
         * 향후 다른 항목들을 일괄처리할 필요가 있을 때 배열로 담는 프로세스를 추가할 수 있다
         * 배열은 소스 상단에 정의
         */
        function setSheetObject(sheet_obj){
            sheetObjects[sheetCnt++]=sheet_obj;
        }
        /**
         * Sheet 기본 설정 및 초기화 
         * body 태그의 onLoad 이벤트핸들러 구현
         * 화면을 브라우저에서 로딩한 후에 선처리해야 하는 기능을 추가한다
         */
        function loadPage() {
            for(i=0;i<sheetObjects.length;i++){
            	//khlee-시작 환경 설정 함수 이름 변경
                ComConfigSheet(sheetObjects[i]);
                initSheet(sheetObjects[i],i+1);
                //khlee-마지막 환경 설정 함수 추가
                ComEndConfigSheet(sheetObjects[i]);
                doActionIBSheet(sheetObjects[i],document.form,IBSEARCH);
            } 
    	} 
        /**
         * 시트 초기설정값, 헤더 정의
         * param : sheetObj ==> 시트오브젝트, sheetNo ==> 시트오브젝트 태그의 아이디에 붙인 일련번호
         * 시트가 다수일 경우 시트 수만큼 case를 추가하여 시트 초기화모듈을 구성한다
         */
        function initSheet(sheetObj,sheetNo) {
            var cnt=0;
            //sheetObj.UseUtf8 = true;
            switch(sheetNo) {
                case 1:      //IBSheet1 init
                    with(sheetObj){
                    
                   
                	var HeadTitle = "STS|Select|Country|Sequence";
    				
    				SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});
    				
    	
    				var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
    				var headers = [ { Text : HeadTitle, Align : "Center" }];
    				InitHeaders(headers, info);
    				
    				var cols = [
//    				           	{ Type : "Status", Hidden : 1, Width : 50,  Align : "Center", SaveName : "ibflag" }, 
    				            {Type : "Radio",  Hidden:0, Width:50,  Align:"Center", SaveName:"radio",           KeyField:0, UpdateEdit:1, InsertEdit:1}, 
    				            {Type:"CheckBox", Hidden:0, Width:20,  Align:"Center", SaveName:"checkbox",        KeyField:0, UpdateEdit:1, InsertEdit:1},
    				            {Type : "Text",   Hidden:0, Width:100, Align:"Center", SaveName:"cust_cnt_cd",     KeyField:1, UpdateEdit:0, InsertEdit:1}, 
    				            {Type : "Text",   Hidden:0, Width:100, Align:"Center", SaveName:"cust_seq",        KeyField:1, UpdateEdit:0, InsertEdit:1}
    				           ];
             
                    InitColumns(cols);
                    SetWaitImageVisible(0);
                    SetEditable(1);
                    SetSheetHeight(310);
                    }
	                  break;
	              }
        }
        /* Sheet관련 프로세스 처리 */
        function doActionIBSheet(sheetObj,formObj,sAction) {
            sheetObj.ShowDebugMsg(false);
            switch(sAction) {
              case IBSEARCH: 
            	  ComOpenWait(true);
                    if(!validateForm(sheetObj,formObj,sAction)) {
                        return false;
                    }
                    formObj.f_cmd.value=SEARCH;
                    sheetObj.DoSearch("Customer_POPUPGS.do", FormQueryString(formObj) );
               break;
            }
        }
        function validateForm(sheetObj,formObj,sAction){
            with(formObj){
            return true;
            }
        }
        
        function sheet_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
        	ComOpenWait(false);
        }
