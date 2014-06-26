package com.jake.ccxfromflash.model.ccx;

import java.util.List;

/**
 * 1レイヤーに対するActionを保持する
 * @author kuuki_yomenaio
 *
 */
public class CCXActionList {

	private boolean hasAction;

	private List<CCXAction> delayTimeList;
	private List<CCXAction> moveByList;
	private List<CCXAction> scaleToList;
	private List<CCXAction> rotateToList;
	private List<CCXAction> fadeToList;
	private List<CCXAction> removeList;
	private List<CCXAction> brendModeList;




	/**
	 * @return hasAction
	 */
	public boolean isHasAction() {
		return hasAction;
	}
	/**
	 * @param hasAction セットする hasAction
	 */
	public void setHasAction(boolean hasAction) {
		this.hasAction = hasAction;
	}
	/**
	 * @return delayTimeList
	 */
	public List<CCXAction> getDelayTimeList() {
		return delayTimeList;
	}
	/**
	 * @param delayTimeList セットする delayTimeList
	 */
	public void setDelayTimeList(List<CCXAction> delayTimeList) {
		this.delayTimeList = delayTimeList;
	}
	/**
	 * @return moveByList
	 */
	public List<CCXAction> getMoveByList() {
		return moveByList;
	}
	/**
	 * @param moveByList セットする moveByList
	 */
	public void setMoveByList(List<CCXAction> moveByList) {
		hasAction = true;
		this.moveByList = moveByList;
	}
	/**
	 * @return scaleToList
	 */
	public List<CCXAction> getScaleToList() {
		return scaleToList;
	}
	/**
	 * @param scaleToList セットする scaleToList
	 */
	public void setScaleToList(List<CCXAction> scaleToList) {
		hasAction = true;
		this.scaleToList = scaleToList;
	}
	/**
	 * @return rotateToList
	 */
	public List<CCXAction> getRotateToList() {
		return rotateToList;
	}
	/**
	 * @param rotateToList セットする rotateToList
	 */
	public void setRotateToList(List<CCXAction> rotateToList) {
		hasAction = true;
		this.rotateToList = rotateToList;
	}
	/**
	 * @return fadeToList
	 */
	public List<CCXAction> getFadeToList() {
		return fadeToList;
	}
	/**
	 * @param fadeToList セットする fadeToList
	 */
	public void setFadeToList(List<CCXAction> fadeToList) {
		hasAction = true;
		this.fadeToList = fadeToList;
	}
	/**
	 * @return removeList
	 */
	public List<CCXAction> getRemoveList() {
		return removeList;
	}
	/**
	 * @param removeList セットする removeList
	 */
	public void setRemoveList(List<CCXAction> removeList) {
		this.removeList = removeList;
	}
	/**
	 * @return brendModeList
	 */
	public List<CCXAction> getBrendModeList() {
		return brendModeList;
	}
	/**
	 * @param brendModeList セットする brendModeList
	 */
	public void setBrendModeList(List<CCXAction> brendModeList) {
		this.brendModeList = brendModeList;
	}



}
