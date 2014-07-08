package com.jake.ccxfromflash.util;

import java.util.List;

import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.constants.DOMItemType;
import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.constants.PositionType;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.DOMMatrix;
import com.jake.ccxfromflash.model.dom.DOMTransformationPoint;
import com.jake.ccxfromflash.model.dom.item.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.item.DOMItem;
import com.jake.ccxfromflash.model.dom.item.DOMSymbolItem;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;

/**
 * DOM計算関係Util
 * @author kuuki_yomenaio
 *
 */
public class DOMCalc {

	/**
	 * 初期化位置を特定するDOMFrameを探す
	 * @param domFrameList
	 * @return
	 */
	public static DOMFrame findInitDOMFrame(List<DOMFrame> domFrameList){

		for(DOMFrame frame : domFrameList){
			DOMObject domObj = frame.getDomObject();
			if(domObj != null && domObj.getDomObjectType() != DomObjectType.WHITE){
				return frame;
			}
		}
		return domFrameList.get(0);
	}

	/**
	 * domFrameのポジションを微調整する。flashとccxの挙動の差異を埋める
	 * @param domFrame
	 */
	public static void calcDomFramePos(DOMFrame domFrame){
		DOMObject domObj = domFrame.getDomObject();
		DOMMatrix matrix = domObj.getMatrix();
		DOMTransformationPoint tran = domObj.getTran();

		double x = matrix.getTx();
		double y = matrix.getTy();
		double tranX = tran.getX();
		double tranY = tran.getY();
		double scaleX = domObj.getScaleX();
		double scaleY = domObj.getScaleY();
		double rotate = domObj.getRotate();

		if(tranX != 0 || tranY != 0){
			double radian = rotate * Math.PI / 180;

			x += Math.cos(radian) * tranX * scaleX - Math.sin(radian) * tranY * scaleY;
        	y += Math.sin(radian) * tranX * scaleX + Math.cos(radian) * tranY * scaleY;

        	matrix.setTx(x);
        	matrix.setTy(y);
		}
	}

	/**
	 * GameObjectの位置を求める
	 * @param ccx
	 * @param dom
	 * @param domFrame
	 */
	public static void calcGameObjectPos(CCXObject ccx , DOMItem dom , DOMFrame domFrame){

		DOMObject domObj = domFrame.getDomObject();
		DOMMatrix mainMatrix = domObj.getMatrix();

		double x = mainMatrix.getTx();
		double y = mainMatrix.getTy();

		double posX = x;
		double posY = y;

		ccx.setPosX( Util.round(posX) );
		if(ccx.getPosType() == PositionType.TOP){
			ccx.setPosY( Util.roundSF(posY , 4) );
		}else{
			double calcPosY = Config.TOTAL_HEIGHT - Util.roundSF(posY , 4);
			ccx.setPosY( calcPosY );
		}
	}

	/**
	 * anchorポイントを計算
	 * @param ccx
	 * @param dom
	 * @param domFrame
	 */
	public static void calcAnchor(CCXObject ccx , DOMItem dom , DOMFrame domFrame) {
		double baseValueX = 0.0;
		double baseValueY = 1.0;

		if(dom != null && dom instanceof DOMSymbolItem){
			DOMSymbolItem domSymbolItem = (DOMSymbolItem)dom;

			DOMObject domObject = domFrame.getDomObject();
			DOMTransformationPoint tran = domObject.getTran();
			DOMFrame subDomFrame = findInitDOMFrame(domSymbolItem.getDomLayer().getDomFrameList());
			DOMMatrix matrix = subDomFrame.getDomObject().getMatrix();
			DOMItem domItem = domSymbolItem.getDomLayer().getDomItem();
			if(domItem.getDomItemType() == DOMItemType.BITMAP){
				DOMBitmapItem bitmapItem = (DOMBitmapItem)domItem;

				double tranX = tran.getX();
				double tranY = tran.getY();

				double offX = matrix.getTx();
				double offY = matrix.getTy();

				double width = bitmapItem.getWidth();
				double height = bitmapItem.getHeight();

				baseValueX = -(offX / width) + (tranX / width);
				baseValueY = -(offY / height) - (tranY / height);
			}
		}
		ccx.setAnchorX(baseValueX);
		ccx.setAnchorY(baseValueY);
	}

	/**
	 * scaleとrotateを計算
	 * @param domFrame
	 */
	public static void calcScaleRotation(DOMItem dom , DOMFrame domFrame){
		double scaleX = 1.0;
		double scaleY = 1.0;
		double rotate = 0.0;

		DOMObject domObj = domFrame.getDomObject();
		DOMMatrix matrix = domObj.getMatrix();

		double a = matrix.getA();
		double b = matrix.getB();
		double c = matrix.getC();
		double d = matrix.getD();

		if(dom != null && dom instanceof DOMBitmapItem){
			DOMBitmapItem domBitmapItem = (DOMBitmapItem)dom;
			DOMMatrix subMatrix = domBitmapItem.getMatrix();
			if(subMatrix != null && !subMatrix.isInit()){
				a = a * subMatrix.getA();
				b = b * subMatrix.getB();
				c = c * subMatrix.getC();
				d = d * subMatrix.getD();
			}
		}


		if(a == 1.0 && b == 0.0 && c == 0.0 && d == 1.0) {
			// 初期値のまま
		} else {

			// xをscaleX倍、yをscaleY倍してから、θだけ回転させる時の変換行列は
			// a b
			// c d
			// ＝ scaleX * cosθ  -scaleY * sinθ
			//    scaleX * sinθ   scaleY * cosθ
			// よって、tanθ = c / a → θ = atan(c / a)
			// θがわかればscaleXとscaleYもわかる
			double theta = a == 0 ? Math.PI / 2 : Math.atan(c / a);

			rotate = - theta / Math.PI * 180;

			double sin = Math.sin(theta);
			double cos = Math.cos(theta);
			if(Math.abs(sin) > Math.abs(cos)) { // 誤差を小さくするために、絶対値が大きい方を使う
				scaleX = c / sin;
				scaleY = - b / sin;
			} else {
				scaleX = a / cos;
				scaleY = d / cos;
			}

			if(a == 1 && c == 0) {
				scaleX = 1;
			}
			if(b == 0 && d == 1) {
				scaleY = 1;
			}

			if(scaleX < 0 && scaleY < 0) {
				scaleX *= -1;
				scaleY *= -1;
				rotate += 180;
			}
		}

		// scaleXとscaleYの符号が逆の時は、回転量が逆になる
		// Flashはscaleを変えてから回転するのに対し、
		// Cocos2dxは回転してからscaleを変えるため
		if(scaleX * scaleY < 0) {
			rotate *= -1;
		}

		// actionのため、domFrameでも持つ
		domObj.setScaleX(scaleX);
		domObj.setScaleY(scaleY);
		domObj.setRotate(rotate);

	}
}
