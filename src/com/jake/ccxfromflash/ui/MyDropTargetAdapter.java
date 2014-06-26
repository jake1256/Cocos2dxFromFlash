package com.jake.ccxfromflash.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.List;

import javax.swing.JTextField;

/**
 * D&Dアダプター
 * @author kuuki_yomenaio
 *
 */
public class MyDropTargetAdapter implements DropTargetListener {

	/**
	 * D&Dされた際に取り出したファイルパスを格納するテキストフィールド
	 */
	private JTextField textField;

	public MyDropTargetAdapter(JTextField textField){
		this.textField = textField;
	}


	/**
	 * フォルダをD&Dされたら、ファイルパスを取り出す
	 */
	@Override
	public void drop(DropTargetDropEvent event) {
		try {
			Transferable transfer = event.getTransferable();
			if(transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				@SuppressWarnings("rawtypes") // List内部の型はStringだろうが、そうとは限らない
				List fileList = (List) (transfer.getTransferData(DataFlavor.javaFileListFlavor));
				String path = "" + fileList.get(0);
				textField.setText(path);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	// 未使用
	@Override
	public void dragEnter(DropTargetDragEvent arg0) {}
	@Override
	public void dragExit(DropTargetEvent arg0) {}
	@Override
	public void dragOver(DropTargetDragEvent arg0) {}
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {}
}
