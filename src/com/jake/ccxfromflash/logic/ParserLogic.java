package com.jake.ccxfromflash.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.item.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.item.DOMSymbolItem;
import com.jake.ccxfromflash.util.Util;

/**
 * 各XMLをパースする処理
 * @author kuuki_yomenaio
 *
 */
public class ParserLogic {

	/**
	 * DOMBitmapItemタグをパース
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List<DOMBitmapItem> parseDOMBitmapItem(Document doc) throws Exception{

		List<DOMBitmapItem> result = new ArrayList<>();

		NodeList domBitmapItemNodeList 	= doc.getElementsByTagName("DOMBitmapItem");
		List<Element> domBitmapItemList = Util.changeList(domBitmapItemNodeList);

		domBitmapItemList.forEach((element)->{
			DOMBitmapItem domBitmapItem = new DOMBitmapItem();
			domBitmapItem.parse(element);
			result.add(domBitmapItem);
		});

		return result;
	}

	/**
	 * Includeタグをパース
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List<DOMSymbolItem> parseIncludeList(Document doc) throws Exception{
		List<DOMSymbolItem> result  = new ArrayList<>();

		DocumentBuilderFactory dbf 	= DocumentBuilderFactory.newInstance();
		DocumentBuilder db 			= dbf.newDocumentBuilder();

		NodeList includeNodeList 	= doc.getElementsByTagName("Include");
		List<Element> includeList	= Util.changeList(includeNodeList);

		includeList.forEach((element)->{
			try {
				String includeFileName = element.getAttribute("href");
				FileInputStream fis = new FileInputStream(Config.ROOT_FOLDER_PATH + "/LIBRARY/" + includeFileName);
				Optional<Document> includeDoc = Optional.ofNullable(db.parse(fis));

				includeDoc.ifPresent((includeDocElement)->{
					NodeList timeLineList = includeDocElement.getElementsByTagName("DOMTimeline");
					if(timeLineList.getLength() >= 1) {
						Element timeLineElement = (Element)timeLineList.item(0);

						DOMSymbolItem timeLine = new DOMSymbolItem();
						timeLine.parse(timeLineElement);
						//timeLine.print();
						result.add(timeLine);
					}
				});
			} catch (IOException | SAXException e) {
				// 本当は外にthrowしてserviceで受けたい。
				e.printStackTrace();
			}
		});

		return result;
	}

	/**
	 * DOMLayerタグをパース
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List<DOMLayer> parseDOMLayer(Document doc) throws Exception{
		List<DOMLayer> result = new ArrayList<>();

		Element root = doc.getDocumentElement();
		NodeList domLayerNodeList = root.getElementsByTagName("DOMLayer");
		List<Element> domLayerList = Util.changeList(domLayerNodeList);

		domLayerList.forEach((element)->{
			DOMLayer domLayer = new DOMLayer();
			domLayer.parse(element);
			result.add(domLayer);
		});

		return result;
	}

}
