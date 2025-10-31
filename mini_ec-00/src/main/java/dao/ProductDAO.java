package dao; // DAO層

// JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Listなど
import java.util.ArrayList;
import java.util.List;

import model.Product;  // モデル

// 商品関連のDB操作クラス
public class ProductDAO {

    // 全商品一覧を返す
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>(); 
        String sql = "SELECT id, name, price, stock FROM products ORDER BY id";

        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 学習用: エラー出力
        }
        return list;
    }

    // 主キー検索（1件だけ）
    public Product findById(int id) {
        String sql = "SELECT id, name, price, stock FROM products WHERE id=?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id); // バインド（? に id を設定）

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 学習用
        }
        return null; // 見つからないとき
    }
}
