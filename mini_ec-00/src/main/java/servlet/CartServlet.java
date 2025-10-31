package servlet; // コントローラ

import java.io.IOException;     // 例外
// Listなど
import java.util.ArrayList;
import java.util.List;

// 画面遷移
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // アノテーション
// HTTP
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ProductDAO;          // 商品参照DAO
import model.CartItem;          // カート行
import model.Product;           // 商品モデル

@WebServlet("/cart") // URL: /cart
public class CartServlet extends HttpServlet {

    @SuppressWarnings("unchecked") // 型安全警告を抑制（学習用）
    private List<CartItem> cart(HttpSession session) { // カート取得ヘルパ
        Object o = session.getAttribute("cart"); // 取り出し
        if (o == null) {                         // 未作成なら
            List<CartItem> c = new ArrayList<>(); // 新規リスト
            session.setAttribute("cart", c);      // 保存
            return c;
        }
        return (List<CartItem>) o;               // 既存を返す
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8"); // 文字コード
        String action = req.getParameter("action"); // "add" or "remove"
        String pidStr = req.getParameter("pid");    // 商品ID（文字列）

        // pidの簡易バリデーション
        int pid;
        try {
            pid = Integer.parseInt(pidStr);
        } catch (Exception e) {
            // 不正なpid → カート画面へ
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        HttpSession ses = req.getSession();
        List<CartItem> cart = cart(ses); // セッションからカート取得

        if ("add".equals(action)) {
            // 既にあれば数量+1
            for (CartItem it : cart) {
                if (it.getProductId() == pid) {
                    it.addOne();
                    resp.sendRedirect(req.getContextPath() + "/products"); // PRGで一覧へ戻す
                    return;
                }
            }
            // 新規追加（DBから商品情報を取得）
            Product p = new ProductDAO().findById(pid);
            if (p != null) {
                cart.add(new CartItem(p.getId(), p.getName(), p.getPrice(), 1)); // 1個
                resp.sendRedirect(req.getContextPath() + "/products"); // 追加後は一覧へ
                return;
            } else {
                // 該当商品なし → カートへ
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
        }

        if ("remove".equals(action)) {
            // 削除（条件一致を全て取り除く）
            cart.removeIf(it -> it.getProductId() == pid);
            resp.sendRedirect(req.getContextPath() + "/cart"); // PRG：カート画面へ
            return;
        }

        // 不正actionはカートへ
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp); // カート画面へ
    }
}
