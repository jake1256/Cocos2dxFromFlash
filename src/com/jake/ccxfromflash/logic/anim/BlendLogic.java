package com.jake.ccxfromflash.logic.anim;

import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.util.Util;

public class BlendLogic extends AnimationLogic{

	@Override
	public CCXAction createAction(DOMFrame preDom, DOMFrame dom) {

		CCXAction act = null;

		DOMObject preDomObj = preDom.getDomObject();
		DOMObject domObj = dom.getDomObject();
		// 透過率が違う場合はFadeTo
		if(domObj.getBlendMode().equals(preDomObj.getBlendMode())) {

			act = new CCXAction();
			act.setActionType( ActionType.BLEND_MODE );
			act.setDuration( Util.roundSF(dom.getIndex() - preDom.getIndex() , 4) );
			if("add".equals(domObj.getBlendMode())) {
				act.setBrend( "GL_ONE, GL_ONE" );
			}else{
				act.setBrend( "CC_BLEND_SRC, CC_BLEND_DST" );
			}
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
			actionList.setBrendModeList(createActionList);
		}
	}

}
