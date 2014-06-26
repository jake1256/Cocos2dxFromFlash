package com.jake.ccxfromflash.model.dom.item;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.constants.DOMItemType;

public abstract class DOMItem {
	protected DOMItemType domItemType;
	
	public abstract void parse(Element element);
	
	public abstract void print(int i);
	
	/**
	 * @return domItemType
	 */
	public DOMItemType getDomItemType() {
		return domItemType;
	}

	/**
	 * @param domItemType セットする domItemType
	 */
	public void setDomItemType(DOMItemType domItemType) {
		this.domItemType = domItemType;
	}

}
