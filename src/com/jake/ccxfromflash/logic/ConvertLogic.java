package com.jake.ccxfromflash.logic;

import java.util.ArrayList;
import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.constants.CCXLabelAlignment;
import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.constants.ObjectType;
import com.jake.ccxfromflash.constants.PositionType;
import com.jake.ccxfromflash.model.ccx.CCXAction;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.ccx.CCXLabel;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.model.dom.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.DOMStaticText;
import com.jake.ccxfromflash.util.Util;

/**
 * パースしたXMLデータをcocos2d-x用に変換する
 * @author kuuki_yomenaio
 *
 */
public class ConvertLogic {

	/**
	 * DOMBitmapItemとDOMLayerをマージする
	 * @param domBitmapList
	 * @param domLayerList
	 * @return
	 */
	public List<DOMLayer> mergeDOMXml(List<DOMBitmapItem> domBitmapItemList , List<DOMLayer> domLayerList){
		boolean isSetDom = false;
		// domLayerの順番が正しい
		for(DOMLayer domLayer : domLayerList){
			String layerName = domLayer.getName();
			isSetDom = false;
			for(DOMBitmapItem domBitmapItem : domBitmapItemList){
				String bitmapItemName = domBitmapItem.getName();
				if(bitmapItemName != null && bitmapItemName.equals( layerName )){
					domLayer.setDomBitmapItem(domBitmapItem);
					isSetDom = true;
					break;
				}
			}
			
			if(domLayer.getDomBitmapItem() == null){
				domLayer.setDomBitmapItem(new DOMBitmapItem());
			}
		}
		
		return domLayerList;
	}

	public List<CCXObject> convertCcx(List<DOMLayer> domLayerList){
		List<CCXObject> ccxObjList = new ArrayList<>();

		CCXObject ccx = null;
		DOMBitmapItem dom = null;
		for(DOMLayer domLayer : domLayerList){
			ccx = null;
			if(domLayer == null){
				continue;
			}
			dom = domLayer.getDomBitmapItem();
			
			if(!domLayer.getLayerType().equals("folder")){
				DOMFrame preDomFrame = domLayer.getDomFrameList().get(0);

				for(DOMFrame domFrame : domLayer.getDomFrameList()){
					// 初期位置
					if(ccx == null){
						switch(domFrame.getDomFrameType()){
							case BITMAP:
							case SYMBOL:
								ccx = new CCXObject();
								break;
							case TEXT:
								DOMStaticText domText = (DOMStaticText)domFrame;
								CCXLabel label = new CCXLabel();
								label.setWidth(domText.getWidth());
								label.setHeight(domText.getHeight());
								label.setCharacters(domText.getCharacters());
								label.setAlignment(CCXLabelAlignment.of(domText.getAlignment()));
								label.setFontName(domText.getFontFace());
								label.setSize(domText.getSize());
								
								ccx = label;
								break;
						}
						
						ccx.setName( domLayer.getName() );
						ccx.setIndex( domLayer.getIndex() );
						ccx.setObjType( ObjectType.of( domLayer.getName() ) );
						ccx.setPosType( domLayer.getPosType() );
						ccx.setOpacity( Util.round(domFrame.getAlphaMultiplier() * 255) );

						// anchor を計算しセット
						calcAnchor(ccx, dom, domFrame);

						// scale , rotateを計算しセット
						calcScaleRotation(ccx, dom , domFrame);
						ccx.setScaleX(domFrame.getScaleX());
						ccx.setScaleY(domFrame.getScaleY());
						ccx.setRotate(domFrame.getRotate());

						// anchor , scale計算後、配置位置をセット
						calcPos(ccx, dom, domFrame); // 必ずfalseに落ちるが、初期値として入れる
					}else{
						// animationのために毎回計算が必要。
						calcScaleRotation(ccx , dom ,  domFrame);
					}
					createAction(ccx , domFrame, preDomFrame);
					preDomFrame = domFrame;
				}
				ccxObjList.add(ccx);
			}
		}

		return ccxObjList;
	}

