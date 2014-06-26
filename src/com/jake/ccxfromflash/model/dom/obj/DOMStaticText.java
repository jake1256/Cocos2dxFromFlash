package com.jake.ccxfromflash.model.dom.obj;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.util.Util;

public class DOMStaticText extends DOMObject{
	private String characters;
	private String alignment;
	private int size;
	private String fontFace;
	private double width;
	private double height;

	public DOMStaticText(){
		this.domObjectType = DomObjectType.TEXT;
	}

	@Override
	public void parse(Element element){
		super.parse(element);

		Element domTextRun = (Element) element.getElementsByTagName("DOMTextRun").item(0);

		//DOMTextAttrs
		Element domTextAttrs = (Element)domTextRun.getElementsByTagName("DOMTextAttrs").item(0);
		//characters
		// なんとFlashは改行入ると別タグに文字列が分割されるという…！
		String characters = "";
		String textContent = null;
		NodeList characterList = element.getElementsByTagName("characters");
		for(int i = 0 ; i < characterList.getLength() ; i++){
			textContent = characterList.item(i).getTextContent().replace("\r", "\\n");
			characters += textContent;
		}

		this.width = Util.getDouble(element, "width");
		this.height = Util.getDouble(element, "height");

		// Flashはwordwrapされるが、cocos2d-xはされないので中央寄せでの自動改行だと位置に差異が出るかも。
		this.characters = characters;
		this.alignment = Util.getString(domTextAttrs, "alignment");
		this.size = Util.getInt(domTextAttrs, "size");
	}

	public String getCharacters() {
		return characters;
	}
	public void setCharacters(String characters) {
		this.characters = characters;
	}
	public String getAlignment() {
		return alignment;
	}
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getFontFace() {
		return fontFace;
	}
	public void setFontFace(String fontFace) {
		this.fontFace = fontFace;
	}
	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}


}
