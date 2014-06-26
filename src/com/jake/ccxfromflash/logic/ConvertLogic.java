package com.jake.ccxfromflash.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jake.ccxfromflash.constants.ActionType;
import com.jake.ccxfromflash.logic.anim.AnimationLogic;
import com.jake.ccxfromflash.model.ccx.CCXActionList;
import com.jake.ccxfromflash.model.ccx.CCXObject;
import com.jake.ccxfromflash.model.dom.DOMFrame;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.item.DOMItem;
import com.jake.ccxfromflash.model.dom.obj.DOMObject;
import com.jake.ccxfromflash.util.DOMCalc;
import com.jake.ccxfromflash.util.Util;


/**
 * パースしたXMLデータをcocos2d-x用に変換する
 * @author kuuki_yomenaio
 *
 */
public class ConvertLogic {

	private List<ActionType> actions = Arrays.asList(ActionType.MOVE_BY , ActionType.ROTATE_BY , ActionType.SCALE_TO , ActionType.FADE_TO);

	/**
	 * DOMLayerのリストをCCXObjectのリストに変換する
	 * @param domLayerList
	 * @return
	 */
	public List<CCXObject> convertCcx(List<DOMLayer> domLayerList){
		List<CCXObject> ccxObjList = new ArrayList<>();

		domLayerList.forEach((domLayer) -> {
			CCXObject ccx = convertDOMLayer(domLayer);
			ccxObjList.add(ccx);
		});

		return ccxObjList;
	}

	/**
	 * domlayer一つをCCXObjectに変換する
	 * @param domLayer
	 * @return
	 */
	private CCXObject convertDOMLayer(DOMLayer domLayer){
		if(!domLayer.getLayerType().equals("folder")){
			CCXObject ccx = initCCXObject(domLayer);

			// create animation
			CCXActionList actionList = new CCXActionList();

			actions.forEach((anim)->{
				AnimationLogic logic = anim.getAnimLogic();
				logic.createAnimation(actionList , domLayer);
			});

			ccx.setActionList(actionList);

			return ccx;
		}
		return null;
	}

