package dao; // DAO共通ユーティリティ

import java.sql.Connection;       // DB接続オブジェクト
import java.sql.DriverManager;    // 接続ヘルパ
import java.sql.SQLException;     // 例外クラス

// DB接続を作るだけの小さなユーティリティクラス
public class DB {
    // 接続URL（DB名=ec_min） ※環境に合わせて修正可
    private static final String URL =
        "jdbc:mysql://localhost:3306/ec_mini" +
        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";

    // ユーザー名（環境に合わせる）
    private static final String USER = "root";

    // パスワード（環境に合わせる）
    private static final String PASS = "";

    // 最初に一度だけドライバ読み込み
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQLドライバをロード
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバ読み込み失敗", e);
        }
    }

    // 呼ぶたびに新しい接続を返す
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
