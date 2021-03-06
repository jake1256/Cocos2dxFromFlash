package com.jake.ccxfromflash.print;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.util.Util;

public class CCXButtonPrint extends CCXPrintAbstract{

	@Override
	public String print(CCXObject ccx, String name, CCXVersionType verType) {
		StringBuilder sb = new StringBuilder();
		if(Config.isVer3_0){
			Util.appendStr(sb , "auto " + name + " = MenuItemImage::create("
													+ "\"" + ccx.getName() + ".png\" , "
													+ "\"" + ccx.getName() + "_push.png\" , "
													+ "CC_CALLBACK_1(NULL, this));");
		}else{
			Util.appendStr(sb, "CCMenuItemImage* " + name + " = CCMenuItemImage::create("
                    											+ "\"" + ccx.getName() + ".png\" , "
                    											+ "\"" + ccx.getName() + "_push.png\" , "
                    											+ "this , "
                    											+ "menu_selector(NULL) );");
		}
		Util.appendStr(sb , name + "->setPosition(" + createPositionString(ccx , verType) + ");");
		Util.appendStr(sb , name + "->setAnchorPoint(" + verType.getCcxPos() + "(" + Util.roundSF(ccx.getAnchorX() , 4) + ", " + Util.roundSF(ccx.getAnchorY() , 4) + "));");
		if(ccx.getScaleX() != 1.0) Util.appendStr(sb , name + "->setScaleX(" + ccx.getScaleX() + ");");
		if(ccx.getScaleY() != 1.0) Util.appendStr(sb , name + "->setScaleY(" + ccx.getScaleY() + ");");
		if(ccx.getRotate() != 0.0) Util.appendStr(sb , name + "->setRotation(" + ccx.getRotate() + ");");
		if(ccx.getOpacity() != 255.0) Util.appendStr(sb , name + "->setOpacity(" + ccx.getOpacity() + ");");
		
		if(Config.isVer3_0){
			Util.appendStr(sb , "auto menu_" + name + " = Menu::create(" + name + " , NULL);");
			Util.appendStr(sb , "menu_" + name + "->setPosition(Point::ZERO);");
		}else{
			Util.appendStr(sb , "CCMenu* menu_" + name + " = CCMenu::create(" + name + " , NULL);");
			Util.appendStr(sb , "menu_" + name + "->setPosition( CCPointZero );");
		}

		Util.appendStr(sb , "this->addChild(menu_" + name + ");");
		Util.appendStr(sb, "");
		return sb.toString();
	}

}
