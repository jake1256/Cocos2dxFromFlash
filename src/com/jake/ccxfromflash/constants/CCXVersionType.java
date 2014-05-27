package com.jake.ccxfromflash.constants;

/**
 * CCX version 種別
 * @author kuuki_yomenaio
 *
 */
public enum CCXVersionType {
	/** cocos2d-x v2.x系の出力文字列 */
	CCX_2X( "outputCreate_v2.txt" , 
			"outputAnimation_v2.txt" , 
			"CC" , 
			"ccp" , 
			"CCSprite*" , 
			"visibleSize.height"),
			
	/** cocos2d-x v3.x系の出力文字列 */
	CCX_3X( "outputCreate_v3.txt" , 
			"outputAnimation_v3.txt" , 
			"" , 
			"Point" , 
			"auto" , 
			"visibleSize.height")
	;
	
	/** 生成用ファイル名 */
	private String createFileName;
	
	/** アニメ用ファイル名 */
	private String animFileName;
	
	/** 頭にCC付けるか否か */
	private String ccxCC;
	
	/** ccpかPointか */
	private String ccxPos;
	
	/** Sprite作成型 */
	private String newSprite;
	
	/** windowの高さを取得 */
	private String winHeight;
	
	private CCXVersionType(	String createFileName , 
							String animFileName , 
							String ccxCC , 
							String ccxPos , 
							String newSprite ,
							String winHeight){
		this.createFileName = createFileName;
		this.animFileName = animFileName;
		this.ccxCC = ccxCC;
		this.ccxPos = ccxPos;
		this.newSprite = newSprite;
		this.winHeight = winHeight;
	}
	
	public String getCreateFileName() {
		return createFileName;
	}

	public String getAnimFileName() {
		return animFileName;
	}

	public String getCcxCC() {
		return ccxCC;
	}

	public String getCcxPos() {
		return ccxPos;
	}

	public String getNewSprite() {
		return newSprite;
	}
	
	public String getWinHeight() {
		return winHeight;
	}
}
