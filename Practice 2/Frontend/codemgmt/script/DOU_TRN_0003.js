/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : DOU_TRN_0003.js
 *@FileTitle : Code Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.16
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.05.16
 * 1.0 Creation
=========================================================*/
function DOU_TRN_0003() {
	this.processButtonClick = tprocessButtonClick;
	this.setSheetObject = setSheetObject;
	this.loadPage = loadPage;
	this.initSheet = initSheet;
	this.initControl = initControl;
	this.doActionIBSheet = doActionIBSheet;
	this.setTabObject = setTabObject;
	this.validateForm = validateForm;
}

/* 개발자 작업 */
var sheetObjects = new Array();
var sheetCnt = 0;
document.onclick = processButtonClick;

function processButtonClick() {
	/** **************************************************** */
	var formObj = document.form;
	try {
		var srcName = ComGetEvent("name");
		if (srcName == null) {
			return;
		}
		switch (srcName) {
		case "btn_Retrieve":
			doActionIBSheet(sheetObjects[0], formObj, IBSEARCH);
			break;
		case "btn_Save":
			if (confirm("Do you want to Save?")) {
				if ((sheetObjects[0].RowCount("I") + sheetObjects[0].RowCount("U") + sheetObjects[0].RowCount("D")) > 0) {
					doActionIBSheet(sheetObjects[0], formObj, IBSAVE);
				}
				if ((sheetObjects[1].RowCount("I") + sheetObjects[1].RowCount("U") + sheetObjects[1].RowCount("D")) > 0) {
					doActionIBSheet(sheetObjects[1], formObj, IBSAVE);
				}
			}
			break;
		/** ***************grid button *********************** */
		case "btn_rowadd_mst": // add row
			doActionIBSheet(sheetObjects[0], formObj, IBINSERT);
			break;
		case "btn_rowdelete_mst": // delete row
			doActionIBSheet(sheetObjects[0], formObj, MODIFY01);
			break;
		case "btn_rowadd_dtl": // add row
			doActionIBSheet(sheetObjects[1], formObj, IBINSERT);
			break;
		case "btn_rowdelete_dtl": // delete row
			doActionIBSheet(sheetObjects[1], formObj, MODIFY01);
			break;
		default:
			break;
		}
	} catch (e) {
		if (e == "[object Error]") {
			ComShowCodeMessage('COM12111');
		} else {
			ComShowMessage(e.message);

		}
	}
}

function loadPage() {
	// generate Grid Layout
	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}

	// auto search data after loading finish.
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
}

