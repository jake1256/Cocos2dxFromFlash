package com.jake.ccxfromflash.model.dom;

/**
 * DOMLayer内のDOMFrame。1フレーム単位の値
 * @author kuuki_yomenaio
 *
 */
public class DOMFrame {

	private int index;
	private int duration;
	private double maxFrame;
	private String blendMode;
	private String tweenType;
	private String motionTweenRotate;
	private int motionTweenRotateTimes;
	private boolean isWhiteFrame; // 空白のフレーム

	// position
	private double tx;
	private double ty;

	private double centerPoint3DX;
	private double centerPoint3DY;

	// matrix
	private double a;
	private double b;
	private double c;
	private double d;

	private double transformationPointX;
	private double transformationPointY;

	private double alphaMultiplier;

	public void print(){
		System.out.println(" --- index[" + index + "] duration[" + duration + "] --- ");
		System.out.println("maxFrame[" + maxFrame + "] blendMode[" + blendMode + "] tweenType[" + tweenType + "] motionTweenRotate[" + motionTweenRotate + "] motionTweenRotateTimes[" + motionTweenRotateTimes + "] isWhiteFrame[" + isWhiteFrame + "]");
		System.out.println("tx[" + tx + "] ty[" + ty + "] centerPoint3DX[" + centerPoint3DX + "] centerPoint3DY[" + centerPoint3DY + "]");
		System.out.println("a[" + a + "] b[" + b + "] c[" + c + "] d[" + d + "] transformationPointX[" + transformationPointX + "] transformationPointY[" + transformationPointY + "] alpha[" + alphaMultiplier + "]");

	}


	/**
	 * 拡大率x
	 */
	private double scaleX = 0.0;

	/**
	 * 拡大率y
	 */
	private double scaleY = 0.0;

	/**
	 * 回転
	 */
	private double rotate = 0.0;

	/**
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index セットする index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration セットする duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
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
	 * @return transformationPointX
	 */
	public double getTransformationPointX() {
		return transformationPointX;
	}

	/**
	 * @param transformationPointX セットする transformationPointX
	 */
	public void setTransformationPointX(double transformationPointX) {
		this.transformationPointX = transformationPointX;
	}

	/**
	 * @return transformationPointY
	 */
	public double getTransformationPointY() {
		return transformationPointY;
	}

	/**
	 * @param transformationPointY セットする transformationPointY
	 */
	public void setTransformationPointY(double transformationPointY) {
		this.transformationPointY = transformationPointY;
	}

	/**
	 * @return alphaMultiplier
	 */
	public double getAlphaMultiplier() {
		return alphaMultiplier;
	}

	/**
	 * @param alphaMultiplier セットする alphaMultiplier
	 */
	public void setAlphaMultiplier(double alphaMultiplier) {
		this.alphaMultiplier = alphaMultiplier;
	}

	/**
	 * @return tweenType
	 */
	public String getTweenType() {
		return tweenType;
	}

	/**
	 * @param tweenType セットする tweenType
	 */
	public void setTweenType(String tweenType) {
		this.tweenType = tweenType;
	}

	/**
	 * @return motionTweenRotate
	 */
	public String getMotionTweenRotate() {
		return motionTweenRotate;
	}

	/**
	 * @param motionTweenRotate セットする motionTweenRotate
	 */
	public void setMotionTweenRotate(String motionTweenRotate) {
		this.motionTweenRotate = motionTweenRotate;
	}

	/**
	 * @param motionTweenRotateTimes セットする motionTweenRotateTimes
	 */
	public void setMotionTweenRotateTimes(int motionTweenRotateTimes) {
		this.motionTweenRotateTimes = motionTweenRotateTimes;
	}

	/**
	 * @return motionTweenRotateTimes
	 */
	public int getMotionTweenRotateTimes() {
		return motionTweenRotateTimes;
	}

	/**
	 * @return scaleX
	 */
	public double getScaleX() {
		return scaleX;
	}

	/**
	 * @param scaleX セットする scaleX
	 */
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * @return scaleY
	 */
	public double getScaleY() {
		return scaleY;
	}

	/**
	 * @param scaleY セットする scaleY
	 */
	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * @return rotate
	 */
	public double getRotate() {
		return rotate;
	}

	/**
	 * @param rotate セットする rotate
	 */
	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	/**
	 * @param blendMode セットする blendMode
	 */
	public void setBlendMode(String blendMode) {
		this.blendMode = blendMode;
	}

	/**
	 * @return blendMode
	 */
	public String getBlendMode() {
		return blendMode;
	}

	/**
	 * @param maxFrame セットする maxFrame
	 */
	public void setMaxFrame(double maxFrame) {
		this.maxFrame = maxFrame;
	}

	/**
	 * @return maxFrame
	 */
	public double getMaxFrame() {
		return maxFrame;
	}

	/**
	 * @param isWhiteFrame セットする isWhiteFrame
	 */
	public void setWhiteFrame(boolean isWhiteFrame) {
		this.isWhiteFrame = isWhiteFrame;
	}

	/**
	 * @return isWhiteFrame
	 */
	public boolean isWhiteFrame() {
		return isWhiteFrame;
	}



}
