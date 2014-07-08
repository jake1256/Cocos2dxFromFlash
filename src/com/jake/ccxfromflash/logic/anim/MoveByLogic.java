package com.jake.ccxfromflash.logic.anim;

import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.DOMMatrix;
import com.jake.ccxfromflash.util.Util;

/**
 * MoveByアニメーション作成ロジック
 * @author kuuki_yomenaio
 *
 */
public class MoveByLogic extends AnimationLogic{

	@Override
	public CCXAction createAction(DOMFrame preDom, DOMFrame dom) {
		CCXAction act = null;

		DOMMatrix preMatrix = preDom.getDomObject().getMatrix();
		DOMMatrix matrix = dom.getDomObject().getMatrix();

		if(preMatrix.getTx() != matrix.getTx() || preMatrix.getTy() != matrix.getTy()){
			act = new CCXAction();
			act.setActionType( ActionType.MOVE_BY  );
			act.setDuration( Util.roundSF(dom.getIndex() - preDom.getIndex() , 4) );
			act.setPosX( Util.roundSF(  matrix.getTx() - preMatrix.getTx() , 4));
			act.setPosY( Util.roundSF(-(matrix.getTy() - preMatrix.getTy()), 4));

			isDelayOnly = false;
		}
		else if(!preDom.equals(dom)){
			act = createDelayTime(preDom.getDuration());
		}

		return act;
	}

	@Override
	public void setList(CCXActionList actionList , List<CCXAction> createActionList) {
		if(!isDelayOnly){
			actionList.setMoveByList(createActionList);
		}
	}

}
