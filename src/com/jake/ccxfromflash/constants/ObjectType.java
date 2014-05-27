package com.jake.ccxfromflash.constants;

import com.jake.ccxfromflash.print.CCXButtonPrint;
import com.jake.ccxfromflash.print.CCXPrintAbstract;
import com.jake.ccxfromflash.print.CCXSpritePrint;

/**
 * オブジェクト種別
 * @author kuuki_yomenaio
 *
 */
public enum ObjectType {

	/**
	 * 画像
	 */
	SPRITE(new CCXSpritePrint()),

	/**
	 * タイル型に配置したい画像
	 */
	TILE_SPRITE(new CCXSpritePrint()),

	/**
	 * テキスト
	 */
	TEXT(new CCXSpritePrint()),

	/**
	 * ボタン
	 */
	BTN(new CCXButtonPrint()),

	/**
	 * どれでもない
	 */
	NONE(null);
	
	private CCXPrintAbstract printClass;
	
	private ObjectType(CCXPrintAbstract printClass){
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
		return printClass;
	}
}
