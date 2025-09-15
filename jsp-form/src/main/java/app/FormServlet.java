package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字コードを UTF-8 に設定
        request.setCharacterEncoding("UTF-8");

        // フォームから送られた名前を取得
        String name = request.getParameter("name");

        // === 履歴リストをセッションスコープに保持（ユーザーごと） ===
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        List<String> names = (List<String>) session.getAttribute("names");
        if (names == null) {
            names = Collections.synchronizedList(new ArrayList<>());
            session.setAttribute("names", names);
        }
        if (name != null && !name.isBlank()) {
            names.add(name);
        }

        // JSP に渡す（リクエスト属性）
        request.setAttribute("userName", name);
        request.setAttribute("names", names);

        // Output.jsp へフォワード
        request.getRequestDispatcher("/Output.jsp").forward(request, response);
    }
}