	private void createAction(CCXObject ccx , DOMFrame domFrame , DOMFrame preDomFrame){
		// CCSpawnで並列に行う可能性がある
		CCXActionList actionList = ccx.getActionList();
		/**
		 * 同一キーフレームを打たれた場合対応出来ない。
		 * 全てのListにdelayTimeを入れていく必要あり。
		 * 待機→移動＆回転→待機→…など
		 */
		// 位置がずれている時はMoveBy
		if(domFrame.getTx() != preDomFrame.getTx() || domFrame.getTy() != preDomFrame.getTy()){
			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
			if(delayAction != null){
				actionList.addMoveByList(delayAction);
			}

			CCXAction action = new CCXAction();
			action.setActionType( ActionType.MOVEBY );
			action.setDuration( calcDuration(domFrame , preDomFrame) );
			action.setPosX(Util.roundSF(domFrame.getTx() - preDomFrame.getTx(), 4));
			action.setPosY(Util.roundSF(-(domFrame.getTy() - preDomFrame.getTy()), 4));

			actionList.addMoveByList(action);
		}

		// 大きさがずれている時はScaleTo
		if(domFrame.getScaleX() != preDomFrame.getScaleX() || domFrame.getScaleY() != preDomFrame.getScaleY()){
			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
			if(delayAction != null){
				actionList.addScaleToList(delayAction);
			}

			CCXAction action = new CCXAction();
			action.setActionType( ActionType.SCALETO );
			action.setDuration( calcDuration(domFrame , preDomFrame) );
			action.setScaleX(Util.roundSF(domFrame.getScaleX() , 4));
			action.setScaleY(Util.roundSF(domFrame.getScaleY() , 4));

			actionList.addScaleToList(action);
		}

		// 回転率が違う時はRotateTo
		if(domFrame.getRotate() != preDomFrame.getRotate()) {
			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
			if(delayAction != null){
				actionList.addRotateToList(delayAction);
			}

			CCXAction action = new CCXAction();
			action.setActionType( ActionType.ROTATETO );
			action.setDuration( calcDuration(domFrame , preDomFrame) );
			action.setRotate( Util.round(domFrame.getRotate() - preDomFrame.getRotate()) );

			actionList.addRotateToList(action);
		}

		// 透過率が違う場合はFadeTo
		if(domFrame.getAlphaMultiplier() != preDomFrame.getAlphaMultiplier()) {
			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
			if(delayAction != null){
				actionList.addFadeToList(delayAction);
			}

			CCXAction action = new CCXAction();
			action.setActionType( ActionType.FADETO );
			action.setDuration( calcDuration(domFrame , preDomFrame) );
			action.setAlpha( Util.round(domFrame.getAlphaMultiplier() * 255) );

			actionList.addFadeToList(action);
		}

		// blendModeがあればBlendModeを追加
		// まだ未確認だから使わないで
		if(!domFrame.getBlendMode().equals(preDomFrame.getBlendMode())) {
			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
			if(delayAction != null){
				actionList.addBrendModeList(delayAction);
			}

			CCXAction action = new CCXAction();
			action.setActionType( ActionType.BLENDMODE );
			action.setDuration( 0 );
			if("add".equals(domFrame.getBlendMode())) {
				action.setBrend( "GL_ONE, GL_ONE" );
			}else{
				action.setBrend( "CC_BLEND_SRC, CC_BLEND_DST" );
			}

			actionList.addBrendModeList(action);
		}

		// 全部同じだったらDelayTime
		if(
			   domFrame.getTx() == preDomFrame.getTx()
			&& domFrame.getTy() == preDomFrame.getTy()
			&& domFrame.getScaleX() == preDomFrame.getScaleX()
			&& domFrame.getScaleY() == preDomFrame.getScaleY()
			&& domFrame.getRotate() == preDomFrame.getRotate()
			&& domFrame.getAlphaMultiplier() == preDomFrame.getAlphaMultiplier()
			&& domFrame.getBlendMode().equals(preDomFrame.getBlendMode())
		){
			double duration = calcDuration(domFrame , preDomFrame);
			if(duration > 0){
				CCXAction action = new CCXAction();
				action.setActionType( ActionType.DELAYTIME );

				action.setDuration( duration );

				//ccx.getActionList().addDelayTimeList(action);
				if(actionList.getFadeToList().size() > 0){
					actionList.addDelayTimeList(action);
				}
				if(actionList.getMoveByList().size() > 0){
					actionList.addMoveByList(action);
				}
				if(actionList.getRemoveList().size() > 0){
					actionList.addRemoveToList(action);
				}
				if(actionList.getRotateToList().size() > 0){
					actionList.addRotateToList(action);
				}
				if(actionList.getScaleToList().size() > 0){
					actionList.addScaleToList(action);
				}
			}
		}
	}

