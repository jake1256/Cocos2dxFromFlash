package com.jake.ccxfromflash.model.dom.item;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.constants.DOMItemType;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.util.Util;

public class DOMSymbolItem extends DOMItem{
	private String name;
	private DOMLayer domLayer;
	
	public DOMSymbolItem(){
		domItemType = DOMItemType.SYMBOL;
	}
	
	@Override
	public void parse(Element timelineElement){
		this.name = Util.getString(timelineElement , "name");

		domLayer = new DOMLayer();
		domLayer.parse(timelineElement);
	}

	@Override
	public void print(int i){
		Util.print("△△△ DOMSymbolItem name [" + name + "] start △△△" , i);

		domLayer.print(i + 1);
		Util.print("△△△ DOMSymbolItem name [" + name + "] end △△△" , i);
	}

	/**
	 * @return the domLayer
	 */
	public DOMLayer getDomLayer() {
		return domLayer;
	}
	/**
	 * @param domLayer the domLayer to set
	 */
	public void setDomLayer(DOMLayer domLayer) {
		this.domLayer = domLayer;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


}
