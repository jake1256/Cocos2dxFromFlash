package com.jake.ccxfromflash.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.constants.DomFrameType;
import com.jake.ccxfromflash.constants.ObjectType;
import com.jake.ccxfromflash.constants.PositionType;
import com.jake.ccxfromflash.model.dom.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.DOMStaticText;
import com.jake.ccxfromflash.util.Util;

/**
 * 各XMLをパースする処理
 * @author kuuki_yomenaio
 *
 */
public class ParserLogic {

	/**
	 * DOMBitmapItemをパース
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List<DOMBitmapItem> parseDOMBitmapItem(Document doc) throws Exception{
		List<DOMBitmapItem> result = new ArrayList<>();

		// DOMDocument.xmlからDOMBitmapItemタグ一覧とIncludeタグ一覧を取り出す
		NodeList domBitmapItemList 	= doc.getElementsByTagName("DOMBitmapItem");
		NodeList includeList 		= doc.getElementsByTagName("Include");
		// DOMDocumentを解析
		for(int i = 0; i < domBitmapItemList.getLength() ; i++) {
			// DOMBitmapItemタグの中身を取り出していく
			Element domBitmapItemElement = (Element)domBitmapItemList.item(i);

			DOMBitmapItem domBitmapItem = new DOMBitmapItem();


			String fileName = domBitmapItemElement.getAttribute("name");

			// png以外は未対応
			if(!fileName.endsWith(".png")) {
				continue;
			}

			domBitmapItem.setName( fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf(".png")) );

			// DOMDocumentでは画像サイズが何故か20倍として出力されている。(flashの単位なのか？)ので、/20をして実サイズに変換する
			domBitmapItem.setWidth( Util.getDouble(domBitmapItemElement, "frameRight") / 20 );
			domBitmapItem.setHeight( Util.getDouble(domBitmapItemElement, "frameBottom") / 20 );
			domBitmapItem.setPath(fileName);

			// offsetを探すため、さらに深く潜る
			Document libraryDoc = searchLibraryXmlDocumentSymbolName(includeList , fileName);
			if(libraryDoc != null){
				// シンボル化した時に名前が一致していないと取れない
				parseLibraryXML(libraryDoc , domBitmapItem);
			}
			
			result.add(domBitmapItem);
		}

		return result;
	}

	/**
	 * ～/LIBRARY/以下のxmlファイルを探索して、offsetを取得する
	 * @param domBitmapItem
	 * @throws Exception
	 */
	private void parseLibraryXML(Document includeDoc , DOMBitmapItem domBitmapItem) throws Exception{

		double offsetX = 0;
		double offsetY = 0;
		// LIBRARY内にあるxmlを開く
		NodeList bitmapInstanceList = includeDoc.getElementsByTagName("DOMBitmapInstance");
		// 同じDOMBitmapInstance情報が２つ出力されている事があるので、とりあえず「>=」にする
		if(bitmapInstanceList.getLength() >= 1) {
			Element bitmapElement = (Element) bitmapInstanceList.item(0);
			Element matrix = (Element) bitmapElement.getElementsByTagName("Matrix").item(0);

			if(matrix != null) {
				offsetX = Util.getDouble(matrix, "tx", 0.0);
				offsetY = Util.getDouble(matrix, "ty", 0.0);
			}
		}

		domBitmapItem.setOffsetX(offsetX);
		domBitmapItem.setOffsetY(offsetY);
	}

	/**
	 * LIBRARY配下のxmlを探して同じ名前のモノがあるかを探す。あればそのDocumentを返す
	 * @param searchNodeList
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 */
	private Document searchLibraryXmlDocumentSymbolName(NodeList searchNodeList , String fileName) throws Exception{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		for(int j = 0; j < searchNodeList.getLength(); j++) {
			Element includeItem = (Element) searchNodeList.item(j);
			String includeFileName = includeItem.getAttribute("href");
			Document includeDoc = db.parse(new FileInputStream(Config.ROOT_FOLDER_PATH + "/LIBRARY/" + includeFileName));
			NodeList bitmapInstanceList = includeDoc.getElementsByTagName("DOMBitmapInstance");
			if(bitmapInstanceList.getLength() >= 1) {
				Element bitmapElement = (Element) bitmapInstanceList.item(0);
				String libraryItemName = bitmapElement.getAttribute("libraryItemName");
			
				if(fileName.equals(libraryItemName)) {
					return includeDoc;
				}
			}
		}
		return null;
	}


	/**
	 * DomLayerのパース処理
	 * @param doc
	 * @param fileName
	 * @return
	 */
	public List<DOMLayer> parseDomLayer(Document doc){
		List<DOMLayer> result = new ArrayList<>();

		Element root = doc.getDocumentElement();
		NodeList domLayerList = root.getElementsByTagName("DOMLayer");

		DOMLayer domLayer = null;
		List<DOMFrame> domFrameList = null;
		PositionType posType = null;
		// DOMLayerをパースしていく。後ろから行くのはzOrderのため
		for(int i = 0 ; i <  domLayerList.getLength() ; i++) {
			Element domLayerElement = (Element)domLayerList.item(i);

			domLayer = new DOMLayer();
			domFrameList = new ArrayList<>();

			String nameStr = Util.getString(domLayerElement, "name");
			String colorStr = Util.getString(domLayerElement, "color");
			String layerType = Util.getString(domLayerElement, "layerType");

			if(nameStr.startsWith("guide")){
				continue; // guideは読み飛ばす
			}

			if(layerType != null && "folder".equals(layerType)){
				posType = PositionType.of(nameStr);
			}

			domLayer.setName( nameStr );
			domLayer.setColor( colorStr );
			domLayer.setLayerType(layerType);
			domLayer.setPosType(posType);
			domLayer.setObjType(ObjectType.of(nameStr));


			if(!layerType.equals("folder")){
				NodeList domFrameElementList = domLayerElement.getElementsByTagName("DOMFrame");

				for(int j = 0; j < domFrameElementList.getLength() ; j++) {
					Element domFrameElement 	= (Element)domFrameElementList.item(j);
					
					// DOMFrameをパース
					DOMFrame domFrame = parseDomFrame(domFrameElement);

					domFrameList.add(domFrame);
				}
			}
			
			domLayer.setDomFrameList(domFrameList);
			
			// 同じオブジェクトを利用した時の名前付けのため、indexを振る
			int index = 0;
			for(DOMLayer dom : result){
				if(dom.getName().equals(domLayer.getName())){
					index++;
				}
			}
			domLayer.setIndex(index);
			
			result.add(domLayer);
		}


		return result;
	}
	
