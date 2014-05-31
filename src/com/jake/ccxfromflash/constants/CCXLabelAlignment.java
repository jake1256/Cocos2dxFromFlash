package com.jake.ccxfromflash.constants;

public enum CCXLabelAlignment {
	LEFT(1),
	CENTER(2),
	RIGHT(3)
	;
	private int value;
	
	private CCXLabelAlignment(int value){
		this.value = value;
	}
	
	public static CCXLabelAlignment of(String alignment){
		if(alignment.contains("left")){
			return LEFT;
		}
		else
		if(alignment.contains("center")){
			return CENTER;
		}
		else
		if(alignment.contains("right")){
			return RIGHT;
		}
		return LEFT; // Flashの場合空だと左寄せ
	}
	
	public int getValue(){
		return value;
	}
}
