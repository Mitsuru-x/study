package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO = new ProductDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Product> products = productDAO.findAll();
		request.setAttribute("products", products);
		request.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
//		全て取得
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !idStr.isEmpty()) {
		    id = Integer.parseInt(idStr);
		}

//		actionの取得
		String action = request.getParameter("action");
		
//		削除
		if("delete".equals(action)) {
			try {
				productDAO.delete(id);
			} catch (RuntimeException e) {
				request.setAttribute("error", "登録に失敗しました: " + e.getMessage());
			}
			response.sendRedirect(request.getContextPath() + "/products");
			return;
		}
		
		String name = request.getParameter("name");
		String priceStr = request.getParameter("price");
		BigDecimal price = new BigDecimal(priceStr);
		LocalDateTime producedAt = LocalDateTime.now();
//		変更
		if("update".equals(action)) {
			try {
				productDAO.update(id,name,price);
			} catch (RuntimeException e) {
				request.setAttribute("error", "登録に失敗しました: " + e.getMessage());
			}
			response.sendRedirect(request.getContextPath() + "/products");
			return;
		}
		
		
//		登録
		try {
			productDAO.insert( name, price, producedAt);
		} catch (RuntimeException e) {
			// TODO: handle exception
			request.setAttribute("error", "登録に失敗しました: " + e.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/products");
	}

}
