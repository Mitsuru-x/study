package dao; // DAO層

// JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;   // モデル

// 会員関連のDAO
public class UserDAO {

    // 認証（メール＋パスワードで1件取得）
    public User findByLogin(String email, String pass) {
        String sql = "SELECT id, email, pass FROM users WHERE email=? AND pass=?"; // 学習用（平文）
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email); // 1番目: email
            ps.setString(2, pass);  // 2番目: pass

            try (ResultSet rs = ps.executeQuery()) { // 実行
                if (rs.next()) { // 一致
                    return new User(
                        rs.getInt("id"),       // id
                        rs.getString("email"), // email
                        rs.getString("pass")   // pass
                    ); // モデル返す
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 学習用
        }
        return null; // 見つからない/失敗
    }

    // 新規登録（メール＋パスワードをINSERT）
    public boolean create(String email, String pass) {
        String sql = "INSERT INTO users(email, pass) VALUES(?, ?)";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email); // 1番目: email
            ps.setString(2, pass);  // 2番目: pass
            return ps.executeUpdate() == 1; // 1行なら成功
        } catch (SQLException e) {
            e.printStackTrace(); // 学習用
        }
        return false; // 失敗
    }
}
