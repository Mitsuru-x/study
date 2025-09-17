package model;                       // 役割ごとにパッケージを分ける(Model層)
public class Message {                   // 投稿1件を表すシンプルなモデル
    private final String text;           // 本文（不変にしたいのでfinal）
    private final String author;         // 投稿者名（不変）

    public Message(String text, String author) { // 必須2項目のコンストラクタ
        this.text = text;                // フィールドへ代入
        this.author = author;            // フィールドへ代入
    }
    public String getText() {            // 本文の取得メソッド（ゲッター）
        return text;                     // 本文を返す
    }
    public String getAuthor() {          // 投稿者名の取得メソッド（ゲッター）
        return author;                   // 投稿者名を返す
    }
}
