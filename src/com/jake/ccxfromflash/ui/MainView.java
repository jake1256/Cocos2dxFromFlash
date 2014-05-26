package com.jake.ccxfromflash.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.jake.ccxfromflash.constants.Config;
import com.jake.ccxfromflash.service.ConvertService;

/**
 * swingのUI
 * @author kuuki_yomenaio
 *
 */
public class MainView extends JFrame{

	/** シリアルID */
	private static final long serialVersionUID = 6234085892847279348L;

	private JTextField dragDropTextField;
	private JComboBox<String> animationModeComboBox;
	private JCheckBox centerPointCheckBox;
	private JRadioButton radioV3;
	private JRadioButton radioV2;

	public MainView(){
		init();
		setVisible(true);
	}

	private void init(){

		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){
			e.printStackTrace();
		}


		// Xボタン押下時の挙動を設定する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Cocos2d-x From Flash"); // title
		setBackground(Color.WHITE);

		// D&D エリアタイトル
		JLabel dragDropTitle = new JLabel();
		dragDropTitle.setText("1.xflファイルをD&Dし、xflファイルが置いてある場所へのpathを取得します。");

		// D&Dエリアテキストフィールド
		dragDropTextField = new JTextField(50);
		new DropTarget(dragDropTextField, new DragDropTargetAdapter( dragDropTextField ));




		// その他細かい所を作る
		JPanel settingPanel = createParameterPanel();


		Container contentPane = getContentPane();
		contentPane.add(dragDropTitle, BorderLayout.NORTH);
		contentPane.add(dragDropTextField, BorderLayout.CENTER);
		contentPane.add(settingPanel, BorderLayout.SOUTH);

		pack();

		setSize(600, 400);
	}

	private JPanel createParameterPanel(){
		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.Y_AXIS));

		// パラメータータイトル
		JPanel parameterTitlePanel = new JPanel();
		parameterTitlePanel.setLayout(new BoxLayout(parameterTitlePanel, BoxLayout.Y_AXIS));
		JLabel parameterTitle = new JLabel();
		parameterTitle.setText("2.各種パラメーターを設定します。");
		parameterTitlePanel.add(parameterTitle);
		parameterPanel.add(parameterTitlePanel);

		// アニメーションタイプ選択
		JPanel animationModePanel = new JPanel();
		animationModePanel.setLayout(new BoxLayout(animationModePanel, BoxLayout.X_AXIS));
		animationModePanel.add(new JLabel("アニメーションのタイプ"));
		animationModeComboBox = new JComboBox<String>(new String[] {
				"once only",
				"repeat forever"
		});
		animationModePanel.add(animationModeComboBox);
		parameterPanel.add(animationModePanel);

		// 中心点
		JPanel centerPointPanel = new JPanel();
		centerPointPanel.setLayout(new BoxLayout(centerPointPanel, BoxLayout.X_AXIS));
		centerPointCheckBox = new JCheckBox();
		centerPointCheckBox.setText("Flashの中心を(0,0)に合わせる");
		centerPointPanel.add(centerPointCheckBox);
		parameterPanel.add(centerPointPanel);

		// cocos v選択ラジオ
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioV3 = new JRadioButton("cocos2d-x v3.x" , true);
		radioV2 = new JRadioButton("cocos2d-x v2.x" , false);
		radioButtonGroup.add(radioV3);
		radioButtonGroup.add(radioV2);
		radioPanel.add(radioV3);
		radioPanel.add(radioV2);
		parameterPanel.add(radioPanel);

		// ボタン生成
		JButton execButton = createExecButton();
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(execButton);
		parameterPanel.add(buttonPanel);



		return parameterPanel;
	}

	/**
	 * 実行ボタンを作成
	 * @return
	 */
	private JButton createExecButton(){
		JButton button = new JButton("Flash xfl → Cocos2dx に 変換");
		final JFrame thiz = this;
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {

					// ファイルパスを取得
					String filePath = dragDropTextField.getText();
					if(filePath != null && !"".equals(filePath)){

						if(!filePath.contains(".xfl")){
							JOptionPane.showMessageDialog(thiz, "xflファイルを指定して下さい。" , "Error", JOptionPane.DEFAULT_OPTION);
							return;
						}

						// ファイルパスをparse
						if(filePath.lastIndexOf("/") > -1){
							filePath = filePath.substring(0, filePath.lastIndexOf("/"));
						}
						else if(filePath.lastIndexOf("\\") > -1){
							filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
						}

						// アニメーションタイプ
						switch(animationModeComboBox.getSelectedIndex()) {
							case 0:
								Config.isRepeatForever = false;
								break;
							case 1:
								Config.isRepeatForever = true;
								break;
						}

						// ラジオボタンの結果取得
						if(radioV3.isSelected()){
							Config.isVer3_0 = true;
						}
						else if(radioV2.isSelected()){
							Config.isVer3_0 = false;
						}

						// 中央点チェックボックス
						Config.isCenter = centerPointCheckBox.isSelected();

						ConvertService convertService = new ConvertService();
						convertService.execute(filePath);

						JOptionPane.showMessageDialog(thiz, "output " + filePath , "Success", JOptionPane.DEFAULT_OPTION);
					}
				} catch(Exception exp) {
					exp.printStackTrace();
					JOptionPane.showMessageDialog(thiz, exp.getMessage(), "Error", JOptionPane.DEFAULT_OPTION);
				}
			}
		});
		return button;
	}
}
