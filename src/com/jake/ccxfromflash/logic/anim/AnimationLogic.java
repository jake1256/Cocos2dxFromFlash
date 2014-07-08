package com.jake.ccxfromflash.logic.anim;

import java.util.ArrayList;
import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.item.DOMItem;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.util.DOMCalc;
import com.jake.ccxfromflash.util.Util;

/**
 * アニメーション作成ロジック
 * @author kuuki_yomenaio
 *
 */
public abstract class AnimationLogic {

	protected boolean isDelayOnly = true; // delay timeしか無いじゃん

	/**
	 * 作成するアニメーションを生成
	 * @param preDom
	 * @param dom
	 * @return
	 */
	public abstract CCXAction createAction(DOMFrame preDom , DOMFrame dom);

	/**
	 * どこのリストに入れるか
	 * @param actionList
	 * @param createActionList
	 */
	public abstract void setList(CCXActionList actionList , List<CCXAction> createActionList);

	/**
	 * 前が空白フレーム
	 * @param preDom
	 * @param dom
	 * @return
	 */
	public CCXAction whiteFrameIsBefore(DOMFrame preDom , DOMFrame dom){ return null; }

	/**
	 * 次が空白フレーム
	 * @param preDom
	 * @param dom
	 * @return
	 */
	public CCXAction whiteFrameIsNext(DOMFrame preDom , DOMFrame dom){ return null; }

	public List<CCXAction> createAnimation(CCXActionList actionList , DOMLayer domLayer){
		List<CCXAction> createActionList = new ArrayList<CCXAction>();
		final DOMFrame initDom = findInitDOMFrame(domLayer.getDomFrameList());
		DOMItem domItem = domLayer.getDomItem();
		// 位置だけ正式版に調整
		domLayer.getDomFrameList().forEach((dom)->{
			if(!dom.equals(initDom)){
				DOMCalc.calcScaleRotation(domItem, dom);
				DOMCalc.calcDomFramePos(dom);
			}
		});

		// 初期位置をセットする

		DOMFrame preDom = findInitDOMFrame(domLayer.getDomFrameList());
		CCXAction act = null;

		// ぐぬぬ、closureにするとpreDom操作がしづらい
		for(DOMFrame dom : domLayer.getDomFrameList()){

			DomObjectType objType = dom.getDomObject().getDomObjectType();

			// 次が空白フレームだった
			CCXAction whiteNext = whiteFrameIsNext(preDom, dom);
			if(whiteNext != null){
				createActionList.add(whiteNext);
			}


			// 最初が空白の場合
			if(objType == DomObjectType.WHITE){
				act = createDelayTime(dom.getDuration());
			}
			else{
				act = createAction(preDom, dom);

				// 前が空白フレームだった
				CCXAction whiteBefore = whiteFrameIsBefore(preDom, dom);
				if(whiteBefore != null){
					createActionList.add(whiteBefore);
				}


				preDom = dom;
			}

			if(act != null){
				createActionList.add(act);
			}


		}

		// 最終待機を生成
		act = createLastDelayTime(domLayer);
		if(act != null){
			createActionList.add(act);
		}

		setList(actionList, createActionList);

		return createActionList;
	}


	/**
	 * 全体のフレーム数から最後に待機するアニメーションを生成する
	 * @param domLayer
	 * @return
	 */
	protected CCXAction createLastDelayTime(DOMLayer domLayer){
		int size = domLayer.getDomFrameList().size();
		DOMFrame frame = domLayer.getDomFrameList().get( size - 1 ); // 最終フレームを取得する

		int index = frame.getIndex();
		int duration = frame.getDuration();

		if((index + duration) < Config.MAX_FRAME){
			int delayTime = Config.MAX_FRAME - (index + duration);
			return createDelayTime(delayTime);
		}

		return null;
	}

	/**
	 * 待機アニメーションを作成
	 * @param duration
	 * @return
	 */
	protected CCXAction createDelayTime(int duration){
		CCXAction action = new CCXAction();
		action.setActionType( ActionType.DELAY_TIME );
		action.setDuration( Util.roundSF(duration , 4));

		return action;
	}

	/**
	 * Flash全体のFrame長さを取得する
	 * @param domLayer
	 * @return
	 */
	public int findMaxFrame(List<DOMLayer> domLayerList){

		int maxFrame = 0;

		for(DOMLayer dom : domLayerList){
			List<DOMFrame> domFrameList = dom.getDomFrameList();
			for(DOMFrame frame : domFrameList){
				if(maxFrame < frame.getIndex() + frame.getDuration()){
					maxFrame = (frame.getIndex() + frame.getDuration());
				}
			}
		}

		return maxFrame;
	}

	/**
	 * 初期化位置を特定するDOMFrameを探す
	 * @param domFrameList
	 * @return
	 */
	public DOMFrame findInitDOMFrame(List<DOMFrame> domFrameList){

		for(DOMFrame frame : domFrameList){
			DOMObject domObj = frame.getDomObject();
			if(domObj != null && domObj.getDomObjectType() != DomObjectType.WHITE){
				return frame;
			}
		}
		return domFrameList.get(0);
	}
}
