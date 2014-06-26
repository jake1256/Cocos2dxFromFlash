package com.jake.ccxfromflash.model.dom.obj;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.model.dom.DOMMatrix;
import com.jake.ccxfromflash.model.dom.DOMTransformationPoint;
import com.jake.ccxfromflash.util.Util;

public class DOMObject {
	protected DomObjectType domObjectType;

	protected String libraryItemName;
	protected String name;
	protected String blendMode;
	protected double centerPoint3DX;
	protected double centerPoint3DY;
	protected DOMMatrix matrix;
	protected DOMTransformationPoint tran;
	protected double alpha;

	protected double scaleX;
	protected double scaleY;
	protected double rotate;

	public void parse(Element element){

		this.libraryItemName 	= Util.getString(element, "libraryItemName");
		this.name 				= Util.getString(element, "name");
		this.blendMode 			= Util.getString(element, "blendMode");
		this.centerPoint3DX 	= Util.getDouble(element, "centerPoint3DX");
		this.centerPoint3DY 	= Util.getDouble(element, "centerPoint3DY");

		this.matrix = new DOMMatrix();
		this.matrix.parse(element);

		this.tran = new DOMTransformationPoint();
		this.tran.parse(element);

		Element color 	= (Element)element.getElementsByTagName("Color").item(0);
		this.alpha = Util.getDouble(color, "alphaMultiplier", 1.0);
	}

	public void print(int i){
		Util.print("@@@ DOMObject name[" + this.name + "] start @@@" , i);
		Util.print("domObjectType == " + this.domObjectType , i);
		Util.print("libraryItemName == " + this.libraryItemName , i);
		Util.print("name == " + this.name , i);
		Util.print("blendMode == " + this.blendMode , i);
		Util.print("centerPoint3DX == " + this.centerPoint3DX , i);
		Util.print("centerPoint3DY == " + this.centerPoint3DY , i);
		matrix.print(i);
		tran.print(i);
		Util.print("scaleX == " + this.scaleX , i);
		Util.print("scaleY == " + this.scaleY , i);
		Util.print("rotate == " + this.rotate , i);
		Util.print("alpha == " + this.alpha , i);
		Util.print("@@@ DOMObject name[" + this.name + "] end @@@" , i);
	}

	public boolean equals(DOMObject domObject){
		if( this.libraryItemName.equals(domObject.getLibraryItemName()) &&
			this.name.equals(domObject.getName()) &&
			this.blendMode.equals(domObject.getBlendMode()) &&
			this.centerPoint3DX == domObject.getCenterPoint3DX() &&
			this.centerPoint3DY == domObject.getCenterPoint3DY() &&
			this.alpha == domObject.getAlpha() &&
			this.scaleX == domObject.getScaleX() &&
			this.scaleY == domObject.getScaleY() &&
			this.rotate == domObject.getRotate() &&
			this.matrix.equals(domObject.getMatrix()) &&
			this.tran.equals(domObject.getTran())
			){
			return true;
		}
		return false;
	}


	/**
	 * @return libraryItemName
	 */
	public String getLibraryItemName() {
		return libraryItemName;
	}



	/**
	 * @param libraryItemName セットする libraryItemName
	 */
	public void setLibraryItemName(String libraryItemName) {
		this.libraryItemName = libraryItemName;
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
	 * @return centerPoint3DX
	 */
	public double getCenterPoint3DX() {
		return centerPoint3DX;
	}



	/**
	 * @param centerPoint3DX セットする centerPoint3DX
	 */
	public void setCenterPoint3DX(double centerPoint3DX) {
		this.centerPoint3DX = centerPoint3DX;
	}



	/**
	 * @return centerPoint3DY
	 */
	public double getCenterPoint3DY() {
		return centerPoint3DY;
	}



	/**
	 * @param centerPoint3DY セットする centerPoint3DY
	 */
	public void setCenterPoint3DY(double centerPoint3DY) {
		this.centerPoint3DY = centerPoint3DY;
	}



	/**
	 * @return matrix
	 */
	public DOMMatrix getMatrix() {
		return matrix;
	}



	/**
	 * @param matrix セットする matrix
	 */
	public void setMatrix(DOMMatrix matrix) {
		this.matrix = matrix;
	}



	/**
	 * @return tran
	 */
	public DOMTransformationPoint getTran() {
		return tran;
	}



	/**
	 * @param tran セットする tran
	 */
	public void setTran(DOMTransformationPoint tran) {
		this.tran = tran;
	}



	/**
	 * @return alpha
	 */
	public double getAlpha() {
		return alpha;
	}



	/**
	 * @param alpha セットする alpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}



	/**
	 * @return the domObjectType
	 */
	public DomObjectType getDomObjectType() {
		return domObjectType;
	}

	/**
	 * @param domObjectType the domObjectType to set
	 */
	public void setDomObjectType(DomObjectType domObjectType) {
		this.domObjectType = domObjectType;
	}
	/**
	 * @return the blendMode
	 */
	public String getBlendMode() {
		return blendMode;
	}
	/**
	 * @param blendMode the blendMode to set
	 */
	public void setBlendMode(String blendMode) {
		this.blendMode = blendMode;
	}


	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}
}
