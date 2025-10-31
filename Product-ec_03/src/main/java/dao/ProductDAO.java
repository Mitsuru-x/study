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
			// TODO: handle exception
			throw new RuntimeException("JDBCドライバーのロード失敗"+e.getMessage(),e);
		}
	}
	private static final String URL = "jdbc:mysql://localhost:3306/golismart?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public void insert(Product product) {
    		String sql = "INSERT INTO products(name,price,created_at) VALUES(?,?,?)";
    		try (Connection conn = DriverManager.getConnection(URL, USER, PASS); // [標準API] DB接続
             PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1,product.getName());
			ps.setBigDecimal(2, product.getPrice());
			ps.setObject(3, product.getCreatedAt());
			ps.executeUpdate();
			} catch (SQLException e) {
				// TODO: handle exception
				 throw new RuntimeException("DB登録失敗: " + e.getMessage(), e); 
			}
		
	}
    public List<Product>findAll() {
    		List<Product> list = new ArrayList<>();
    		String sql = "SELECT id,name,price,created_at FROM products ORDER BY id DESC";
    		System.out.println("SQL = [" + sql + "]");
    		try (Connection conn = DriverManager.getConnection(URL,USER,PASS);
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
    			
    		}catch (SQLException e) {
    				// TODO: handle exception
    				throw new RuntimeException("DB取得失敗: " + e.getMessage(), e);
    		}
    		return list;
	}
    
//    削除
    public void delete(int id) {
		String sql = "DELETE FROM products WHERE id=?;";
		System.out.println("SQL = [" + sql + "]");
		try (Connection conn = DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement ps = conn.prepareStatement(sql);){
				ps.setInt(1,id);
				ps.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
				System.out.println("SQL = [" + sql + "]");
            throw new RuntimeException("DB取得失敗: " + e.getMessage(), e); // [独自] 例外集約
            
		}
    }
//    テーブル1件だけ表示 のちに変更する為
    public Product findById(int id) {
    		String sql = "SELECT id, name, price, created_at FROM products WHERE id=?;";
		System.out.println("SQL = [" + sql + "]");
		try (Connection conn = DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement ps = conn.prepareStatement(sql);){
				ps.setInt(1,id);
				try(ResultSet rs = ps.executeQuery()){
					if(rs.next()) {
						int pid = rs.getInt("id");
						String name = rs.getString("name");
						BigDecimal price = rs.getBigDecimal("price");
						LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
						Product product = new Product(pid,name,price,createdAt);
						return product;
					}else {
						return null;
					}
				}
			
		}catch (SQLException e) {
			// TODO: handle exception
				System.out.println("SQL = [" + sql + "]");
            throw new RuntimeException("DB表示表示失敗" + e.getMessage(), e); // [独自] 例外集約
		}
	}
//    変更する
    public int update(int id,String name,BigDecimal price) {
		String sql = "UPDATE products SET name=?,price=?  WHERE id=?";
		try (Connection conn = DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement ps = conn.prepareStatement(sql);){
				ps.setString(1,name);
				ps.setBigDecimal(2, price);
				ps.setInt(3, id);
				int update =  ps.executeUpdate();
				return update;
		}catch (SQLException e) {
			// TODO: handle exception
				System.out.println("SQL = [" + sql + "]");
            throw new RuntimeException("DB更新: " + e.getMessage(), e); // [独自] 例外集約
            
		}
    }
    
}