package com.jake.ccxfromflash.print;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.model.ccx.CCXLabel;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.util.Util;

public class CCXTextPrint extends CCXPrintAbstract{

	@Override
	public String print(CCXObject ccx, String name, CCXVersionType verType) {
		StringBuilder sb = new StringBuilder();
		CCXLabel lbl = (CCXLabel)ccx;
		if(Config.isVer3_0){
			Util.appendStr(sb , "auto " + name + " = Label::createWithSystemFont(\"" + lbl.getCharacters() + "\", \"" + lbl.getFontName() + "\", " + lbl.getSize() + ");");
			switch(lbl.getAlignment()){
			case LEFT:
				Util.appendStr(sb, name + "->setHorizontalAlignment(TextHAlignment::LEFT);");
				break;
			case CENTER:
				Util.appendStr(sb, name + "->setHorizontalAlignment(TextHAlignment::CENTER);");
				break;
			case RIGHT:
				Util.appendStr(sb, name + "->setHorizontalAlignment(TextHAlignment::RIGHT);");
				break;
			}
			Util.appendStr(sb, name + "->setVerticalAlignment(TextVAlignment::CENTER);");
		}else{
			Util.appendStr(sb , "CCLabelTTF * " + name + " = CCLabelTTF::create(\"" + lbl.getCharacters() + "\", \"" + lbl.getFontName() + "\", " + lbl.getSize() + ");");
			switch(lbl.getAlignment()){
			case LEFT:
				Util.appendStr(sb, name + "->setHorizontalAlignment(kCCVerticalTextAlignmentLeft);");
				break;
			case CENTER:
				Util.appendStr(sb, name + "->setHorizontalAlignment(kCCVerticalTextAlignmentCenter);");
				break;
			case RIGHT:
				Util.appendStr(sb, name + "->setHorizontalAlignment(kCCVerticalTextAlignmentRight);");
				break;
			}			
		}
		
		Util.appendStr(sb , name + "->setPosition(" + createPositionString(ccx , verType) + ");");
		Util.appendStr(sb , name + "->setAnchorPoint(" + verType.getCcxPos() + "(0.0 , 1.0));");
		if(ccx.getScaleX() != 1.0) Util.appendStr(sb , name + "->setScaleX(" + ccx.getScaleX() + ");");
		if(ccx.getScaleY() != 1.0) Util.appendStr(sb , name + "->setScaleY(" + ccx.getScaleY() + ");");
		if(ccx.getRotate() != 0.0) Util.appendStr(sb , name + "->setRotation(" + ccx.getRotate() + ");");
		if(ccx.getOpacity() != 255.0) Util.appendStr(sb , name + "->setOpacity(" + ccx.getOpacity() + ");");
		Util.appendStr(sb, name + "->setWidth(" + lbl.getWidth() + ");");
		Util.appendStr(sb , "this->addChild(" + name + ");");
		Util.appendStr(sb, "");
		return sb.toString();
	}

}
