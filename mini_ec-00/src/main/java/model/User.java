package model; // モデル層

// 会員を表すクラス（JavaBean）
public class User {
    // フィールド（プライベート変数）
    private int id;        // ID
    private String email;  // メール
    private String pass;   // パスワード（学習用：平文）

    // コンストラクタ（全フィールド用）
    public User(int id, String email, String pass) {
        this.id = id;         // 引数idをフィールドに代入
        this.email = email;   // 引数emailをフィールドに代入
        this.pass = pass;     // 引数passをフィールドに代入
    }

    // コンストラクタ（新規登録時などID未確定用）
    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    // ゲッター（値を取り出す）
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPass() { return pass; }

    // セッター（必要なら追加）
    public void setId(int id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPass(String pass) { this.pass = pass; }
}
