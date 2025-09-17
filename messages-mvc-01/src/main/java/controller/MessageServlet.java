package controller;                                          // Controller層パッケージ
import java.io.IOException;                                      // 例外型
import java.util.ArrayList;                                      // List実装（学習用に最小）
import java.util.List;                                           // コレクションの型

import jakarta.servlet.RequestDispatcher;                         // フォワード用
import jakarta.servlet.ServletException;                          // サーブレット例外
import jakarta.servlet.annotation.WebServlet;                     // ルーティング用アノテーション
import jakarta.servlet.http.HttpServlet;                          // 基底サーブレット
import jakarta.servlet.http.HttpServletRequest;                   // リクエスト
import jakarta.servlet.http.HttpServletResponse;                  // レスポンス
import jakarta.servlet.http.HttpSession;                          // セッション

import model.Message;

@WebServlet("/messages")                                         // このサーブレットのURLを /messages に固定
public class MessageServlet extends HttpServlet {                 // コントローラ本体
    private static final List<Message> STORE = new ArrayList<>(); // 学習用メモリ保存（超最小：再起動で消える）

    @Override                                                     // GETは「表示」担当
    protected void doGet(HttpServletRequest request,              // リクエスト
                          HttpServletResponse response)           // レスポンス
                          throws ServletException, IOException {  // 例外委譲
        request.setAttribute("messages", STORE);                  // Viewに一覧を渡す（リクエスト属性）
        RequestDispatcher rd =                                    // JSPへの転送器を取得
            request.getRequestDispatcher("/WEB-INF/views/messages.jsp"); // 直リンク不可の安全な配置
        rd.forward(request, response);                            // サーバ内でJSPへフォワード
    }

    @Override                                                     // POSTは「追加・クリア」担当
    protected void doPost(HttpServletRequest request,             // フォーム送信で呼ばれる
                           HttpServletResponse response)          // レスポンス
                           throws ServletException, IOException { // 例外委譲
        request.setCharacterEncoding("UTF-8");                    // POST本文の文字化け対策
        HttpSession session = request.getSession();               // セッションを取得（なければ作成）

        String action = request.getParameter("action");           // 追加/クリアの分岐用パラメータ
        if ("clear".equals(action)) {                             // 全消去が押されたとき
            STORE.clear();                                        // メモリ上の一覧を空にする
            response.sendRedirect(request.getContextPath()+"/messages"); // PRG: リダイレクトでGETへ
            return;                                               // 以降を実行しない
        }

        String userParam = request.getParameter("user");          // 入力ユーザー名を取得
        if (userParam != null && !userParam.isBlank()) {          // 空でなければ
            session.setAttribute("userName", userParam);          // セッションに保存/更新
        }
        String user = (String) session.getAttribute("userName");  // 既存のセッション名を読む
        if (user == null || user.isBlank()) {                     // 未設定の場合は
            user = "ゲスト";                                       // 仮の表示名にする
            session.setAttribute("userName", user);               // セッションへ保存
        }

        String text = request.getParameter("text");               // 投稿本文を取得
        if (text != null && !text.isBlank()) {                    // 空でなければ
            STORE.add(new Message(text, user));                   // モデルを生成して保存
        }

        response.sendRedirect(request.getContextPath()+"/messages"); // PRG: 二重送信防止のためGETへ
    }
}
