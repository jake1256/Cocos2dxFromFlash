package com.jake.ccxfromflash.constants;

/**
 * オブジェクト種別
 * @author kuuki_yomenaio
 *
 */
public enum ObjectType {

	/**
	 * 画像
	 */
	SPRITE,

	/**
	 * タイル型に配置したい画像
	 */
	TILE_SPRITE,

	/**
	 * テキスト
	 */
	TEXT,

	/**
	 * ボタン
	 */
	BTN,

	/**
	 * どれでもない
	 */
	NONE;

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
}
