package com.jake.ccxfromflash.model.ccx;

import java.util.ArrayList;
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

	public CCXActionList(){
		hasAction = false;

		delayTimeList = new ArrayList<>();
		moveByList = new ArrayList<>();
		scaleToList = new ArrayList<>();
		rotateToList = new ArrayList<>();
		fadeToList = new ArrayList<>();
		removeList = new ArrayList<>();
		brendModeList = new ArrayList<>();
	}

	public void addDelayTimeList(CCXAction act){
		setHasAction();
		delayTimeList.add(act);
	}

	public void addMoveByList(CCXAction act){
		setHasAction();
		moveByList.add(act);
	}

	public void addScaleToList(CCXAction act){
		setHasAction();
		scaleToList.add(act);
	}

	public void addRotateToList(CCXAction act){
		setHasAction();
		rotateToList.add(act);
	}

	public void addFadeToList(CCXAction act){
		setHasAction();
		fadeToList.add(act);
	}

	public void addRemoveToList(CCXAction act){
		setHasAction();
		removeList.add(act);
	}

	public void addBrendModeList(CCXAction act){
		setHasAction();
		brendModeList.add(act);
	}

	/**
	 * @return delayTimeList
	 */
	public List<CCXAction> getDelayTimeList() {
		return delayTimeList;
	}

	/**
	 * @return moveByList
	 */
	public List<CCXAction> getMoveByList() {
		return moveByList;
	}

	/**
	 * @return scaleToList
	 */
	public List<CCXAction> getScaleToList() {
		return scaleToList;
	}

	/**
	 * @return rotateToList
	 */
	public List<CCXAction> getRotateToList() {
		return rotateToList;
	}

	/**
	 * @return fadeToList
	 */
	public List<CCXAction> getFadeToList() {
		return fadeToList;
	}

	/**
	 * @return removeList
	 */
	public List<CCXAction> getRemoveList() {
		return removeList;
	}

	/**
	 * @return brendModeList
	 */
	public List<CCXAction> getBrendModeList() {
		return brendModeList;
	}

	/**
	 * @param hasAction セットする hasAction
	 */
	public void setHasAction() {
		if(this.hasAction == false){
			this.hasAction = true;
		}
	}

	/**
	 * @return hasAction
	 */
	public boolean isHasAction() {
		return hasAction;
	}



}
