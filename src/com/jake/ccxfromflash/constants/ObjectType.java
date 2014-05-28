package com.jake.ccxfromflash.constants;

import com.jake.ccxfromflash.print.CCXButtonPrint;
import com.jake.ccxfromflash.print.CCXPrintAbstract;
import com.jake.ccxfromflash.print.CCXSpritePrint;
import com.jake.ccxfromflash.print.CCXTextPrint;

/**
 * オブジェクト種別
 * @author kuuki_yomenaio
 *
 */
public enum ObjectType {

	/**
	 * 画像
	 */
	SPRITE(CCXSpritePrint.class),

	/**
	 * タイル型に配置したい画像
	 */
	TILE_SPRITE(CCXSpritePrint.class),

	/**
	 * テキスト
	 */
	TEXT(CCXTextPrint.class),

	/**
	 * ボタン
	 */
	BTN(CCXButtonPrint.class),

	/**
	 * どれでもない
	 */
	NONE(null);

	private Class<? extends CCXPrintAbstract> printClass;

	private ObjectType(Class<? extends CCXPrintAbstract> printClass){
		this.printClass = printClass;
	}

	public static ObjectType of(String name){
		if(name != null){
			if(name.startsWith("txt")) {
				return TEXT;
			}
			else
			if(name.startsWith("btn")){
				return BTN;
			}
			else
			if(name.startsWith("tile")){
				return TILE_SPRITE;
			}
			else{
				return SPRITE;
			}
		}
		return NONE;
	}

	/**
	 * @return the printClass
	 */
	public CCXPrintAbstract getPrintClass() {
		try {
			return printClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
