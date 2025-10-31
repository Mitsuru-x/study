package servlet; // コントローラ層
import java.io.IOException;
// 例外
import java.util.List;

import jakarta.servlet.ServletException;
// DAO
 // モデル
// 画面遷移
import jakarta.servlet.annotation.WebServlet; // アノテ
// HTTP
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;
 // List
 @WebServlet("/products")
 // URL: /products
 public class ProductServlet extends HttpServlet { // 商品一覧表示
@Override
 // GETだけ使用
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
 ServletException, IOException {
 List<Product> products = new ProductDAO().findAll(); // DBから一覧
req.setAttribute("products", products);
 // JSPへ渡す
req.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(req, resp); // フォワード
}
 }