	/**
	 * DOMLayerからCCXObjectの初期位置データを作成する
	 * @param domLayer
	 * @return
	 */
	private CCXObject initCCXObject(DOMLayer domLayer){
		CCXObject ccx = new CCXObject();
		DOMItem domItem = domLayer.getDomItem();
		DOMFrame initDOMFrame = DOMCalc.findInitDOMFrame(domLayer.getDomFrameList());
		DOMObject domObject = initDOMFrame.getDomObject();

		ccx.setName( domLayer.getName() );
		ccx.setIndex( initDOMFrame.getIndex() );
		ccx.setObjType( domLayer.getObjType() );
		ccx.setPosType( domLayer.getPosType() );
		ccx.setOpacity( Util.round(domObject.getAlpha() * 255) );

		DOMCalc.calcAnchor(ccx, domItem, initDOMFrame);
		DOMCalc.calcScaleRotation(domItem, initDOMFrame);

		ccx.setScaleX(domObject.getScaleX());
		ccx.setScaleY(domObject.getScaleY());
		ccx.setRotate(domObject.getRotate());

		DOMCalc.calcDomFramePos(initDOMFrame);
		DOMCalc.calcGameObjectPos(ccx, domItem, initDOMFrame);

		return ccx;
	}



//
//	/**
//	 * DOMBitmapItemとDOMLayerをマージする
//	 * @param domBitmapList
//	 * @param domLayerList
//	 * @return
//	 */
//	public List<DOMLayer> mergeDOMXml(List<DOMBitmapItem> domBitmapItemList , List<DOMLayer> domLayerList){
//		boolean isSetDom = false;
//		// domLayerの順番が正しい
//		for(DOMLayer domLayer : domLayerList){
//			String layerName = domLayer.getName();
//			isSetDom = false;
//			for(DOMBitmapItem domBitmapItem : domBitmapItemList){
//				String bitmapItemName = domBitmapItem.getName();
//				if(bitmapItemName != null && bitmapItemName.equals( layerName )){
//					domLayer.setDomBitmapItem(domBitmapItem);
//					isSetDom = true;
//					break;
//				}
//			}
//
//			if(domLayer.getDomBitmapItem() == null){
//				domLayer.setDomBitmapItem(new DOMBitmapItem());
//			}
//		}
//
//		return domLayerList;
//	}
//
//	public List<CCXObject> convertCcx(List<DOMLayer> domLayerList){
//		List<CCXObject> ccxObjList = new ArrayList<>();
//
//		CCXObject ccx = null;
//		DOMBitmapItem dom = null;
//		for(DOMLayer domLayer : domLayerList){
//			ccx = null;
//			if(domLayer == null){
//				continue;
//			}
//			dom = domLayer.getDomBitmapItem();
//
//			if(!domLayer.getLayerType().equals("folder")){
//				Util.print("■■■■■■■■■ " + domLayer.getName() + " ---");
//				DOMFrame preDomFrame = domLayer.getDomFrameList().get(0);
//
//				for(DOMFrame domFrame : domLayer.getDomFrameList()){
//					// 初期位置
//					if(ccx == null){
//						switch(domFrame.getDomFrameType()){
//							case BITMAP:
//							case SYMBOL:
//							case WHITE:
//								ccx = new CCXObject();
//								break;
//							case TEXT:
//								DOMStaticText domText = (DOMStaticText)domFrame;
//								CCXLabel label = new CCXLabel();
//								label.setWidth(domText.getWidth());
//								label.setHeight(domText.getHeight());
//								label.setCharacters(domText.getCharacters());
//								label.setAlignment(CCXLabelAlignment.of(domText.getAlignment()));
//								label.setFontName(domText.getFontFace());
//								label.setSize(domText.getSize());
//
//								ccx = label;
//								break;
//						}
//
//						ccx.setName( domLayer.getName() );
//						ccx.setIndex( domLayer.getIndex() );
//						ccx.setObjType( ObjectType.of( domLayer.getName() ) );
//						ccx.setPosType( domLayer.getPosType() );
//						ccx.setOpacity( Util.round(domFrame.getAlphaMultiplier() * 255) );
//
//						// anchor を計算しセット
//						calcAnchor(ccx, dom, domFrame);
//
//						// scale , rotateを計算しセット
//						calcScaleRotation(ccx, dom , domFrame);
//						ccx.setScaleX(domFrame.getScaleX());
//						ccx.setScaleY(domFrame.getScaleY());
//						ccx.setRotate(domFrame.getRotate());
//						calcDomFramePos(domFrame);
//						// anchor , scale計算後、配置位置をセット
//						calcPos(ccx, dom, domFrame); // 必ずfalseに落ちるが、初期値として入れる
//					}else{
//						// animationのために毎回計算が必要。
//						calcScaleRotation(ccx , dom ,  domFrame);
//						//
//						calcDomFramePos(domFrame);
//					}
//					createAction(ccx , domFrame, preDomFrame);
//					preDomFrame = domFrame;
//				}
//				ccxObjList.add(ccx);
//			}
//		}
//
//		return ccxObjList;
//	}
//
//	private void createAction(CCXObject ccx , DOMFrame domFrame , DOMFrame preDomFrame){
//		// CCSpawnで並列に行う可能性がある
//		CCXActionList actionList = ccx.getActionList();
//
//
//		Util.print("-- pre domframe --");
//		preDomFrame.print();
//		Util.print("-- domFrame --");
//		domFrame.print();
//
//		/**
//		 * 同一キーフレームを打たれた場合対応出来ない。
//		 * 全てのListにdelayTimeを入れていく必要あり。
//		 * 待機→移動＆回転→待機→…など
//		 */
//		// 位置がずれている時はMoveBy
//		if(domFrame.getTx() != preDomFrame.getTx() || domFrame.getTy() != preDomFrame.getTy()){
//			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
//			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
//			if(delayAction != null){
//				actionList.addMoveByList(delayAction);
//			}
//
//			Util.print("--- pre dom ---");
//			preDomFrame.print();
//
//			Util.print("--- dom ---");
//			domFrame.print();
//
//			CCXAction action = new CCXAction();
//			action.setActionType( ActionType.MOVEBY );
//			action.setDuration( calcDuration(domFrame , preDomFrame) );
//			action.setPosX(Util.roundSF(domFrame.getTx() - preDomFrame.getTx(), 4));
//			action.setPosY(Util.roundSF(-(domFrame.getTy() - preDomFrame.getTy()), 4));
//
//			actionList.addMoveByList(action);
//		}
//
//		// 大きさがずれている時はScaleTo
//		if(domFrame.getScaleX() != preDomFrame.getScaleX() || domFrame.getScaleY() != preDomFrame.getScaleY()){
//			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
//			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
//			if(delayAction != null){
//				actionList.addScaleToList(delayAction);
//			}
//
//			CCXAction action = new CCXAction();
//			action.setActionType( ActionType.SCALETO );
//			action.setDuration( calcDuration(domFrame , preDomFrame) );
//			action.setScaleX(Util.roundSF(domFrame.getScaleX() , 4));
//			action.setScaleY(Util.roundSF(domFrame.getScaleY() , 4));
//
//			actionList.addScaleToList(action);
//		}
//
//		// 回転率が違う時はRotateTo
//		if(domFrame.getRotate() != preDomFrame.getRotate()) {
//			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
//			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
//			if(delayAction != null){
//				actionList.addRotateToList(delayAction);
//			}
//
//			CCXAction action = new CCXAction();
//			action.setActionType( ActionType.ROTATETO );
//			action.setDuration( calcDuration(domFrame , preDomFrame) );
//			action.setRotate( Util.round(domFrame.getRotate() - preDomFrame.getRotate()) );
//
//			actionList.addRotateToList(action);
//		}
//
//		// 透過率が違う場合はFadeTo
//		if(domFrame.getAlphaMultiplier() != preDomFrame.getAlphaMultiplier()) {
//			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
//			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
//			if(delayAction != null){
//				actionList.addFadeToList(delayAction);
//			}
//
//			CCXAction action = new CCXAction();
//			action.setActionType( ActionType.FADETO );
//			action.setDuration( calcDuration(domFrame , preDomFrame) );
//			action.setAlpha( Util.round(domFrame.getAlphaMultiplier() * 255) );
//
//			actionList.addFadeToList(action);
//		}
//
//		// blendModeがあればBlendModeを追加
//		// まだ未確認だから使わないで
//		if(!domFrame.getBlendMode().equals(preDomFrame.getBlendMode())) {
//			// クラシカルなキーフレームだけのアニメーションの場合、duration(経過時間)が無い。そのため、0秒で移動させ、残りの時間を待機する
//			CCXAction delayAction = createDelayTime(domFrame, preDomFrame);
//			if(delayAction != null){
//				actionList.addBrendModeList(delayAction);
//			}
//
//			CCXAction action = new CCXAction();
//			action.setActionType( ActionType.BLENDMODE );
//			action.setDuration( 0 );
//			if("add".equals(domFrame.getBlendMode())) {
//				action.setBrend( "GL_ONE, GL_ONE" );
//			}else{
//				action.setBrend( "CC_BLEND_SRC, CC_BLEND_DST" );
//			}
//
//			actionList.addBrendModeList(action);
//		}
//
//		// 全部同じだったらDelayTime
//		if(
//			   domFrame.getTx() == preDomFrame.getTx()
//			&& domFrame.getTy() == preDomFrame.getTy()
//			&& domFrame.getScaleX() == preDomFrame.getScaleX()
//			&& domFrame.getScaleY() == preDomFrame.getScaleY()
//			&& domFrame.getRotate() == preDomFrame.getRotate()
//			&& domFrame.getAlphaMultiplier() == preDomFrame.getAlphaMultiplier()
//			&& domFrame.getBlendMode().equals(preDomFrame.getBlendMode())
//		){
//			double duration = calcDuration(domFrame , preDomFrame);
//			if(duration > 0){
//				CCXAction action = new CCXAction();
//				action.setActionType( ActionType.DELAYTIME );
//
//				action.setDuration( duration );
//
//				//ccx.getActionList().addDelayTimeList(action);
//				if(actionList.getFadeToList().size() > 0){
//					actionList.addDelayTimeList(action);
//				}
//				if(actionList.getMoveByList().size() > 0){
//					actionList.addMoveByList(action);
//				}
//				if(actionList.getRemoveList().size() > 0){
//					actionList.addRemoveToList(action);
//				}
//				if(actionList.getRotateToList().size() > 0){
//					actionList.addRotateToList(action);
//				}
//				if(actionList.getScaleToList().size() > 0){
//					actionList.addScaleToList(action);
//				}
//			}
//		}
//	}
//
//	private CCXAction createDelayTime(DOMFrame domFrame , DOMFrame preDomFrame){
//		if(!"motion".equals(preDomFrame.getTweenType())){
//			CCXAction action = new CCXAction();
//			action.setActionType( ActionType.DELAY_TIME );
//			//action.setDuration( "" + Util.roundSF((domFrame.getIndex() - preDomFrame.getIndex()) / Config.MAX_FRAME , 1));
//			action.setDuration( Util.round(domFrame.getIndex() - preDomFrame.getIndex()));
//
//			return action;
//		}
//		return null;
//	}
//
//	/**
//	 * positionを計算します。
//	 * @param ccx
//	 * @param dom
//	 * @param domFrame
//	 */
//	private void calcPos(CCXObject ccx , DOMBitmapItem dom , DOMFrame domFrame){
//		switch(ccx.getObjType()){
//			case SPRITE:
//			case BTN:
//			case TEXT:
//				calcGameObjectPos(ccx , dom, domFrame);
//				break;
//			case TILE_SPRITE:
//				calcTilePos(ccx, dom, domFrame);
//				break;
//			default:
//		}
//
//	}
//
//	/**
//	 * domFrameのポジションを微調整する。flashとccxの挙動の差異を埋める
//	 * @param domFrame
//	 */
//	private void calcDomFramePos(DOMFrame domFrame){
//		DOMObject domObj = domFrame.getDomObject();
//		DOMMatrix matrix = domObj.getMatrix();
//		DOMTransformationPoint tran = domObj.getTran();
//
//		double x = matrix.getTx();
//		double y = matrix.getTy();
//		double tranX = tran.getX();
//		double tranY = tran.getY();
//		double scaleX = domObj.getScaleX();
//		double scaleY = domObj.getScaleY();
//		double rotate = domObj.getRotate();
//
//		if(tranX != 0 || tranY != 0){
//			double radian = rotate * Math.PI / 180;
//
//			Util.print("pos (" + x + " , " + y + ")");
//
//			x += Math.cos(radian) * tranX * scaleX - Math.sin(radian) * tranY * scaleY;
//        	y += Math.sin(radian) * tranX * scaleX + Math.cos(radian) * tranY * scaleY;
//
//
//    		Util.print("calc pos (" + x + " , " + y + ")");
//    		Util.print("scale (" + scaleX + " , " + scaleY + ")");
//    		Util.print("tran (" + tranX + " , " + tranY + ")");
//
//        	matrix.setTx(x);
//        	matrix.setTy(y);
//		}
//	}
//
//	private void calcGameObjectPos(CCXObject ccx , DOMItem dom , DOMFrame domFrame){
//
//		DOMObject domObj = domFrame.getDomObject();
//		DOMMatrix mainMatrix = domObj.getMatrix();
//		DOMTransformationPoint mainTran = domObj.getTran();
//
////		DOMMatrix subMatrix = dom.getMatrix();
//
//		double x = mainMatrix.getTx();
//		double y = mainMatrix.getTy();
////		double w = dom.getWidth();
////		double h = dom.getHeight();
////		double offX = subMatrix.getTx();
////		double offY = subMatrix.getTy();
////		double tranX = mainTran.getX();
////		double tranY = mainTran.getY();
////		double ancX = ccx.getAnchorX();
////		double ancY = ccx.getAnchorY();
////		double scaleX = domObj.getScaleX();
////		double scaleY = domObj.getScaleY();
////		double rotate = domObj.getRotate();
//
//		double posX = x;
//		double posY = y;
//
//		ccx.setPosX( Util.round(posX) );
//		if(ccx.getPosType() == PositionType.TOP){
//			ccx.setPosY( Util.roundSF(posY , 4) );
//		}else{
//			double calcPosY = Config.TOTAL_HEIGHT - Util.roundSF(posY , 4);
//			ccx.setPosY( calcPosY );
//		}
//	}
//
//	private void calcTilePos(CCXObject ccx , DOMBitmapItem dom , DOMFrame domFrame){
//		double radian = domFrame.getRotate() * Math.PI / 180;
//
//		double tx = domFrame.getTx() + Math.cos(radian) * domFrame.getTransformationPointX() * domFrame.getScaleX() - Math.sin(radian) * domFrame.getTransformationPointY() * domFrame.getScaleY();
//		double ty = domFrame.getTy() + Math.sin(radian) * domFrame.getTransformationPointX() * domFrame.getScaleX() + Math.cos(radian) * domFrame.getTransformationPointY() * domFrame.getScaleY();
//
//		double posX = (ccx.getAnchorX() * dom.getWidth() * ccx.getScaleX());
//		double posY = (ccx.getAnchorY() * dom.getHeight() * ccx.getScaleY());
//
//		domFrame.setTx(tx);
//		domFrame.setTy(ty);
//
//		domFrame.setTx(domFrame.getTx() - posX);
//		domFrame.setTy(domFrame.getTy() + posY);
//
//		ccx.setPosX(domFrame.getTx());
//		ccx.setPosY(Config.TOTAL_HEIGHT - domFrame.getTy());
//	}
//
//	private double calcDuration(DOMFrame domFrame , DOMFrame preDomFrame){
//		if(!"motion".equals(preDomFrame.getTweenType())){
//			return 0.0;
//		}
//		//return Util.roundSF((domFrame.getIndex() - preDomFrame.getIndex()) / Config.MAX_FRAME , 1);
//		return domFrame.getIndex() - preDomFrame.getIndex();
//	}
//
//	/**
//	 * anchorポイントを計算
//	 * @param ccx
//	 * @param dom
//	 * @param domFrame
//	 */
//	private void calcAnchor(CCXObject ccx , DOMItem dom , DOMFrame domFrame) {
//		double baseValueX = 0.0;
//		double baseValueY = 1.0;
//
//		if(dom != null){
//			DOMSymbolItem domSymbolItem = (DOMSymbolItem)dom;
//
//			DOMObject domObject = domFrame.getDomObject();
//			DOMTransformationPoint tran = domObject.getTran();
//			DOMFrame subDomFrame = findInitDOMFrame(domSymbolItem.getDomLayer().getDomFrameList());
//			DOMMatrix matrix = subDomFrame.getDomObject().getMatrix();
//			DOMItem domItem = domSymbolItem.getDomLayer().getDomItem();
//			if(domItem.getDomItemType() == DOMItemType.BITMAP){
//				DOMBitmapItem bitmapItem = (DOMBitmapItem)domItem;
//
//				double tranX = tran.getX();
//				double tranY = tran.getY();
//
//				double offX = matrix.getTx();
//				double offY = matrix.getTy();
//
//				double width = bitmapItem.getWidth();
//				double height = bitmapItem.getHeight();
//
//				baseValueX = -(offX / width) + (tranX / width);
//				baseValueY = -(offY / height) - (tranY / height);
//			}
//		}
//		ccx.setAnchorX(baseValueX);
//		ccx.setAnchorY(baseValueY);
//	}
//
//	/**
//	 * scaleとrotateを計算
//	 * @param ccx
//	 * @param domFrame
//	 */
//	private void calcScaleRotation(CCXObject ccx , DOMItem dom , DOMFrame domFrame){
//		double scaleX = 1.0;
//		double scaleY = 1.0;
//		double rotate = 0.0;
//
//		DOMObject domObj = domFrame.getDomObject();
//		DOMMatrix matrix = domObj.getMatrix();
//
//		double a = matrix.getA();
//		double b = matrix.getB();
//		double c = matrix.getC();
//		double d = matrix.getD();
//
//		if(dom != null && dom instanceof DOMBitmapItem){
//			DOMBitmapItem domBitmapItem = (DOMBitmapItem)dom;
//			DOMMatrix subMatrix = domBitmapItem.getMatrix();
//			if(subMatrix != null && !subMatrix.isInit()){
//				a = a * subMatrix.getA();
//				b = b * subMatrix.getB();
//				c = c * subMatrix.getC();
//				d = d * subMatrix.getD();
//			}
//		}
//
//
//		if(a == 1.0 && b == 0.0 && c == 0.0 && d == 1.0) {
//			// 初期値のまま
//		} else {
//
//			// xをscaleX倍、yをscaleY倍してから、θだけ回転させる時の変換行列は
//			// a b
//			// c d
//			// ＝ scaleX * cosθ  -scaleY * sinθ
//			//    scaleX * sinθ   scaleY * cosθ
//			// よって、tanθ = c / a → θ = atan(c / a)
//			// θがわかればscaleXとscaleYもわかる
//			double theta = a == 0 ? Math.PI / 2 : Math.atan(c / a);
//
//			rotate = - theta / Math.PI * 180;
//
//			double sin = Math.sin(theta);
//			double cos = Math.cos(theta);
//			if(Math.abs(sin) > Math.abs(cos)) { // 誤差を小さくするために、絶対値が大きい方を使う
//				scaleX = c / sin;
//				scaleY = - b / sin;
//			} else {
//				scaleX = a / cos;
//				scaleY = d / cos;
//			}
//
//			if(a == 1 && c == 0) {
//				scaleX = 1;
//			}
//			if(b == 0 && d == 1) {
//				scaleY = 1;
//			}
//
//			if(scaleX < 0 && scaleY < 0) {
//				scaleX *= -1;
//				scaleY *= -1;
//				rotate += 180;
//			}
//		}
//
//		// scaleXとscaleYの符号が逆の時は、回転量が逆になる
//		// Flashはscaleを変えてから回転するのに対し、
//		// Cocos2dxは回転してからscaleを変えるため
//		if(scaleX * scaleY < 0) {
//			rotate *= -1;
//		}
//
//		// actionのため、domFrameでも持つ
//		domObj.setScaleX(scaleX);
//		domObj.setScaleY(scaleY);
//		domObj.setRotate(rotate);
//
//	}

//	/**
//	 * animationが終わったら元の位置に戻す
//	 * @param ccxList
//	 */
//	public void finishInitPosition(List<CCXObject> ccxList){
//		for(CCXObject obj : ccxList){
//			double posX = 0;
//			double posY = 0;
//			double scaleX = 0;
//			double scaleY = 0;
//			double rotate = 0;
//			double opacity = 0;
//
//			CCXActionList actList = obj.getActionList();
//			for(CCXAction act : actList.getMoveByList()){
//				posX += act.getPosX();
//				posY += act.getPosY();
//			}
//
//			for(CCXAction act : actList.getScaleToList()){
//				scaleX += act.getScaleX();
//				scaleY += act.getScaleY();
//			}
//
//			for(CCXAction act : actList.getFadeToList()){
//				opacity += act.getAlpha();
//			}
//
//			for(CCXAction act : actList.getRotateToList()){
//				rotate += act.getRotate();
//			}
//
//			if(posX != 0 || posY != 0){
//				CCXAction act = new CCXAction();
//				act.setActionType(ActionType.MOVE_BY);
//				act.setDuration(0);
//				act.setPosX( -Util.roundSF(posX , 4) );
//				act.setPosY( -Util.roundSF(posY , 4) );
//				actList.addMoveByList(act);
//			}
//
//			if(scaleX != 0 || scaleY != 0){
//				CCXAction act = new CCXAction();
//				act.setActionType(ActionType.SCALE_TO);
//				//act.setScaleX( -Util.roundSF(scaleX , 4) );
//				//act.setScaleY( -Util.roundSF(scaleY , 4) );
//				act.setScaleX(1.0);
//				act.setScaleY(1.0);
//				actList.addScaleToList(act);
//			}
//
//			if(rotate != 0){
//				CCXAction act = new CCXAction();
//				act.setActionType(ActionType.ROTATE_TO);
//				act.setRotate( -Util.roundSF(rotate , 4) );
//				actList.addRotateToList(act);
//			}
//
//			if(opacity != 0){
//				CCXAction act = new CCXAction();
//				act.setActionType(ActionType.FADE_TO);
//				act.setAlpha(-Util.roundSF(opacity , 4));
//				actList.addFadeToList(act);
//			}
//		}
//	}
}
