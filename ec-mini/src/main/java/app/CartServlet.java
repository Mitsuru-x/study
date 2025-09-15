package app;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import app.model.Product;

/**
 * カートの表示/追加/削除をまとめて担当
 */
@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // セッション上のカートを取り出す（なければ作る）
    @SuppressWarnings("unchecked")
    private Map<String, Integer> getCart(HttpSession session){
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null){
            cart = new LinkedHashMap<>(); // 追加順を保つ
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        Map<String, Integer> cart = getCart(session);

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if ("add".equals(action) && id != null){
            cart.merge(id, 1, Integer::sum); // 既にあれば+1
        } else if ("remove".equals(action) && id != null){
            cart.remove(id);
        } else if ("clear".equals(action)){
            cart.clear();
        }

        // 表示へ
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);
        Map<String, Integer> cart = getCart(session);

        // 合計金額を計算
        int total = 0;
        for (Map.Entry<String, Integer> e : cart.entrySet()){
            Product p = ProductServlet.findById(e.getKey());
            if (p != null) total += p.getPrice() * e.getValue();
        }

        request.setAttribute("cart", cart);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}
