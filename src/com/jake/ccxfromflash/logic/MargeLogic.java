package com.jake.ccxfromflash.logic;

import java.util.List;
import java.util.Optional;

import com.jake.ccxfromflash.constants.DomObjectType;
import com.jake.ccxfromflash.model.dom.DOMLayer;
import com.jake.ccxfromflash.model.dom.item.DOMBitmapItem;
import com.jake.ccxfromflash.model.dom.item.DOMSymbolItem;

/**
 * XMLをパースした後、各要素を結びつける必要がある。
 * その処理を行うクラス
 * @author kuuki_yomenaio
 *
 */
public class MargeLogic {

	/**
	 * 各要素を結びつける
	 * @param domLayerList
	 * @param domBitmapItemList
	 * @param domSymbolList
	 */
	public void marge(List<DOMLayer> domLayerList , List<DOMBitmapItem> domBitmapItemList , List<DOMSymbolItem> domSymbolList ){

		execDOMLayerIndex(domLayerList);

		// DOMSymbol配下にあるDOMLayerを処理する
		domSymbolList.forEach((domSymbol)->{
			DOMLayer domLayer = domSymbol.getDomLayer();
			inputDomLayer(domLayer, domBitmapItemList, domSymbolList);
		});

		// 最上位層にあるDOMLayerを処理する
		domLayerList.forEach((domLayer)->{
			inputDomLayer(domLayer, domBitmapItemList, domSymbolList);
		});
	}

	/**
	 * DOMLayerにシンボルか画像を紐付ける
	 * @param domLayer
	 * @param domBitmapItemList
	 * @param domSymbolList
	 */
	private void inputDomLayer(DOMLayer domLayer , List<DOMBitmapItem> domBitmapItemList , List<DOMSymbolItem> domSymbolList){
		String name = domLayer.getName();
		// このレイヤーがシンボルか画像かはdomFrameの最初の一つを見てみるしかない。

		domLayer.getDomFrameList().forEach((domFrame)->{
			DomObjectType objType = domFrame.getDomObject().getDomObjectType();
			if(objType == DomObjectType.BITMAP){
				Optional<DOMBitmapItem> domBitmapItem = findDomBitmapItem(name, domBitmapItemList);
				domBitmapItem.ifPresent(d->domLayer.setDomItem(d));
				return;
			}
			else if(objType == DomObjectType.SYMBOL){
				Optional<DOMSymbolItem> domSymbolItem = findDOMSymbol(name, domSymbolList);
				domSymbolItem.ifPresent(d->domLayer.setDomItem(d));
				return;
			}
		});
	}

	/**
	 * 指定した名前からDOMBitmapItemを探して返す
	 * @param name
	 * @param domBitmapItemList
	 * @return
	 */
	private Optional<DOMBitmapItem> findDomBitmapItem(String name , List<DOMBitmapItem> domBitmapItemList){
		return domBitmapItemList.stream().filter(domBitmap->domBitmap.getName().equals(name) == true).findFirst();
	}

	/**
	 * 指定した名前からDOMTimelineを探して返す
	 * @param name
	 * @param domTimelineList
	 * @return
	 * @return
	 */
	private Optional<DOMSymbolItem> findDOMSymbol(String name , List<DOMSymbolItem> domSymbolList){
		return domSymbolList.stream().filter(domSymbol->domSymbol.getName().equals(name) == true).findFirst();
	}

	/**
	 * 同じ名前のゲームオブジェクトがあった場合、indexを割り振り、管理します。
	 * @param domLayerList
	 */
	private void execDOMLayerIndex(List<DOMLayer> domLayerList){

		domLayerList.forEach((domLayer)->{
			int index = 1;
			String name = domLayer.getName();
			domLayer.setIndex(index++);

			// ここは無理か…。同じ名前だったらindexを割り振る
			for(DOMLayer tempDomLayer : domLayerList){
				int tempIndex  = tempDomLayer.getIndex();
				String tempName = tempDomLayer.getName();

				// 同じ名前でindexがまだ振られてないものはindexをセット
				if(name.equals(tempName) && tempIndex == 0){
					tempDomLayer.setIndex(index++);
				}
			}
		});

	}

}
