package com.jake.ccxfromflash.constants;

/**
 * 相対配置位置を決める種別
 * @author kuuki_yomenaio
 *
 */
public enum PositionType {
	TOP, // 上部
	CENTER, // 中央
	BOTTOM, // 下部
	VALIABLE, // 可変
	NONE;

	public static PositionType of(String folderName){
		if("top".equals(folderName)){
			return PositionType.TOP;
		}
		else if("center".equals(folderName)){
			return PositionType.CENTER;
		}
		else if("bottom".equals(folderName)){
			return PositionType.BOTTOM;
		}
		else if("variable".equals(folderName)){
			return PositionType.VALIABLE;
		}

		return PositionType.NONE;
	}
}
