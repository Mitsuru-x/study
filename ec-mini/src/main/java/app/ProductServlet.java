package app;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import app.model.Product;

/**
 * 商品一覧を表示するサーブレット
 */
@WebServlet("/Products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 学習用：商品カタログ（固定）
    private static final Map<String, Product> CATALOG = new LinkedHashMap<>();
    static {
        CATALOG.put("p1", new Product("p1", "コーヒー豆 200g", 980));
        CATALOG.put("p2", new Product("p2", "ドリッパー", 1200));
        CATALOG.put("p3", new Product("p3", "マグカップ", 800));
        CATALOG.put("p4", new Product("p4", "フィルター(100枚)", 350));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 商品リストをJSPへ渡す
        request.setAttribute("catalog", CATALOG.values());

        // products.jsp へフォワード
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    // CartServlet から参照するためのヘルパー
    public static Product findById(String id) {
        return CATALOG.get(id);
    }
}
