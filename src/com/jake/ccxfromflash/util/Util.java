package com.jake.ccxfromflash.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jake.ccxfromflash.constants.Config;

/**
 * 汎用処理
 * @author kuuki_yomenaio
 *
 */
public class Util {

	// 改行コード
	private static final String KAIGYOU = "\r\n";

	public static void print(String print){
		System.out.println(print);
	}

	public static void print(String print , int index){
		for(int i = 0 ; i < index ; i++){
			print = "\t" + print;
		}
		System.out.println(print);
	}

	/**
	 * 有効桁で丸め込む
	 * @param value
	 * @return
	 */
	public static  double round(double value) {
		return Math.round(value * 10) / 10.0;
	}

	/**
	 * n桁で丸める
	 */
	public static double roundSF(double value, int effectiveDigit) {
		int valueDigit = (int)Math.round( Math.log10( Math.abs(value) ) );
		int roundDigit = valueDigit - effectiveDigit;

		int powNum = 1;
		for(int i = 0; i < Math.abs(roundDigit); i++) {
			powNum *= 10;
		}
		if(roundDigit < 0) {
			return Math.round(value * powNum) / (double)powNum;
		} else {
			return Math.round(value / powNum) * powNum;
		}
	}

	/**
	 * XmlElementから指定した値をintで取得
	 * @param element
	 * @param name
	 * @return
	 */
	public static int getInt(Element element, String name) {
		return (int)Math.round(getDouble(element, name, 0.0));
	}

	/**
	 * XmlElementから指定した値をintで取得(デフォルト値あり
	 * @param element
	 * @param name
	 * @return
	 */
	public static int getInt(Element element, String name , int defaultValue) {
		return (int)Math.round(getDouble(element, name, defaultValue));
	}

	/**
	 * XmlElementから指定した値をdoubleで取得(デフォルト値あり)
	 * @param element
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(Element element, String name, double defaultValue) {
		if(element == null){
			return defaultValue;
		}
		String attribute = element.getAttribute(name);
		if(attribute == null || "".equals(attribute)) {
			return defaultValue;
		}
		return getDouble(element, name);
	}

	/**
	 * XmlElementから指定した値をdoubleで取得
	 * @param element
	 * @param name
	 * @return
	 */
	public static double getDouble(Element element, String name) {
		if(element == null){
			return 0.0;
		}
		String attribute = element.getAttribute(name);
		if(attribute == null || "".equals(attribute)){
			return 0.0;
		}
		return Double.parseDouble(attribute);
	}

	/**
	 * XmlElementから指定した値をStringで取得
	 * @param element
	 * @param name
	 * @return
	 */
	public static String getString(Element element , String name){
		if(element == null){
			return "";
		}
		String attribute = element.getAttribute(name);
		if(attribute != null){
			return attribute;
		}
		return "";
	}

	/**
	 * 文字を改行しつつ連結
	 * @param sbTemp
	 * @param str
	 */
	public static void appendStr(StringBuilder sbTemp, String str) {
		sbTemp.append(str + KAIGYOU);
	}

	/**
	 * 中央Wを取得
	 * @return
	 */
	public static double getCenterW(){
		return Config.TOTAL_WIDTH / 2;
	}

	/**
	 * 中央Hを取得
	 * @return
	 */
	public static double getCenterH(){
		return Config.TOTAL_HEIGHT / 2;
	}

	/**
	 * 使いにくいNodeListをList<Element>に変換する
	 * @param nodeList
	 * @return
	 */
	public static List<Element> changeList(NodeList nodeList){
		List<Element> result = new ArrayList<>();

		// nullによる例外が発生しにくくなるわけではなく、nullチェックが早くなるfailFast的な
		Optional<NodeList> thisNodeList = Optional.of(nodeList); // nullだったらnullPointerExceptionが発生する
		thisNodeList.ifPresent(list->{
			for(int i = 0 ; i < list.getLength() ; i++){
				result.add((Element)list.item(i)); // ぐぬぬ
			}
		});

		return result;
	}

}
