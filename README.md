Cocos2dxFromFlash
=================

###Introduction

Flashからcocos2d-x(cpp)に変換するツールです。

・画面全体のレイアウトをFlashで配置し、cocos2d-xにエクスポート

・キャラクターのアニメーションをFlashで制作し、エクスポート

などが可能です。

![alt flash1](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/flash1.png)

複数ある画面サイズに対応するため、
top , center , bottom各種相対ポジション対応

iphone4(640 x 960)

![alt ui640x960](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/iOS1.png)

iphone5(640 x 1136)

![alt ui640x1136](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/iOS2.png)

(上下のメニューがそのまま張り付いていて、その他の部分は相対的に位置を補完します）

テスト用のUI作成に[ジュエルセイバーFREE](http://www.jewel-s.jp/)の画像をお借りしています。

各種Animation出力対応

![alt flash4](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/ios_movie.mov.gif)

例えば画面レイアウト製作をした時にnewアイコンだけアニメーションさせたい
といった使用方法も可能。

もちろんキャラクターのアニメーションのような複雑な組み合わせ、
腕の動きのような中心点がズレたアニメーションでも出力可能です。

特に追加で必要なソースファイルやヘッダーファイルはありません。
100% pure cocos2d-xのソース形式で出力致します。

###Support
実行環境
Windows , Mac OS で確認済
実行には[Java](http://java.com/ja/download/)のインストールが必要です。
(MacOSでは標準で搭載されています。)

対応Ver
Flash CS5〜
cocos2d-x v2.x
cocos2d-x v3.x

###use
使い方は[wikiのhow to use](https://github.com/jake1256/Cocos2dxFromFlash/wiki#how-to-use)をご確認下さい。

###advice(wikiにまとめます)


###Licence
[BSD Licence](http://ja.wikipedia.org/wiki/BSD%E3%83%A9%E3%82%A4%E3%82%BB%E3%83%B3%E3%82%B9)

###etc

wiki書かねば。

残項目メモ

・画像のマスクを変換

・色のブレンドを変換

・多階層構造の場合

・ベクターグラフィック

・ActionScript？
