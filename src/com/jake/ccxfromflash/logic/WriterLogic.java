package com.jake.ccxfromflash.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.constants.PositionType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.util.Util;

/**
 * 出力ロジック
 * @author kuuki_yomenaio
 *
 */
public class WriterLogic {
	
	private CCXVersionType verType;

	public final boolean write(CCXVersionType verType , List<CCXObject> ccxObjectList){
		boolean result = false;
		this.verType = verType;
		
		StringBuilder createSb = createCcxCreateString(ccxObjectList);
		StringBuilder createAnim = createCcxAction(ccxObjectList);

		result = writeFile(createSb, verType.getCreateFileName());
		result = writeFile(createAnim, verType.getAnimFileName());

		return result;
	}

	/**
	 * 初期位置を決め、createする部分を出力
	 * @param ccxObjectList
	 * @return
	 */
	private StringBuilder createCcxCreateString(List<CCXObject> ccxObjectList){
		StringBuilder sb = new StringBuilder();

		String name = null;
		for(int i = ccxObjectList.size() - 1 ; i > -1 ; i--){
			CCXObject ccx = ccxObjectList.get(i);
			switch (ccx.getObjType()) {

				// 出力を振り分ける…振り分ける…頑張る
				case SPRITE:
				case TILE_SPRITE:
				case TEXT:
				case BTN:
					name = ccx.getName() + ((ccx.getIndex() == 0) ? "" : "_" + ccx.getIndex());
					Util.appendStr(sb , verType.getNewSprite() + " " + name + " = " + verType.getCcxCC() + "Sprite::create(\"" + ccx.getName() + ".png\");");
					Util.appendStr(sb , name + "->setPosition(" + createPositionString(ccx) + ");");
					Util.appendStr(sb , name + "->setAnchorPoint(" + verType.getCcxPos() + "(" + Util.roundSF(ccx.getAnchorX() , 4) + ", " + Util.roundSF(ccx.getAnchorY() , 3) + "));");
					if(ccx.getScaleX() != 1.0) Util.appendStr(sb , name + "->setScaleX(" + ccx.getScaleX() + ");");
					if(ccx.getScaleY() != 1.0) Util.appendStr(sb , name + "->setScaleY(" + ccx.getScaleY() + ");");
					if(ccx.getRotate() != 0.0) Util.appendStr(sb , name + "->setRotation(" + ccx.getRotate() + ");");
					if(ccx.getOpacity() != 255.0) Util.appendStr(sb , name + "->setOpacity(" + ccx.getOpacity() + ");");
					Util.appendStr(sb , "this->addChild(" + name + ");");
					Util.appendStr(sb , "");
					break;

	//			case TEXT:
	//				break;
	//			case BTN:
	//				break;
				case NONE:
					break;
				default:
					break;
			}
		}

		return sb;
	}

	/**
	 * actionを出力
	 * @param ccxObjectList
	 * @return
	 */
	private StringBuilder createCcxAction(List<CCXObject> ccxObjectList){
		StringBuilder sb = new StringBuilder();
		for(CCXObject ccx : ccxObjectList){
			CCXActionList actList = ccx.getActionList();
			if(actList.isHasAction()){
				String name = ccx.getName() + ((ccx.getIndex() == 0) ? "" : "_" + ccx.getIndex());
				Util.appendStr(sb , name + "->runAction(");
				if(Config.isRepeatForever){
					Util.appendStr(sb, verType.getCcxCC() + "RepeatForever::create(");
					Util.appendStr(sb, "(" + verType.getCcxCC() + "ActionInterval*)");
				}

				Util.appendStr(sb, verType.getCcxCC() + "Spawn::create(");

				// delay time
				if(actList.getDelayTimeList().size() > 1){
					appendActionStr(sb, actList.getDelayTimeList(), name);
				}
				// move by
				appendActionStr(sb, actList.getMoveByList(), name);

				// fade to
				appendActionStr(sb, actList.getFadeToList(), name);

				// rotate to
				appendActionStr(sb, actList.getRotateToList(), name);

				// scale to
				if(actList.getScaleToList().size() > 1){
					appendActionStr(sb, actList.getScaleToList(), name);
				}

//				// remove
//				appendActionStr(sb, actList.getRemoveList(), name);
//
//				// brendmode
//				appendActionStr(sb, actList.getBrendModeList(), name);


				// CCSpawn閉じる
				Util.appendStr(sb, "NULL)");
				if(Config.isRepeatForever){
					Util.appendStr(sb, ")");
				}
				Util.appendStr(sb, ");");
			}
		}
		return sb;
	}

	/**
	 * 各ActionリストごとにSequenceにする
	 * @param sb
	 * @param actList
	 * @param name
	 */
	private void appendActionStr(StringBuilder sb , List<CCXAction> actList , String name){
		if(actList != null && actList.size() > 0){

			Util.appendStr(sb, verType.getCcxCC() + "Sequence::create(");

			for(CCXAction act : actList){
				switchActionType(sb, act, name);
			}
			Util.appendStr(sb, "NULL),");
		}
	}

	/**
	 * ActionTypeごとに振り分け
	 * @param sb
	 * @param act
	 * @param name
	 */
	private void switchActionType(StringBuilder sb , CCXAction act , String name){
		String duration = act.getDuration() + " / " + Util.round(Config.FPS);
		switch(act.getActionType()) {
			case DELAYTIME:
				Util.appendStr(sb , verType.getCcxCC() + "DelayTime::create(" + duration + "),");
				break;
			case MOVEBY:
				Util.appendStr(sb , verType.getCcxCC() + "MoveBy::create(" + duration + " , " + verType.getCcxPos() + "(" + act.getPosX() + " , " + act.getPosY() + ")),");
				break;
			case SCALETO:
				Util.appendStr(sb , verType.getCcxCC() + "ScaleTo::create(" + duration + " , " + act.getScaleX() + " , " + act.getScaleY()  + "),");
				break;
			case ROTATETO:
				Util.appendStr(sb , verType.getCcxCC() + "RotateBy::create(" + duration + " , " + act.getRotate() + "),");
				break;
			case FADETO:
				Util.appendStr(sb , verType.getCcxCC() + "FadeTo::create(" + duration + " , " + act.getAlpha() + "),");
				break;
			default:
				break;
		}
	}

	/**
	 * top,center,bottomに対応したpositionを出力
	 * @param ccx
	 * @return
	 */
	private String createPositionString(CCXObject ccx) {
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

	/**
	 * ファイルに出力
	 * @param data
	 * @param outputFileName
	 */
	private boolean writeFile(StringBuilder data , String outputFileName){
		PrintWriter pwDefinition = null;
		try {
			pwDefinition = new PrintWriter(new FileWriter(Config.ROOT_FOLDER_PATH + "/" + outputFileName));
			pwDefinition.println(data.toString().trim());
			pwDefinition.flush();
			pwDefinition.close();
			return true;
		} catch(IOException io) {
			io.printStackTrace();
		} finally {
			try {
				if(pwDefinition != null) {
					pwDefinition.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}
}
