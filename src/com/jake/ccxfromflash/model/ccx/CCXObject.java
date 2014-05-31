package com.jake.ccxfromflash.model.ccx;

import com.jake.ccxfromflash.constants.ObjectType;
import com.jake.ccxfromflash.constants.PositionType;

/**
 * cocos2d-x GameObject
 * @author kuuki_yomenaio
 *
 */
public class CCXObject {

	/**
	 * オブジェクト名（ファイル名)
	 */
	private String name;

	/**
	 * オブジェクト出現番号
	 */
	private int index;

	/**
	 * オブジェクトタイプ
	 */
	private ObjectType objType;

	/**
	 * 配置位置タイプ
	 */
	private PositionType posType;

	/**
	 * position x
	 */
	private double posX = 0.0;

	/**
	 * position y
	 */
	private double posY = 0.0;

	/**
	 * 拡大率x
	 */
	private double scaleX = 0.0;

	/**
	 * 拡大率y
	 */
	private double scaleY = 0.0;

	/**
	 * anchor X
	 */
	private double anchorX = 0.0;

	/**
	 * anchor Y
	 */
	private double anchorY = 0.0;

	/**
	 * 回転
	 */
	private double rotate = 0.0;

	/**
	 * 透過率
	 */
	private double opacity = 0.0;
	
	/**
	 * action list
	 */
	private CCXActionList actionList;

	public CCXObject(){
		actionList = new CCXActionList();
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return objType
	 */
	public ObjectType getObjType() {
		return objType;
	}

	/**
	 * @param objType セットする objType
	 */
	public void setObjType(ObjectType objType) {
		this.objType = objType;
	}

	/**
	 * @return posType
	 */
	public PositionType getPosType() {
		return posType;
	}

	/**
	 * @param posType セットする posType
	 */
	public void setPosType(PositionType posType) {
		this.posType = posType;
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
	 * @return anchorX
	 */
	public double getAnchorX() {
		return anchorX;
	}

	/**
	 * @param anchorX セットする anchorX
	 */
	public void setAnchorX(double anchorX) {
		this.anchorX = anchorX;
	}

	/**
	 * @return anchorY
	 */
	public double getAnchorY() {
		return anchorY;
	}

	/**
	 * @param anchorY セットする anchorY
	 */
	public void setAnchorY(double anchorY) {
		this.anchorY = anchorY;
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
	 * @param opacity セットする opacity
	 */
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	/**
	 * @return opacity
	 */
	public double getOpacity() {
		return opacity;
	}

	/**
	 * @return actionList
	 */
	public CCXActionList getActionList() {
		return actionList;
	}

	/**
	 * @param actionList セットする actionList
	 */
	public void setActionList(CCXActionList actionList) {
		this.actionList = actionList;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}




}
