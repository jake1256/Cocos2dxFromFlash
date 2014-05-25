Cocos2dxFromFlash
=================

###Introduction

Flashからcocos2d-xに変換するツールです。

・画面全体のレイアウトをFlashで配置し、cocos2d-xにエクスポート

・キャラクターのアニメーションをFlashで制作し、エクスポート

などが可能です。

![alt flash1](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/flash1.png)

複数ある画面サイズに対応するため、
top , center , bottom各種相対ポジション対応

iphone4(640 x 960)
![alt 640x960](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/iOS1.png)

iphone5(640 x 1136)
![alt flash1](https://raw.githubusercontent.com/jake1256/Cocos2dxFromFlash/master/img/sample/iOS2.png)

(上下のメニューがそのまま張り付いていて、その他の部分は相対的に位置を補完します）

各種Animation出力対応

例えば画面レイアウト製作をした時にnewアイコンだけアニメーションさせたい
といった使用方法も可能。

もちろんキャラクターのアニメーションのような複雑な組み合わせ、
腕の動きのような中心点がズレたアニメーションでも出力可能です。

特に追加で必要なソースファイルやヘッダーファイルはありません。
100% pure cocos2d-xのソース形式で出力致します。

cocos2d-xを使いたいんだけど、クリエイティブに新しいツールを覚えてもらうのはコストが…。

オープンソースになっていないライブラリ組み込むのはバグが怖い…。

過去Flashで作ってたものをお手軽に移植したい！

少しでもリソース減らして軽くしたい！→texture packer使うとさらに軽く！オススメです。

と言った場合にでも使って頂ければ幸いです。

###Support

対応Ver
Input
Flash CS5〜

Output
cocos2d-x v2.x
cocos2d-x v3.x
のソースコードをテキストで出力

###use
使い方

1. flaファイルを開き、名前を付けて保存→形式を非圧縮Fla(.xfl)で保存

2. Cocos2dxFromFlashを実行し、.xflファイルをD&Dしてパスを設定

3. 各種設定

4. 変換ボタンを押下

5. .xflの配置場所にtxtファイルが2つ出力されます。

6. 後はcocos2d-x側でソースコードをコピペ。resourceフォルダに画像を配置するのを忘れずに。texture packerマジおすすめ。

7. Flashで作成したモノがそのままcocosに反映されます。


###advice
・flash側は画像ファイル名とレイヤー名を合わせてね。
sample.png → sample

・同じ画像を複数使う場合でも、画像ファイル名と同じレイヤー名にしてね。
(変換時に自動的に_1 , _2と名前を付けます)

・シンボル使う時はシンボル名も合わせてね。

・MovieClipは使えるはず。

・１レイヤー１シンボル（１画像）でお願いします。複数配置すると全部同じ動きしちゃうよ！

・top , bottom , centerでフォルダ分けすると相対座標で配置出来ます。

・Flash側は日本語は使うな。英文字にしておくこと。（レイヤー1とかダメ。macに持ってった時にバグるかも）さらに-(ハイフン)を使うと、
cpp側でエラーになるので、_(アンダースコア)にでもしてください。

・cocos側はwindowSizeを別に持つのをオススメします。後で置き換えてね。

・回転、拡大、変形、移動、アルファは組み合わせて出来る。が、色のブレンドはまだ未実装。

・キャラクターなどを作る時はFlashの中心点を0,0に。画面全体を組む時は逆に中心点を0,0にしないように。

・動きがFlashと合わない事はあります。それは小数点以下の微量な動きがあった場合に
小数点第二位以下で丸め込みを入れているからです。その場合はソースコード側で調整してあげて下さい。

・Flash CS5～対応なのは、xfl形式で出力出来るのがそこからだから。

・Flash側で設定しているFrameRateで出力しているので、遅い、早いはそこで調整してください。
もしくは出力した後、 / 30.0になってる所でも書き換えてあげて。

・ラッパークラス使ってる？WriterLogicの所書き換えて使うといいよ！

・JSなんだけど…。→WriterLogic書き換えていいのよ。


###etc

開発はbitbucketのプライベートリポジトリで行っています。
そろそろコッチに移行したい。


