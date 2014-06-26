package com.jake.ccxfromflash.logic.anim;

import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.util.Util;

/**
 * Rotateアニメーション作成ロジック
 * @author kuuki_yomenaio
 *
 */
public class RotateByLogic extends AnimationLogic{

	@Override
	public CCXAction createAction(DOMFrame preDom, DOMFrame dom) {
		CCXAction act = null;

		DOMObject preDomObj = preDom.getDomObject();
		DOMObject domObj = dom.getDomObject();

		// 回転率が違う時はRotateTo
		if(preDomObj.getRotate() != domObj.getRotate()) {
			act = new CCXAction();
			act.setActionType( ActionType.ROTATE_BY );
			act.setDuration( Util.roundSF(dom.getIndex() - preDom.getIndex() , 4) );
			act.setRotate( Util.round(domObj.getRotate() - preDomObj.getRotate()) );

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
			actionList.setRotateToList(createActionList);
		}
	}


}
