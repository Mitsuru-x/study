package servlet; // コントローラ層

import java.io.IOException;  // 例外

// 画面遷移
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // アノテーション
// HTTP
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;          // DAO
import model.User;           // モデル

@WebServlet("/auth") // URL: /auth
public class AuthServlet extends HttpServlet { // 認証コントローラ

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp); // ログイン/登録画面へ
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8"); // 文字化け対策

        String action = req.getParameter("action");        // "login" or "register"
        String email  = trim(req.getParameter("email"));   // 入力メール
        String pass   = trim(req.getParameter("pass"));    // 入力パス

        // 入力バリデーション（簡易）
        if (email.isEmpty() || pass.isEmpty()) {
            req.setAttribute("msg", "メールとパスワードを入力してください。");
            doGet(req, resp);
            return;
        }
        if (!"login".equals(action) && !"register".equals(action)) {
            req.setAttribute("msg", "不正な操作です。");
            doGet(req, resp);
            return;
        }

        UserDAO dao = new UserDAO(); // DAO

        if ("register".equals(action)) {
            boolean ok = dao.create(email, pass); // 新規登録（学習用: 平文パス）
            if (ok) {
                req.setAttribute("msg", "登録完了。ログインしてください。");
            } else {
                req.setAttribute("msg", "登録失敗（既に登録済みの可能性があります）。");
            }
            doGet(req, resp); // 画面再表示
            return;
        }

        if ("login".equals(action)) {
            User u = dao.findByLogin(email, pass); // 認証
            if (u != null) {
                // 成功: セッションに保存 → PRGで商品一覧へ
                req.getSession().setAttribute("login", u);
                resp.sendRedirect(req.getContextPath() + "/products");
                return;
            } else {
                req.setAttribute("msg", "ログイン失敗（メールまたはパスワードが違います）。");
                doGet(req, resp);
                return;
            }
        }
    }

    // null安全なtrim（null→""）
    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
