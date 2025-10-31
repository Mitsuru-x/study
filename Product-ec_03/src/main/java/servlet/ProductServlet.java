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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
//		変更するを押してupdate.jspへ移管するところ
		String idStr = request.getParameter("id");
		if(idStr != null) {
			int id = Integer.parseInt(idStr);
			Product product = productDAO.findById(id);
			request.setAttribute("p", product);
			request.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(request, response);
			return;
		}
		
//		一覧
		List<Product> products = productDAO.findAll();
		request.setAttribute("products", products);
		request.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
//		削除するところ
		String action = request.getParameter("action");
		if ("delete".equals(action)) {
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			System.out.println(id);
			productDAO.delete(id);
			response.sendRedirect(request.getContextPath()+"/products");
			return;
		}

//		変更するところ
		if("update".equals(action)) {
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			String name = request.getParameter("name");
			String priceStr = request.getParameter("price");
			System.out.println("[DEBUG] price raw='" + priceStr + "'");
			BigDecimal price = new BigDecimal(priceStr);
			System.out.println("[DEBUG] price raw='" + priceStr + "'");
			productDAO.update(id,name,price);
			response.sendRedirect(request.getContextPath()+"/products");
			return;
		}
		
		
		
//		登録するところ
		String name = request.getParameter("name");
		String priceStr = request.getParameter("price");
		BigDecimal price = new BigDecimal(priceStr);
		LocalDateTime created_at = LocalDateTime.now();
		Product product = new Product(0,name,price,created_at);
		try {
			productDAO.insert(product);
		}catch (RuntimeException e) {
			// TODO: handle exception
			request.setAttribute("erro","登録に失敗しました:"+e.getMessage());
		}
		response.sendRedirect(request.getContextPath()+"/products"); 
		return;
	}

}
