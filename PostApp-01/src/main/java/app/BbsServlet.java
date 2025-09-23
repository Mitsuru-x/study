package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BbsServlet
 */
@WebServlet("/bbs")
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	private List<String> ensureStore(ServletContext app){
		Object obj = app.getAttribute("mutterList");
		if(obj == null) {
			List<String> store = Collections.synchronizedList(new ArrayList<>());
			app.setAttribute("mutterList", store);
			return store;
		}
		return (List<String>)obj;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext app = getServletContext();
		List<String> store = ensureStore(app);
		request.setAttribute("mutters", store);
		request.getRequestDispatcher("/WEB-INF/bbs.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String text =request.getParameter("text");
		if(text != null && !text.isBlank()) {
			ServletContext app = getServletContext();
			List<String> store = ensureStore(app);
			store.add(text.trim());
		}
		response.sendRedirect(request.getContextPath()+"/bbs");
	}

}
