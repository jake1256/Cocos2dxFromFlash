package com.jake.ccxfromflash.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.logic.ConvertLogic;
import com.jake.ccxfromflash.logic.MargeLogic;
import com.jake.ccxfromflash.logic.ParserLogic;
import com.jake.ccxfromflash.logic.WriterLogic;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.item.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.item.DOMSymbolItem;
import com.jake.ccxfromflash.util.Util;

/**
 * flashからccxソースコードを出力する
 * @author kuuki_yomenaio
 *
 */
public class ConvertService {

	private DocumentBuilder db = null;

	private ParserLogic parserLogic;
	private MargeLogic margeLogic;
	private ConvertLogic convertLogic;
	private WriterLogic writerLogic;

	public void execute(String path) throws Exception{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		parserLogic = new ParserLogic();
		margeLogic = new MargeLogic();
		convertLogic = new ConvertLogic();
		writerLogic = new WriterLogic();

		CCXVersionType verType;
		if(Config.isVer3_0){
			verType = CCXVersionType.CCX_3X;
		}else{
			verType = CCXVersionType.CCX_2X;
		}

		Config.ROOT_FOLDER_PATH = path;
		File file = new File(path);
		if(!file.isDirectory()) {
			Config.ROOT_FOLDER_PATH = file.getParent();
		}

		/* DOMDocument.xmlを読み込む. */
		Document doc = db.parse(new FileInputStream(Config.ROOT_FOLDER_PATH + "/DOMDocument.xml"));
		Element root = doc.getDocumentElement();

		// 基礎値をrootタグから取り出す
		Config.FPS 			= Util.getDouble(root, "frameRate", 24);
		Config.TOTAL_HEIGHT = Util.getInt(root, "height");
		Config.TOTAL_WIDTH 	= Util.getInt(root, "width");

		// Flashのデフォルトは550×400
		if(Config.TOTAL_HEIGHT == 0) {
			Config.TOTAL_HEIGHT = 400;
		}
		if(Config.TOTAL_WIDTH == 0){
			Config.TOTAL_WIDTH = 550;
		}

		// DOMElement.xmlをparseしてjavaオブジェクトに変換
		List<DOMBitmapItem> domBitmapItemList 	= parserLogic.parseDOMBitmapItem(doc);
		List<DOMSymbolItem> domSymbolList		= parserLogic.parseIncludeList(doc);
		List<DOMLayer> domLayerList 			= parserLogic.parseDOMLayer(doc);

		// 全ての要素をDOMLayerに紐付ける
		margeLogic.marge(domLayerList, domBitmapItemList, domSymbolList);
		

//		// mergeして、CCX型に変換処理
//		domLayerList 			= convertLogic.mergeDOMXml(domBitmapItemList, domLayerList);
		List<CCXObject> ccxList = convertLogic.convertCcx(domLayerList);
//
//		if(Config.isRepeatForever){
//			convertLogic.finishInitPosition(ccxList);
//		}
//
//		// 書き出す
		writerLogic.write(verType , ccxList);
	}

}
