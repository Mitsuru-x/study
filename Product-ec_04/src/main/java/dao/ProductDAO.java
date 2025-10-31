package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	private static final String USER ="root";
	private static final String PASS ="";
	
	public void insert(Product p) {
		String sql = "INSERT INTO products (name, price, created_at) VALUES (?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setString(1, p.getName());
			ps.setBigDecimal(2, p.getPrice());
			ps.setObject(3, p.getCreatedAt());
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("insert失敗: " + e.getMessage(), e);
		}
	}
	public List<Product> findAll() {
		List<Product> list = new ArrayList<>();
		String spl = "SELECT id, name, price, created_at FROM products ORDER BY id DESC";
		try(Connection conn = DriverManager.getConnection( URL,USER,PASS);
				PreparedStatement ps = conn.prepareStatement(spl);){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				BigDecimal price = rs.getBigDecimal("price");
				java.sql.Timestamp ts = rs.getTimestamp("created_at");
				LocalDateTime createdAt = (ts != null) ? ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				list.add(new Product(id, name, price, createdAt));
			}
			
		} catch (Exception e) {
			throw new RuntimeException("findAll失敗: " + e.getMessage(), e);
		}
		return list;
	}
}