package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDAO {
	static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // [標準API] MySQLドライバー登録（要約：JDBCがDBに接続できるようにする）
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBCドライバーのロード失敗: " + e.getMessage(), e); // [独自] 例外集約
        }
    }
	private static final String URL = "jdbc:mysql://localhost:3306/golismart?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public void insert(Product p) {
		String sql = "INSERT INTO products(name,price) VALUES(?,?)";
		try (Connection conn = DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, p.getName());
			ps.setBigDecimal(2, p.getPrice());
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("insert失敗: " + e.getMessage(), e);
		}
	}
    
    public List<Product> findAll() {
    		List<Product> list = new ArrayList<>();
    		String sql = "SELECT id,name,price FROM products ODER BY id DESC";
    		try (Connection conn = DriverManager.getConnection(URL, USER, PASS); // [標準API] DB接続
    	             PreparedStatement ps = conn.prepareStatement(sql); // [標準API] SQL安全実行
    	             ResultSet rs = ps.executeQuery()) { // [標準API] SQL結果取得
    			while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					BigDecimal price = rs.getBigDecimal("price");
					list.add(new Product(id,name,price));
    		}
    } catch (SQLException e) {
        // ERROR: 商品一覧取得失敗 [独自] ログ例：ERROR Product一覧例外 message=...
        throw new RuntimeException("DB取得失敗: " + e.getMessage(), e); // [独自] 例外集約
    }
    return list; // [独自] 商品リスト返却
	}
}
