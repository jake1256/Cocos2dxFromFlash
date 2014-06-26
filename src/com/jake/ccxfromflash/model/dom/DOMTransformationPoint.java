package com.jake.ccxfromflash.model.dom;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.util.Util;

public class DOMTransformationPoint {
	private double x = 0.0;
	private double y = 0.0;

	public void parse(Element parentElement){
		Element element = (Element)parentElement.getElementsByTagName("Point").item(0);
		this.x = Util.getDouble(element, "x", 0.0);
		this.y = Util.getDouble(element, "y", 0.0);
	}

	public void print(int i){
		Util.print("x == " + x , i);
		Util.print("y == " + y , i);
	}

	public boolean equals(DOMTransformationPoint point){
		if( this.x == point.getX() &&
			this.y == point.getY()){
			return true;
		}
		return false;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

}
