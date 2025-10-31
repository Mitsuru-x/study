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
	private static final String USER ="root";
	private static final String PASS ="";
	
	public void insert(Product product) {
		String sql = "insert into products (name,price,created_at) values (?,?,?)";
		try(Connection conn = DriverManager.getConnection(URL,USER,PASS);
			PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, product.getName());
			ps.setBigDecimal(2, product.getPrice());
			ps.setObject(3, product.getCreatedAt());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("DB登録失敗: " + e.getMessage(), e);
		}
	}
	public List<Product> findAll() {
		List<Product> list = new ArrayList<>();;
		String sql = "select id,name,price,created_at from products order by id desc"	;
		try(Connection conn = DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				BigDecimal price = rs.getBigDecimal("price");
				java.sql.Timestamp ts = rs.getTimestamp("created_at");
				LocalDateTime createdAt = (ts != null) ? ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				list.add(new Product(id,name,price,createdAt));
			}
		}catch(SQLException e) {
			throw new RuntimeException("DB取得失敗: " + e.getMessage(), e);
		}
		return list;
	}
}