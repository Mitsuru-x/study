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
@WebServlet("/servlet")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Product> products = productDAO.findAll();
		request.setAttribute("products", products);
		request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String priceStr = request.getParameter("price");
		BigDecimal price = new BigDecimal(priceStr);
		LocalDateTime createdAt = LocalDateTime.now();
		Product product = new Product(0, name, price, createdAt);
		try {
			productDAO.insert(product);
		} catch (RuntimeException e) {
			// TODO: handle exception
			request.setAttribute("error","登録に失敗しました"+e.getMessage());
		}
	}

}
