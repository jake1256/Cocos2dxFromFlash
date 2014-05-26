package com.jake.ccxfromflash.model.ccx;

import com.jake.ccxfromflash.constants.ActionType;

/**
 * 1つのAction。
 * @author kuuki_yomenaio
 *
 */
public class CCXAction {
	private ActionType actionType;
	private double duration;
	private double posX;
	private double posY;
	private double scaleX;
	private double scaleY;
	private double rotate;
	private double alpha;
	private String brend;

	public CCXAction clone(){
		CCXAction act = new CCXAction();
		act.setActionType(actionType);
		act.setDuration(duration);
		act.setPosX(posX);
		act.setPosY(posY);
		act.setRotate(rotate);
		act.setScaleX(scaleX);
		act.setScaleY(scaleY);
		act.setAlpha(alpha);
		act.setBrend(brend);

		return act;
	}


	/**
	 * @return actionType
	 */
	public ActionType getActionType() {
		return actionType;
	}
	/**
	 * @param actionType セットする actionType
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return duration
	 */
	public double getDuration() {
		return duration;
	}
	/**
	 * @param duration セットする duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	/**
	 * @return posX
	 */
	public double getPosX() {
		return posX;
	}
	/**
	 * @param posX セットする posX
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}
	/**
	 * @return posY
	 */
	public double getPosY() {
		return posY;
	}
	/**
	 * @param posY セットする posY
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}
	/**
	 * @return scaleX
	 */
	public double getScaleX() {
		return scaleX;
	}
	/**
	 * @param scaleX セットする scaleX
	 */
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}
	/**
	 * @return scaleY
	 */
	public double getScaleY() {
		return scaleY;
	}
	/**
	 * @param scaleY セットする scaleY
	 */
	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}
	/**
	 * @return rotate
	 */
	public double getRotate() {
		return rotate;
	}
	/**
	 * @param rotate セットする rotate
	 */
	public void setRotate(double rotate) {
		this.rotate = rotate;
	}
	/**
	 * @return alpha
	 */
	public double getAlpha() {
		return alpha;
	}
	/**
	 * @param alpha セットする alpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	/**
	 * @return brend
	 */
	public String getBrend() {
		return brend;
	}
	/**
	 * @param brend セットする brend
	 */
	public void setBrend(String brend) {
		this.brend = brend;
	}


}
