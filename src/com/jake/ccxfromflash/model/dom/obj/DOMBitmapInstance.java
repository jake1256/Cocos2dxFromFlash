package com.jake.ccxfromflash.model.dom.obj;

import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.model.dom.item.DOMBitmapItem;

public class DOMBitmapInstance extends DOMObject{

	private DOMBitmapItem domBitmapItem;

	public DOMBitmapInstance(){
		this.domObjectType = DomObjectType.BITMAP;
	}

	public DOMBitmapItem getDomBitmapItem() {
		return domBitmapItem;
	}

	public void setDomBitmapItem(DOMBitmapItem domBitmapItem) {
		this.domBitmapItem = domBitmapItem;
	}


}
