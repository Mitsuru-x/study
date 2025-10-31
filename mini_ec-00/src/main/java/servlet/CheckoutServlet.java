package servlet; // コントローラ

import java.io.IOException;    // 例外
import java.util.List;         // List

// 画面遷移
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // アノテ
// HTTP
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.OrderDAO;           // 注文DAO
import model.CartItem;         // 明細
import model.User;             // 会員

@WebServlet("/checkout") // URL: /checkout
public class CheckoutServlet extends HttpServlet {

    // 購入処理（POST専用）
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession ses = req.getSession();

        // 認証チェック
        User login = (User) ses.getAttribute("login");
        if (login == null) {
            resp.sendRedirect(req.getContextPath() + "/auth"); // 未ログイン → 認証画面へ
            return;
        }

        // カート取得
        List<CartItem> cart = (List<CartItem>) ses.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/products"); // 空カート → 商品一覧へ
            return;
        }

        try {
            // 注文作成（トランザクションは OrderDAO 内）
            int orderId = new OrderDAO().create(login.getId(), cart);

            // カートを空にする（購入完了）
            ses.removeAttribute("cart");

            // 完了画面へ
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(req, resp);

        } catch (Exception e) {
            // 在庫不足などの失敗時：メッセージを出してカートへ戻す
            req.setAttribute("msg", "購入に失敗しました：" + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
        }
    }

    // 誤ってGETされた場合は商品一覧へ
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
