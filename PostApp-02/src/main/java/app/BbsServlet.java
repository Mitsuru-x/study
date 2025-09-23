package app;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Message;

/**
 * Servlet implementation class BbsServlet
 */
@WebServlet("/bbs")
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	private List<Message> ensureStore(ServletContext app){
		Object obj = app.getAttribute("messageList");
		if(obj == null) {
			List<Message> store = Collections.synchronizedList(new ArrayList<>());
			app.setAttribute("messageList", store);
			return store;
		}
		return (List<Message>)obj;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext app = getServletContext();
		List<Message> store = ensureStore(app);
		request.setAttribute("messages", store);
		
		request.getRequestDispatcher("/WEB-INF/bbs.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");
		if(text != null && !text.isBlank()) {
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
			ServletContext app = getServletContext();
			List<Message> store = ensureStore(app);
			store.add(new Message(text.trim(), now));
		}
		response.sendRedirect(request.getContextPath()+"/bbs");
	}

}
