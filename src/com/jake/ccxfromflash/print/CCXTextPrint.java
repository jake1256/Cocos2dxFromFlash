package com.jake.ccxfromflash.print;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.util.Util;

public class CCXTextPrint extends CCXPrintAbstract{

	@Override
	public String print(CCXObject ccx, String name, CCXVersionType verType) {
		StringBuilder sb = new StringBuilder();
		if(Config.isVer3_0){
			Util.appendStr(sb , "auto " + name + " = Label::createWithSystemFont(\"ああああああ\", \"_ゴシック\", 30);");
			Util.appendStr(sb, name + "->setHorizontalAlignment(TextHAlignment::CENTER);");
			Util.appendStr(sb, name + "->setVerticalAlignment(TextVAlignment::CENTER);");
		}else{
			Util.appendStr(sb , "CCLabelTTF * " + name + " = CCLabelTTF::create(\"ああああああ\", \"_ゴシック\", 30);");
			Util.appendStr(sb, name + "->setHorizontalAlignment(kCCVerticalTextAlignmentCenter);");
			Util.appendStr(sb, name + "->setVerticalAlignment(kCCTextAlignmentCenter);");			
		}
		
		Util.appendStr(sb , name + "->setPosition(" + createPositionString(ccx , verType) + ");");
		Util.appendStr(sb , name + "->setAnchorPoint(" + verType.getCcxPos() + "(" + Util.roundSF(ccx.getAnchorX() , 4) + ", " + Util.roundSF(ccx.getAnchorY() , 4) + "));");
		if(ccx.getScaleX() != 1.0) Util.appendStr(sb , name + "->setScaleX(" + ccx.getScaleX() + ");");
		if(ccx.getScaleY() != 1.0) Util.appendStr(sb , name + "->setScaleY(" + ccx.getScaleY() + ");");
		if(ccx.getRotate() != 0.0) Util.appendStr(sb , name + "->setRotation(" + ccx.getRotate() + ");");
		if(ccx.getOpacity() != 255.0) Util.appendStr(sb , name + "->setOpacity(" + ccx.getOpacity() + ");");

		Util.appendStr(sb , "this->addChild(" + name + ");");
		Util.appendStr(sb, "");
		return sb.toString();
	}

}
