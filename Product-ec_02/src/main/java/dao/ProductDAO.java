package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDAO {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("JDBCドライバーのロード失敗: " + e.getMessage(), e);
		}
	}
	private static final String URL = "jdbc:mysql://localhost:3306/golismart?zeroDateTimeBehavior=CONVERT_TO_NULL";
	private static final String USER = "root";
	private static final String PASS = "";

	public void insert(String name,BigDecimal price,LocalDateTime createdAt) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "INSERT INTO products (name,price,created_at) VALUES (?,?,?)";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setBigDecimal(2, price);
			ps.setObject(3, java.sql.Timestamp.valueOf(createdAt));

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("DB登録失敗: " + e.getMessage(), e);
		}
		

	}

	public List<Product> findAll() {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT id,name,price,created_at FROM products ORDER BY id DESC";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				BigDecimal price = rs.getBigDecimal("price");
				java.sql.Timestamp ts = rs.getTimestamp("created_at");
				LocalDateTime createdAt = (ts != null) ? ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				list.add(new Product(id, name, price, createdAt));
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB取得失敗: " + e.getMessage(), e);
		}
		return list;
	}
//	削除
	public void delete(int id) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "DELETE FROM products WHERE id=?";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("DB登録失敗: " + e.getMessage(), e);
		}
	}
//	変更
	public void update(int id,String name,BigDecimal price) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "UPDATE products SET name=?,price=? WHERE id=?";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setBigDecimal(2, price);
			ps.setInt(3, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("DB登録失敗: " + e.getMessage(), e);
		}
	}
}