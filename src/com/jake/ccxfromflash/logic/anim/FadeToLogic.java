package com.jake.ccxfromflash.logic.anim;

import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.util.Util;

/**
 * Fadeアニメーション作成ロジック
 * @author kuuki_yomenaio
 *
 */
public class FadeToLogic extends AnimationLogic {

	@Override
	public CCXAction createAction(DOMFrame preDom, DOMFrame dom) {
		CCXAction act = null;

		DOMObject preDomObj = preDom.getDomObject();
		DOMObject domObj = dom.getDomObject();
		// 透過率が違う場合はFadeTo
		if(domObj.getAlpha() != preDomObj.getAlpha()) {

			act = new CCXAction();
			act.setActionType( ActionType.FADE_TO );
			act.setDuration( Util.roundSF(dom.getIndex() - preDom.getIndex() , 4) );
			act.setAlpha( Util.roundSF(domObj.getAlpha() , 4) );

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
			actionList.setFadeToList(createActionList);
		}
	}

}
