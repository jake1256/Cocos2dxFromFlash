package com.jake.ccxfromflash.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.jake.ccxfromflash.constants.CCXVersionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.print.CCXPrintAbstract;
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

		Util.print("");
		Util.print(createSb.toString());
		Util.print("");
		Util.print(createAnim.toString());

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
		CCXObject ccx = null;
		CCXPrintAbstract printCCX;
		String result = null;
		for(int i = ccxObjectList.size() - 1 ; i > -1 ; i--){
			ccx = ccxObjectList.get(i);
			if(ccx != null){
				name = ccx.getName() + ((ccx.getIndex() == 0) ? "" : "_" + ccx.getIndex());
				printCCX = ccx.getObjType().getPrintClass();
				result = printCCX.print(ccx, name, verType);
				sb.append(result);
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
			if(ccx != null){
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
					appendActionStr(sb, actList.getDelayTimeList(), name);
					// move by
					appendActionStr(sb, actList.getMoveByList(), name);

					// fade to
					appendActionStr(sb, actList.getFadeToList(), name);

					// rotate to
					appendActionStr(sb, actList.getRotateToList(), name);

					// scale to
					appendActionStr(sb, actList.getScaleToList(), name);

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
			case DELAY_TIME:
				Util.appendStr(sb , verType.getCcxCC() + "DelayTime::create(" + duration + "),");
				break;
			case MOVE_BY:
				Util.appendStr(sb , verType.getCcxCC() + "MoveBy::create(" + duration + " , " + verType.getCcxPos() + "(" + act.getPosX() + " , " + act.getPosY() + ")),");
				break;
			case SCALE_TO:
				Util.appendStr(sb , verType.getCcxCC() + "ScaleTo::create(" + duration + " , " + act.getScaleX() + " , " + act.getScaleY()  + "),");
				break;
			case ROTATE_BY:
				Util.appendStr(sb , verType.getCcxCC() + "RotateBy::create(" + duration + " , " + act.getRotate() + "),");
				break;
			case FADE_TO:
				Util.appendStr(sb , verType.getCcxCC() + "FadeTo::create(" + duration + " , " + act.getAlpha() + "),");
				break;
			default:
				break;
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
