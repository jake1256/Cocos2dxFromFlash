package com.jake.ccxfromflash.constants;

/**
 * コンフィグパラメータ。
 * @author kuuki_yomenaio
 *
 */
public class Config {
	/**
	 * Flashの実行速度。FrameRate
	 */
	public static double FPS = 0;

	/**
	 * 最大Frame数
	 */
	public static int MAX_FRAME = 0;

	/**
	 * Flashのcanvasサイズ高さ
	 */
	public static double TOTAL_HEIGHT = 0;

	/**
	 * Flashのcanvasサイズ幅
	 */
	public static double TOTAL_WIDTH = 0;

	/**
	 * Flashの配置場所
	 */
	public static String ROOT_FOLDER_PATH = null;

	/**
	 * cocos v3で出力するか否か
	 */
	public static boolean isVer3_0 = true;

	/**
	 * 中央に配置するか？
	 */
	public static boolean isCenter = false;

	/**
	 * アニメーションをリピートするか？
	 */
	public static boolean isRepeatForever = true;
}