function initSheet(sheetObj, sheetNo) {
	var cnt = 0;
	switch (sheetNo) {
	case 1: // sheet1 init
		with (sheetObj) {

			var HeadTitle = "|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date";
			var headCount = ComCountHeadTitle(HeadTitle);

			sheetObj.SetConfig({SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0});

			var info = { Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			var headers = [ {Text : HeadTitle, Align : "Center"} ];
			InitHeaders(headers, info);
			var cols = [ {Type:"Status",    Hidden:1,  Width:10,   Align:"Center",  ColMerge:0,   SaveName:"ibflag",          KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:70,   Align:"Center",  ColMerge:0,   SaveName:"ownr_sub_sys_cd", KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:60,   Align:"Center",  ColMerge:0,   SaveName:"intg_cd_id",      KeyField:1,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:200,  Align:"Left",    ColMerge:0,   SaveName:"intg_cd_nm",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:50,   Align:"Center",  ColMerge:0,   SaveName:"intg_cd_len",     KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Combo",     Hidden:0,  Width:100,  Align:"Center",  ColMerge:0,   SaveName:"intg_cd_tp_cd",   KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:150,  Align:"Left",    ColMerge:0,   SaveName:"mng_tbl_nm",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:350,  Align:"Left",    ColMerge:0,   SaveName:"intg_cd_desc",    KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Combo",     Hidden:0,  Width:40,   Align:"Center",  ColMerge:0,   SaveName:"intg_cd_use_flg", KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
			             {Type:"Text",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:"cre_usr_id",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
			             {Type:"Date",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:"cre_dt",          KeyField:0,   CalcLogic:"",   Format:"Ymd",         PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
			             {Type:"Text",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:"upd_usr_id",      KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:0 },
			             {Type:"Date",      Hidden:0,  Width:80,   Align:"Center",  ColMerge:0,   SaveName:"upd_dt",          KeyField:0,   CalcLogic:"",   Format:"Ymd",         PointCount:0,   UpdateEdit:0,   InsertEdit:0 } ];

			
			InitColumns(cols);

			SetColProperty("intg_cd_tp_cd", { ComboText : "General Code|Table Code", ComboCode : "G|T" });
			SetColProperty("intg_cd_use_flg", { ComboText : "Y|N", ComboCode : "Y|N" });

			SetEditable(1);
			SetWaitImageVisible(0);
			SetSheetHeight(240);

		}
		break;
	case 2: // IBSheet2 init
		with (sheetObj) {

			var HeadTitle = "|Cd ID|Cd Val|Display Name|Description Remark|Order";

			SetConfig({ SearchMode : 2, MergeSheet : 5, Page : 20, FrozenCol : 0, DataRowMerge : 0 });

			var info = { Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1 };
			var headers = [ { Text : HeadTitle, Align : "Center" } ];
			InitHeaders(headers, info);
			var cols = [ 
		                 {Type:"Status",    Hidden:1,  Width:10,   Align:"Center",  ColMerge:0,   SaveName:"ibflag",              KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
					     {Type:"Text",      Hidden:1,  Width:10,   Align:"Center",  ColMerge:0,   SaveName:"intg_cd_id",          KeyField:1,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
					     {Type:"Text",      Hidden:0,  Width:60,   Align:"Center",  ColMerge:0,   SaveName:"intg_cd_val_ctnt",    KeyField:1,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:0,   InsertEdit:1 },
					     {Type:"Text",      Hidden:0,  Width:200,  Align:"Center",  ColMerge:0,   SaveName:"intg_cd_val_dp_desc", KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
					     {Type:"Text",      Hidden:0,  Width:600,  Align:"Left",    ColMerge:0,   SaveName:"intg_cd_val_desc",    KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 },
					     {Type:"Text",      Hidden:0,  Width:50,   Align:"Center",  ColMerge:0,   SaveName:"intg_cd_val_dp_seq",  KeyField:0,   CalcLogic:"",   Format:"",            PointCount:0,   UpdateEdit:1,   InsertEdit:1 } ];
			InitColumns(cols);

			SetEditable(1);
			SetWaitImageVisible(0);
			SetSheetHeight(150);
			resizeSheet();
		}
		break;
	}

}

function resizeSheet() {
	ComResizeSheet(sheetObjects[1]);
}

function setSheetObject(sheet_obj) {
	sheetObjects[sheetCnt++] = sheet_obj;
}

function doActionIBSheet(sheetObj, formObj, sAction) {
	sheetObj.ShowDebugMsg();
	switch (sAction) {
	case IBSEARCH: // retrieve
		if (sheetObj.id == "sheet1") {
			formObj.f_cmd.value = SEARCH01;
			ComOpenWait(true);
			sheetObj.DoSearch("DOU_TRN_0003GS.do", FormQueryString(formObj));
		} else if (sheetObj.id == "sheet2") {
			formObj.f_cmd.value = SEARCH02;
			ComOpenWait(true);
			sheetObj.DoSearch("DOU_TRN_0003GS.do", FormQueryString(formObj));
		}
		break;
	case IBSAVE:// 저장
		if (sheetObj.id == "sheet1") {
			formObj.f_cmd.value = MULTI01;
		} else {
			formObj.f_cmd.value = MULTI02;
		}
		sheetObj.DoSave("DOU_TRN_0003GS.do", FormQueryString(formObj), -1,
				false); // prevent confirm default in DoSave
		break;
	case IBINSERT: // Row Add
		with (sheetObj) {
			sheetObj.DataInsert(-1);
			if (sheetObj.id == "sheet1") {
				sheetObjects[1].RemoveAll();
				formObj.codeID.value = '';
			}
			if (sheetObj.id == "sheet2") {
				if (sheetObj.SearchRows() == 0) {
					SetCellValue(LastRow(), "intg_cd_id", sheetObjects[0].GetCellValue(sheetObjects[0].GetSelectRow(), "intg_cd_id"));
				} else {
					SetCellValue(LastRow(), "intg_cd_id", sheetObj.GetCellValue(1, "intg_cd_id"));
				}
			}
			return true;
		}
		break;
	case MODIFY01: // Row Delete

		var j = sheetObj.GetSelectRow();
		sheetObj.SetCellValue(j, "ibflag", "D");
		sheetObj.SetRowHidden(j, 1);
		// check if delete row in sheet1 then delete all row are in sheet2
		if (sheetObj.id == "sheet1") {
			var codeid = sheetObj.GetCellValue(j, "intg_cd_id");
			if (sheetObjects[1].RowCount() > 0
					&& codeid == document.form.codeID.value) {
				for (i = sheetObjects[1].LastRow(); i > 0; i--) {
					sheetObjects[1].SetCellValue(i, "ibflag", "D");
					sheetObjects[1].SetRowHidden(i, 1);
				}
			}
		}
		break;
	default:
		break;
	}
}
// listener event double click on sheet1
function sheet1_OnDblClick(sheetObj, Row, Col) {
	// check new insert row then do not this action
	if (sheetObj.GetCellValue(Row, "ibflag") != 'I') {
		ComSetObjValue(document.form.codeID, sheetObj.GetCellValue(Row, "intg_cd_id"));
		doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
	}
}

function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
	ComOpenWait(false);
}
function sheet2_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
	ComOpenWait(false);
}
