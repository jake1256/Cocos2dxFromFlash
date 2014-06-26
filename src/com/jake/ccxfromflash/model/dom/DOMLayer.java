package com.jake.ccxfromflash.model.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jake.ccxfromflash.constants.ObjectType;
import com.jake.ccxfromflash.constants.PositionType;
import com.jake.ccxfromflash.model.dom.item.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.item.DOMItem;
import com.jake.ccxfromflash.model.dom.item.DOMSymbolItem;
import com.jake.ccxfromflash.util.Util;

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
	private DOMItem domItem;

	public void parse(Element element){
		this.name				= Util.getString(element , "name");
		this.color 				= Util.getString(element , "color");
		this.layerType 			= Util.getString(element , "layerType");
		this.posType 			= PositionType.of(this.name);
		this.objType 			= ObjectType.of(this.name);
		this.parentLayerIndex 	= Util.getInt(element, "parentLayerIndex" , -1);
		this.domFrameList		= new ArrayList<>();

		if(!this.layerType.equals("folder")){
			NodeList domFrameElementList = element.getElementsByTagName("DOMFrame");

			for(int j = 0; j < domFrameElementList.getLength() ; j++) {
				Element domFrameElement 	= (Element)domFrameElementList.item(j);

				// DOMFrameをパース
				DOMFrame domFrame = new DOMFrame();
				domFrame.parse(domFrameElement);
				domFrameList.add(domFrame);
			}
		}

	}

	public void print(int i){
		Util.print("" , i);
		Util.print("--- DOMLayer name[" + name + "] ---" , i);
		Util.print("color[" + color + "]" , i);

		Util.print("layerType[" + layerType + "]" , i);
		Util.print("posType[" + posType + "]" , i);
		Util.print("objType[" + objType + "]" , i);
		Util.print("parentLayerIndex[" + parentLayerIndex + "]" , i);

		if(domItem instanceof DOMBitmapItem){
			((DOMBitmapItem)domItem).print(i + 1);
		}
		else if(domItem instanceof DOMSymbolItem){
			((DOMSymbolItem)domItem).print(i + 1);
		}

		int index = 0;
		for(DOMFrame domFrame : domFrameList){
			Util.print("" , i);
			Util.print("domFrameList[" + index++ + "]" , i + i);
			domFrame.print(i + 1);
		}

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

	public DOMItem getDomItem() {
		return domItem;
	}

	public void setDomItem(DOMItem domItem) {
		this.domItem = domItem;
	}


}
