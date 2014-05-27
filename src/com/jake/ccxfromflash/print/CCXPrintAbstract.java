package com.jake.ccxfromflash.print;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.constants.PositionType;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.util.Util;

public abstract class CCXPrintAbstract {
	public abstract String print(CCXObject ccx , String name , CCXVersionType verType);
	
	/**
	 * top,center,bottomに対応したpositionを出力
	 * @param ccx
	 * @return
	 */
	protected String createPositionString(CCXObject ccx , CCXVersionType verType) {
		String positionYStr;
		if(Config.isCenter){
			// centerの時はPositionTypeを無視
			return verType.getCcxPos() + "(" +
					Util.roundSF((ccx.getPosX() - Util.getCenterW()) , 4) +
					", " +
					Util.roundSF((ccx.getPosY() - Util.getCenterH()) , 4) +
					")";
		}else{
			if(ccx.getPosType() == PositionType.TOP){
				positionYStr = verType.getWinHeight() + " - " + ccx.getPosY();
			}
			else if(ccx.getPosType() == PositionType.CENTER){
				positionYStr = "(" + verType.getWinHeight() + " - 960) / 2 + " + ccx.getPosY();
			}
			else{
				positionYStr = "" + ccx.getPosY();
			}
			return verType.getCcxPos() + "(" + ccx.getPosX() + ", " + positionYStr + ")";
		}
	}
}
