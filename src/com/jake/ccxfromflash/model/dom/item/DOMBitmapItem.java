package com.jake.ccxfromflash.model.dom.item;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.constants.DOMItemType;
import com.jake.ccxfromflash.model.dom.DOMMatrix;
import com.jake.ccxfromflash.util.Util;

/**
 * DomDocument.xmlのDomBitmapItem
 * @author kuuki_yomenaio
 *
 */
public class DOMBitmapItem extends DOMItem{

    private String name;
    private String path;
    private double width;
    private double height;

    private DOMMatrix matrix;
    
    public DOMBitmapItem(){
    	domItemType = DOMItemType.BITMAP;
    }
    
    @Override
    public void parse(Element element){
    	String fileName = Util.getString(element, "name");

		this.name =  fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf(".png"));

		// DOMDocumentでは画像サイズが何故か20倍として出力されている。(flashの単位なのか？)ので、/20をして実サイズに変換する
		this.width = Util.getDouble(element, "frameRight") / 20;
		this.height =  Util.getDouble(element, "frameBottom") / 20;
		this.path = fileName;

		this.matrix = new DOMMatrix();
		this.matrix.parse(element);
    }

    @Override
    public void print(int i){
    	Util.print("○○○ DOMBitmapItem name [" + name + "] start ○○○" , i);
    	Util.print("path[" + path + "]" , i);
    	Util.print("width[" + width + "]" , i);
    	Util.print("height[" + height + "]" , i);


    	matrix.print(i);

    	Util.print("○○○ DOMBitmapItem name [" + name + "] end ○○○" , i);
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
	 * @return the matrix
	 */
	public DOMMatrix getMatrix() {
		return matrix;
	}

	/**
	 * @param matrix the matrix to set
	 */
	public void setMatrix(DOMMatrix matrix) {
		this.matrix = matrix;
	}

}