	private CCXAction createDelayTime(DOMFrame domFrame , DOMFrame preDomFrame){
		if(!"motion".equals(domFrame.getTweenType())){
			CCXAction action = new CCXAction();
			action.setActionType( ActionType.DELAYTIME );
			//action.setDuration( "" + Util.roundSF((domFrame.getIndex() - preDomFrame.getIndex()) / Config.MAX_FRAME , 1));
			action.setDuration( Util.round(domFrame.getIndex() - preDomFrame.getIndex()));

			return action;
		}
		return null;
	}
	
	/**
	 * positionを計算します。
	 * @param ccx
	 * @param dom
	 * @param domFrame
	 */
	private void calcPos(CCXObject ccx , DOMBitmapItem dom , DOMFrame domFrame){
		switch(ccx.getObjType()){
			case SPRITE:
			case BTN:
			case TEXT:
				calcGameObjectPos(ccx, domFrame);
				break;
			case TILE_SPRITE:
				calcTilePos(ccx, dom, domFrame);
				break;
			default:
		}
		
	}
	
	private void calcGameObjectPos(CCXObject ccx , DOMFrame domFrame){
		double posX = domFrame.getTx() + (domFrame.getTransformationPointX() * ccx.getScaleX());
		double posY = domFrame.getTy() + (domFrame.getTransformationPointY() * ccx.getScaleY());

		ccx.setPosX( Util.round(posX) );
		if(ccx.getPosType() == PositionType.TOP){
			ccx.setPosY( Util.round(domFrame.getTy()) );
		}else{
			double calcPosY = Config.TOTAL_HEIGHT - Util.round(posY);
			ccx.setPosY( calcPosY );
		}
	}
	
	private void calcTilePos(CCXObject ccx , DOMBitmapItem dom , DOMFrame domFrame){
		double radian = domFrame.getRotate() * Math.PI / 180;

		double tx = domFrame.getTx() + Math.cos(radian) * domFrame.getTransformationPointX() * domFrame.getScaleX() - Math.sin(radian) * domFrame.getTransformationPointY() * domFrame.getScaleY();
		double ty = domFrame.getTy() + Math.sin(radian) * domFrame.getTransformationPointX() * domFrame.getScaleX() + Math.cos(radian) * domFrame.getTransformationPointY() * domFrame.getScaleY();
		
		double posX = (ccx.getAnchorX() * dom.getWidth() * ccx.getScaleX());
		double posY = (ccx.getAnchorY() * dom.getHeight() * ccx.getScaleY());
		
		domFrame.setTx(tx);
		domFrame.setTy(ty);

		domFrame.setTx(domFrame.getTx() - posX);
		domFrame.setTy(domFrame.getTy() + posY);

		ccx.setPosX(domFrame.getTx());
		ccx.setPosY(Config.TOTAL_HEIGHT - domFrame.getTy());
	}

	private double calcDuration(DOMFrame domFrame , DOMFrame preDomFrame){
		if(!"motion".equals(domFrame.getTweenType())){
			return 0.0;
		}
		//return Util.roundSF((domFrame.getIndex() - preDomFrame.getIndex()) / Config.MAX_FRAME , 1);
		return domFrame.getIndex() - preDomFrame.getIndex();
	}

