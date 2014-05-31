package com.jake.ccxfromflash.constants;

/**
 * DomFrameの種別
 * @author jake
 *
 */
public enum DomFrameType {
	/** シンボル、ムービークリップなどのFlash専用オブジェクト型 */
	SYMBOL(0),
	/** 画像 */
	BITMAP(1),
	/** テキスト */
	TEXT(2)
	;
	
	private int value;
	
	private DomFrameType(int value){
		this.value = value;
	}
	
	public static DomFrameType of(int value){
		for(DomFrameType frameType : values()){
			if(frameType.getValue() == value){
				return frameType;
			}
		}
		throw new IllegalArgumentException("Is not DomFrameType value == [" + value + "]");
	}
	
	public int getValue(){
		return value;
	}
}
