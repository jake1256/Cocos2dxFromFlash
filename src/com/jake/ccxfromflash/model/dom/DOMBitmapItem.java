package com.jake.ccxfromflash.model.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * DomDocument.xmlのDomBitmapItem
 * @author kuuki_yomenaio
 *
 */
public class DOMBitmapItem {

    private String name;
    private String path;
    private double width;
    private double height;
    private double offsetX;
    private double offsetY;
    // nameが一致するDomLayerを紐付ける
    private List<DOMLayer> domLayerList;
    
    public DOMBitmapItem(){
    	domLayerList = new ArrayList<>();
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
	 * @return path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path セットする path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return offsetX
	 */
	public double getOffsetX() {
		return offsetX;
	}
	/**
	 * @param offsetX セットする offsetX
	 */
	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}
	/**
	 * @return offsetY
	 */
	public double getOffsetY() {
		return offsetY;
	}
	/**
	 * @param offsetY セットする offsetY
	 */
	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}
	/**
	 * @return width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * @param width セットする width
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	/**
	 * @return height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param height セットする height
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the domLayerList
	 */
	public List<DOMLayer> getDomLayerList() {
		return domLayerList;
	}
	/**
	 * @param domLayerList the domLayerList to set
	 */
	public void addDomLayer(DOMLayer domLayer) {
		this.domLayerList.add(domLayer);
	}

    public void print(){
    	System.out.println("--- name[" + name + "] ---");
    	System.out.println("path[" + path + "]");
    	System.out.println("width[" + width + "] height[" + height + "]");
    	System.out.println("offsetX[" + offsetX + "] offsetY[" + offsetY + "]");
    }

}
