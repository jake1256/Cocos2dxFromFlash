package com.jake.ccxfromflash.model.dom;

import org.w3c.dom.Element;

import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.util.Util;

/**
 * DOMLayer内のDOMFrame。1フレーム単位の値
 * @author kuuki_yomenaio
 *
 */
public class DOMFrame {

	private int index;
	private int duration;
	private String tweenType;
	private String motionTweenSnap;

	private DOMObject domObject;

	public void parse(Element element){
		this.index				= Util.getInt(element , "index");
		this.duration 			= Util.getInt(element , "duration");
		this.tweenType 			= Util.getString(element , "tweenType");
		this.motionTweenSnap 	= Util.getString(element , "motionTweenSnap");

		DomObjectType objType = DomObjectType.of(element);
		domObject = objType.getInstance();
		domObject.parse(element);
	}

	public void print(int i){
		Util.print("☆☆☆ DOMFrame [" + index + " - " + duration + "] start ☆☆☆" , i);
		Util.print("index[" + index + "]" , i);
		Util.print("duration[" + duration + "]" , i);
		Util.print("tweenType[" + tweenType + "]" , i);
		Util.print("motionTweenSnap[" + motionTweenSnap + "]" , i);

		domObject.print(i + 1);
		Util.print("☆☆☆ DOMFrame [" + index + "- " + duration + "] end ☆☆☆" , i);
	}

	public boolean equals(DOMFrame domFrame){
		if( this.index == domFrame.getIndex() &&
			this.duration == domFrame.getDuration() &&
			this.tweenType.equals(domFrame.getTweenType()) &&
			this.motionTweenSnap.equals(domFrame.getMotionTweenSnap())
			){
			return true;
		}

		return false;
	}

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
	 * @return motionTweenSnap
	 */
	public String getMotionTweenSnap() {
		return motionTweenSnap;
	}

	/**
	 * @param motionTweenSnap セットする motionTweenSnap
	 */
	public void setMotionTweenSnap(String motionTweenSnap) {
		this.motionTweenSnap = motionTweenSnap;
	}

	/**
	 * @return domObject
	 */
	public DOMObject getDomObject() {
		return domObject;
	}

	/**
	 * @param domObject セットする domObject
	 */
	public void setDomObject(DOMObject domObject) {
		this.domObject = domObject;
	}


}
