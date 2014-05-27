package com.jake.ccxfromflash.print;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.util.Util;

public class CCXSpritePrint extends CCXPrintAbstract{

	@Override
	public String print(CCXObject ccx, String name, CCXVersionType verType) {
		StringBuilder sb = new StringBuilder();
		
		Util.appendStr(sb , verType.getNewSprite() + " " + name + " = " + verType.getCcxCC() + "Sprite::create(\"" + ccx.getName() + ".png\");");
		Util.appendStr(sb , name + "->setPosition(" + createPositionString(ccx , verType) + ");");
		Util.appendStr(sb , name + "->setAnchorPoint(" + verType.getCcxPos() + "(" + Util.roundSF(ccx.getAnchorX() , 4) + ", " + Util.roundSF(ccx.getAnchorY() , 3) + "));");
		if(ccx.getScaleX() != 1.0) Util.appendStr(sb , name + "->setScaleX(" + ccx.getScaleX() + ");");
		if(ccx.getScaleY() != 1.0) Util.appendStr(sb , name + "->setScaleY(" + ccx.getScaleY() + ");");
		if(ccx.getRotate() != 0.0) Util.appendStr(sb , name + "->setRotation(" + ccx.getRotate() + ");");
		if(ccx.getOpacity() != 255.0) Util.appendStr(sb , name + "->setOpacity(" + ccx.getOpacity() + ");");
		Util.appendStr(sb , "this->addChild(" + name + ");");
		Util.appendStr(sb , "");
		
		
		return sb.toString();
	}

}
