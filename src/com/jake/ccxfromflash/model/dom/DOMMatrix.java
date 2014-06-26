package com.jake.ccxfromflash.model.dom;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.util.Util;

/**
 * DOM内にあるMatrix
 * @author kuuki_yomenaio
 *
 */
public class DOMMatrix {
	private double a = 1.0;
	private double b = 0.0;
	private double c = 0.0;
	private double d = 1.0;
	private double tx = 0.0;
	private double ty = 0.0;

	public void parse(Element parentElement){
		Element element = (Element)parentElement.getElementsByTagName("Matrix").item(0);
		this.a = Util.getDouble(element , "a", 1.0);
		this.b = Util.getDouble(element , "b", 0.0);
		this.c = Util.getDouble(element , "c", 0.0);
		this.d = Util.getDouble(element , "d", 1.0);
		this.tx = Util.getDouble(element , "tx" , 0.0);
		this.ty = Util.getDouble(element , "ty" , 0.0);
	}

	public boolean isInit(){
		if(a == 1 && b == 0 && c == 0 && d == 1){
			return true;
		}
		return false;
	}

	public void print(int i){
		Util.print("tx[" + tx + "]" , i);
		Util.print("ty[" + ty + "]" , i);
		Util.print("a[" + a + "] b[" + b + "] c[" + c + "] d[" + d + "]" , i);
	}

	public boolean equals(DOMMatrix matrix){
		if( this.a == matrix.getA() &&
			this.b == matrix.getB() &&
			this.c == matrix.getC() &&
			this.d == matrix.getD() &&
			this.tx == matrix.getTx() &&
			this.ty == matrix.getTy()){
			return true;
		}
		return false;
	}

	/**
	 * @return a
	 */
	public double getA() {
		return a;
	}

	/**
	 * @param a セットする a
	 */
	public void setA(double a) {
		this.a = a;
	}

	/**
	 * @return b
	 */
	public double getB() {
		return b;
	}

	/**
	 * @param b セットする b
	 */
	public void setB(double b) {
		this.b = b;
	}

	/**
	 * @return c
	 */
	public double getC() {
		return c;
	}

	/**
	 * @param c セットする c
	 */
	public void setC(double c) {
		this.c = c;
	}

	/**
	 * @return d
	 */
	public double getD() {
		return d;
	}

	/**
	 * @param d セットする d
	 */
	public void setD(double d) {
		this.d = d;
	}

	/**
	 * @return tx
	 */
	public double getTx() {
		return tx;
	}

	/**
	 * @param tx セットする tx
	 */
	public void setTx(double tx) {
		this.tx = tx;
	}

	/**
	 * @return ty
	 */
	public double getTy() {
		return ty;
	}

	/**
	 * @param ty セットする ty
	 */
	public void setTy(double ty) {
		this.ty = ty;
	}

}