	private DOMFrame parseDomFrame(Element domFrameElement){
		DOMFrame domFrame = null;
		DomFrameType domFrameType = null;
		
		// DOMFrameの各要素を取り出す。基本的に1つしかないのでそれを取り出す
		Element domSymbolInstance = (Element) domFrameElement.getElementsByTagName("DOMSymbolInstance").item(0);
		if(domSymbolInstance != null){
			domFrame = new DOMFrame();
			domFrameType = DomFrameType.SYMBOL;
		}else{
			domSymbolInstance = (Element) domFrameElement.getElementsByTagName("DOMBitmapInstance").item(0);

			if(domSymbolInstance != null){
				domFrame = new DOMFrame();
				domFrameType = DomFrameType.BITMAP;
			}else{
				domSymbolInstance = (Element) domFrameElement.getElementsByTagName("DOMStaticText").item(0);
				domFrame = parseDOMFrameText(domSymbolInstance);
				domFrameType = DomFrameType.TEXT;
			}
		}	
		
		domFrame.setDomFrameType(domFrameType);
		domFrame.setIndex( Util.getInt(domFrameElement, "index") );
		domFrame.setDuration( Util.getInt(domFrameElement, "duration") );
		domFrame.setMaxFrame( domFrame.getIndex() + domFrame.getDuration() );
		domFrame.setBlendMode( Util.getString(domSymbolInstance, "blendMode") );
		domFrame.setTweenType( Util.getString(domFrameElement, "tweenType") );
		domFrame.setMotionTweenRotate( Util.getString(domFrameElement, "motionTweenRotate") );
		domFrame.setMotionTweenRotateTimes( Util.getInt(domFrameElement, "motionTweenRotateTimes"));

		domFrame.setCenterPoint3DX( Util.getDouble(domSymbolInstance, "centerPoint3DX", 0.0) );
		domFrame.setCenterPoint3DY( Util.getDouble(domSymbolInstance, "centerPoint3DY", 0.0) );

		// max frameを求める
		Config.MAX_FRAME = Math.max(domFrame.getMaxFrame(), Config.MAX_FRAME);

		if(domSymbolInstance == null){
			domFrame.setWhiteFrame(true);
		}else{
			parseMatrix(domFrame, domSymbolInstance);
		}
		
		return domFrame;
	}
	
	private DOMStaticText parseDOMFrameText(Element domSymbolInstance){
		DOMStaticText domStaticText = new DOMStaticText();
		
		Element domTextRun = (Element) domSymbolInstance.getElementsByTagName("DOMTextRun").item(0);
		
		//DOMTextAttrs
		Element domTextAttrs = (Element)domTextRun.getElementsByTagName("DOMTextAttrs").item(0);
		//characters
		// なんとFlashは改行入ると別タグに文字列が分割されるという…！
		String characters = "";
		NodeList characterList = domSymbolInstance.getElementsByTagName("characters");
		for(int i = 0 ; i < characterList.getLength() ; i++){
			characters += characterList.item(i).getTextContent();
		}
		
		domStaticText.setWidth( Util.getDouble(domSymbolInstance, "width") );
		domStaticText.setHeight(Util.getDouble(domSymbolInstance, "height"));
		
		// Flashはwordwrapされるが、cocos2d-xはされないので中央寄せでの自動改行位置に差異が出るかも。
		// 手動で改行コードを入れる事をオススメします。
		domStaticText.setCharacters( characters );
		domStaticText.setAlignment( Util.getString(domTextAttrs, "alignment") );
		domStaticText.setSize( Util.getInt(domTextAttrs, "size") );
		domStaticText.setFontFace( Util.getString(domTextAttrs, "face") );
		
		return domStaticText;
	}
	
	private void parseMatrix(DOMFrame domFrame , Element domSymbolInstance){
		// DOMSymbolInstanceから取り出す
		Element matrix 	= (Element)domSymbolInstance.getElementsByTagName("Matrix").item(0);
		Element point 	= (Element)domSymbolInstance.getElementsByTagName("Point").item(0);
		Element color 	= (Element)domSymbolInstance.getElementsByTagName("Color").item(0);

		domFrame.setA( Util.getDouble(matrix, "a", 1.0) );
		domFrame.setB( Util.getDouble(matrix, "b", 0.0) );
		domFrame.setC( Util.getDouble(matrix, "c", 0.0) );
		domFrame.setD( Util.getDouble(matrix, "d", 1.0) );
		domFrame.setTx( Util.getDouble(matrix, "tx" , 0.0) );
		domFrame.setTy( Util.getDouble(matrix, "ty" , 0.0) );

		domFrame.setTransformationPointX( Util.getDouble(point, "x", 0.0) );
		domFrame.setTransformationPointY( Util.getDouble(point, "y", 0.0) );

		domFrame.setAlphaMultiplier( Util.getDouble(color, "alphaMultiplier", 1.0) );
	}
	
	
}