	/**
	 * anchorポイントを計算
	 * @param ccx
	 * @param dom
	 * @param domFrame
	 */
	private void calcAnchor(CCXObject ccx , DOMBitmapItem dom , DOMFrame domFrame) {
		double baseValueX = 0.0;
		double baseValueY = 0.0;

		double pointX = domFrame.getTransformationPointX();
		double pointY = domFrame.getTransformationPointY();

		double offsetX = dom.getOffsetX();
		double offsetY = dom.getOffsetY();

		double width = dom.getWidth();
		double height = dom.getHeight();

		// xのanchor
		baseValueX += pointX;
		baseValueX -= offsetX;
		baseValueX = (width - baseValueX) / width;
		baseValueX = 1.0 - baseValueX;

		// yのanchor
		baseValueY += pointY;
		baseValueY -= offsetY;
		baseValueY = (height - baseValueY) / height;

		ccx.setAnchorX(baseValueX);
		ccx.setAnchorY(baseValueY);
	}

	/**
	 * scaleとrotateを計算
	 * @param ccx
	 * @param domFrame
	 */
	private void calcScaleRotation(CCXObject ccx , DOMBitmapItem dom , DOMFrame domFrame){
		double scaleX = 1.0;
		double scaleY = 1.0;
		double rotate = 0.0;

		double a = domFrame.getA();
		double b = domFrame.getB();
		double c = domFrame.getC();
		double d = domFrame.getD();
		
		if(dom != null && dom.getMatrix() != null){
			a = a * dom.getMatrix().getA();
			b = b * dom.getMatrix().getB();
			c = c * dom.getMatrix().getC();
			d = d * dom.getMatrix().getD();
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

		scaleX = Util.roundSF(scaleX, 4);
		scaleY = Util.roundSF(scaleY, 4);
		rotate = Util.roundSF(rotate, 4);

		// actionのため、domFrameでも持つ
		domFrame.setScaleX(scaleX);
		domFrame.setScaleY(scaleY);
		domFrame.setRotate(rotate);

	}

	/**
	 * animationが終わったら元の位置に戻す
	 * @param ccxList
	 */
	public void finishInitPosition(List<CCXObject> ccxList){
		for(CCXObject obj : ccxList){
			double posX = 0;
			double posY = 0;
			double scaleX = 0;
			double scaleY = 0;
			double rotate = 0;
			double opacity = 0;

			CCXActionList actList = obj.getActionList();
			for(CCXAction act : actList.getMoveByList()){
				posX += act.getPosX();
				posY += act.getPosY();
			}

			for(CCXAction act : actList.getScaleToList()){
				scaleX += act.getScaleX();
				scaleY += act.getScaleY();
			}

			for(CCXAction act : actList.getFadeToList()){
				opacity += act.getAlpha();
			}

			for(CCXAction act : actList.getRotateToList()){
				rotate += act.getRotate();
			}

			if(posX != 0 || posY != 0){
				CCXAction act = new CCXAction();
				act.setActionType(ActionType.MOVEBY);
				act.setDuration(0);
				act.setPosX( -Util.roundSF(posX , 4) );
				act.setPosY( -Util.roundSF(posY , 4) );
				actList.addMoveByList(act);
			}

			if(scaleX != 0 || scaleY != 0){
				CCXAction act = new CCXAction();
				act.setActionType(ActionType.SCALETO);
				act.setScaleX( -Util.roundSF(scaleX , 4) );
				act.setScaleY( -Util.roundSF(scaleY , 4) );
				actList.addScaleToList(act);
			}

			if(rotate != 0){
				CCXAction act = new CCXAction();
				act.setActionType(ActionType.ROTATETO);
				act.setRotate( -Util.roundSF(rotate , 4) );
				actList.addRotateToList(act);
			}

			if(opacity != 0){
				CCXAction act = new CCXAction();
				act.setActionType(ActionType.FADETO);
				act.setAlpha(-Util.roundSF(opacity , 4));
				actList.addFadeToList(act);
			}
		}
	}
}
