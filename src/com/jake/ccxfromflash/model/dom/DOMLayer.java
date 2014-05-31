package com.jake.ccxfromflash.model.dom;

import java.util.List;

import com.jake.ccxfromflash.constants.ObjectType;
import com.jake.ccxfromflash.constants.PositionType;

/**
 * DOMDocument.xmlのDOMLayer
 * @author kuuki_yomenaio
 *
 */
public class DOMLayer {
	/** 同じオブジェクトのためのIndex */
	private int index;
	private String name;
	private String layerType;
	private String color;
	private ObjectType objType;
	private PositionType posType;
	private int parentLayerIndex;
	private List<DOMFrame> domFrameList;
	
	/** domBitmapItemとマージする */
	private DOMBitmapItem domBitmapItem;
	
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
	 * @return color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color セットする color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return domFrameList
	 */
	public List<DOMFrame> getDomFrameList() {
		return domFrameList;
	}
	/**
	 * @param domFrameList セットする domFrameList
	 */
	public void setDomFrameList(List<DOMFrame> domFrameList) {
		this.domFrameList = domFrameList;
	}
	/**
	 * @return parentLayerIndex
	 */
	public int getParentLayerIndex() {
		return parentLayerIndex;
	}
	/**
	 * @param parentLayerIndex セットする parentLayerIndex
	 */
	public void setParentLayerIndex(int parentLayerIndex) {
		this.parentLayerIndex = parentLayerIndex;
	}
	/**
	 * @param layerType セットする layerType
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}
	/**
	 * @return layerType
	 */
	public String getLayerType() {
		return layerType;
	}
	/**
	 * @param posType セットする posType
	 */
	public void setPosType(PositionType posType) {
		this.posType = posType;
	}
	/**
	 * @return posType
	 */
	public PositionType getPosType() {
		return posType;
	}
	/**
	 * @return the domBitmapItem
	 */
	public DOMBitmapItem getDomBitmapItem() {
		return domBitmapItem;
	}
	/**
	 * @param domBitmapItem the domBitmapItem to set
	 */
	public void setDomBitmapItem(DOMBitmapItem domBitmapItem) {
		this.domBitmapItem = domBitmapItem;
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
	/**
	 * @return the objType
	 */
	public ObjectType getObjType() {
		return objType;
	}
	/**
	 * @param objType the objType to set
	 */
	public void setObjType(ObjectType objType) {
		this.objType = objType;
	}


}
