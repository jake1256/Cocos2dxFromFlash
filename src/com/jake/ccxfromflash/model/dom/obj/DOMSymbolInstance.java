package com.jake.ccxfromflash.model.dom.obj;

import com.jake.ccxfromflash.constants.DomObjectType;

public class DOMSymbolInstance extends DOMObject{

	private DOMBitmapInstance domBitmap;

	public DOMSymbolInstance(){
		this.domObjectType = DomObjectType.SYMBOL;
	}

	public DOMBitmapInstance getDomBitmap() {
		return domBitmap;
	}

	public void setDomBitmap(DOMBitmapInstance domBitmap) {
		this.domBitmap = domBitmap;
	}


}
