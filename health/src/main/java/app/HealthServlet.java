package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Health;

// 役割: GETでフォーム表示、POSTで計算→結果をフォワード。履歴はセッションに保存。
@WebServlet("/health") // URL: /health にマッピング
public class HealthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // doGetの目的: 初回表示や「もう一度」押下時にフォームを見せる
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 文字コード(受信側)はGETでは通常不要だが、統一のため明示してもOK
        req.setCharacterEncoding("UTF-8"); // フォームの項目名解釈をUTF-8に
        // health_form.jsp へフォワード（サーバー内転送）
        req.getRequestDispatcher("/health_form.jsp").forward(req, resp);
    }

    // doPostの目的: 入力を受け取り、Healthを生成し、結果JSPに渡す
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // POST本文のデコードをUTF-8で

        // 1) パラメータ取得（未入力や数値でないケースに備え安全にパース）
        String h = req.getParameter("height"); // "170" など
        String w = req.getParameter("weight"); // "65"  など

        double heightCm = parseOr(req, "height", h, 0.0); // 不正なら0.0
        double weightKg = parseOr(req, "weight", w, 0.0); // 不正なら0.0

        // 2) バリデーション（最低限: 正の数か）
        if (heightCm <= 0 || weightKg <= 0) {
            req.setAttribute("error", "身長と体重は正の数で入力してください。");
            // 入力値も戻してUXを良くする
            req.setAttribute("prevHeight", h);
            req.setAttribute("prevWeight", w);
            req.getRequestDispatcher("/health_form.jsp").forward(req, resp);
            return;
        }

        // 3) モデル生成 & リクエスト属性に載せる
        Health health = new Health(heightCm, weightKg); // 計算はコンストラクタ内で実施
        req.setAttribute("health", health);

        // 4) 履歴: セッションに List<Health> を保持して追記
        HttpSession session = req.getSession(); // ユーザー毎の保存領域
        @SuppressWarnings("unchecked")
        List<Health> history = (List<Health>) session.getAttribute("history");
        if (history == null) {                 // 初回なら新規作成
            history = new ArrayList<>();
            session.setAttribute("history", history);
        }
        history.add(health);                   // 記録

        // 5) 結果画面へフォワード（URLはそのまま/healthのまま）
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    // 補助メソッドの目的: 数値パースの失敗時にデフォルト値を返す + エラーメッセージ格納
    private double parseOr(HttpServletRequest req, String field, String raw, double def) {
        try {
            return Double.parseDouble(raw);
        } catch (Exception e) {
            req.setAttribute("error", "項目「" + field + "」が数値として解釈できません: " + raw);
            return def;
        }
    }
}
