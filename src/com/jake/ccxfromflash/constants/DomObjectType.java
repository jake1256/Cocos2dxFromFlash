package com.jake.ccxfromflash.constants;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.model.dom.obj.DOMBitmapInstance;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.model.dom.obj.DOMStaticText;
import com.jake.ccxfromflash.model.dom.obj.DOMSymbolInstance;
import com.jake.ccxfromflash.model.dom.obj.DOMWhiteFrame;

/**
 * DomFrameの種別
 * @author kuuki_yomenaio
 *
 */
public enum DomObjectType {
	/** シンボル、ムービークリップなどのFlash専用オブジェクト型 */
	SYMBOL(0 , DOMSymbolInstance.class),
	/** 画像 */
	BITMAP(1 , DOMBitmapInstance.class),
	/** テキスト */
	TEXT(2 , DOMStaticText.class),
	/** 空白フレーム */
	WHITE(3 , DOMWhiteFrame.class),
	;

	private int value;

	private Class<? extends DOMObject> instance;

	private DomObjectType(int value , Class<? extends DOMObject> instance){
		this.value = value;
		this.instance = instance;
	}

	public static DomObjectType of(Element element){
		Element domSymbolInstanceElement 	= (Element)element.getElementsByTagName("DOMSymbolInstance").item(0);
		Element domBitmapInstanceElement 	= (Element)element.getElementsByTagName("DOMBitmapInstance").item(0);
		Element domStaticTextElement 		= (Element)element.getElementsByTagName("DOMStaticText").item(0);

		if(domSymbolInstanceElement != null){
			return DomObjectType.SYMBOL;
		}

		if(domBitmapInstanceElement != null){
			return DomObjectType.BITMAP;
		}

		if(domStaticTextElement != null){
			return DomObjectType.TEXT;
		}

		return DomObjectType.WHITE;
	}

	public int getValue(){
		return value;
	}

	public DOMObject getInstance(){
		try {
			return instance.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
