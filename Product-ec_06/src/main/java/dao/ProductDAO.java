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
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("JDBCドライバーのロード失敗：" + e.getMessage());
		}
	}

	private static final String URL = "jdbc:mysql://localhost:3306/golismart?zeroDateTimeBehavior=CONVERT_TO_NULL";
	private static final String USER = "root";
	private static final String PASS = "";

	public void insert(Product p) {
		String sql = "INSERT INTO products(name,price)VALUES(?,?)";
		try (Connection c = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, p.getName());
			ps.setBigDecimal(2, p.getPrice());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("DB登録失敗：" + e.getMessage());
		}
	}

	public List<Product> findAll() {
		List<Product> list = new ArrayList<>();
		String s = "SELECT * FROM products";
		try (Connection c = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = c.prepareStatement(s)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				BigDecimal price = rs.getBigDecimal("price");
				list.add(new Product(id, name, price));
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB登録失敗：" + e.getMessage());
		}
		return list;
	}

}