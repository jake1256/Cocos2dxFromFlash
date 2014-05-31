package com.jake.ccxfromflash.model.ccx;

import com.jake.ccxfromflash.constants.CCXLabelAlignment;

public class CCXLabel extends CCXObject{
	/**
	 * 幅
	 */
	private double width = 0.0;
	
	/**
	 * 高さ
	 */
	private double height = 0.0;
	
	/**
	 * フォント名
	 */
	private String fontName;
	
	/**
	 * 表示文字列
	 */
	private String characters;
	
	/**
	 * 段組
	 */
	private CCXLabelAlignment alignment;
	
	/**
	 * フォントサイズ
	 */
	private int size;

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the alignment
	 */
	public CCXLabelAlignment getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(CCXLabelAlignment alignment) {
		this.alignment = alignment;
	}
	
	
}
