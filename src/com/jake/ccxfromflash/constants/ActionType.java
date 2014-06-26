package com.jake.ccxfromflash.constants;

import com.jake.ccxfromflash.logic.anim.AnimationLogic;
import com.jake.ccxfromflash.logic.anim.BlendLogic;
import com.jake.ccxfromflash.logic.anim.FadeToLogic;
import com.jake.ccxfromflash.logic.anim.MoveByLogic;
import com.jake.ccxfromflash.logic.anim.RotateByLogic;
import com.jake.ccxfromflash.logic.anim.ScaleToLogic;

/**
 * Cocos2dx actin type
 * @author kuuki_yomenaio
 *
 */
public enum ActionType {
	/**
	 * 待機(待機だけするアニメーション…あるか…？)
	 */
	DELAY_TIME(0 , AnimationLogic.class),

	/**
	 * 移動
	 */
	MOVE_BY(1 , MoveByLogic.class),

	/**
	 * 拡大
	 */
	SCALE_TO(2 , ScaleToLogic.class),

	/**
	 * 回転
	 */
	ROTATE_BY(3 , RotateByLogic.class),

	/**
	 * 透過
	 */
	FADE_TO(4 , FadeToLogic.class),

	/**
	 * ブレンド
	 */
	BLEND_MODE(5 , BlendLogic.class) // ブレンド
	;

	private int value;
	private Class<? extends AnimationLogic> animLogic;

	private ActionType(int value , Class<? extends AnimationLogic> animLogic){
		this.value = value;
		this.animLogic = animLogic;
	}

	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return animLogic
	 */
	public AnimationLogic getAnimLogic() {
		try {
			return animLogic.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


}
