package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Message;

@WebServlet("/messages")
public class MessageServlet extends HttpServlet {
	private static final List<Message> STORE = new ArrayList<>();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("messages", STORE);
		request.getRequestDispatcher("/WEB-INF/view/messages.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");
		if("clear".equals(action)) {
			STORE.clear();
			response.sendRedirect(request.getContextPath()+"/messages");
			return;
		}
		
		String userParam = request.getParameter("user");
		if(userParam != null && !userParam.isBlank()) {
			session.setAttribute("userName", userParam);
		}
		String user = (String)session.getAttribute("userName");
		if(user == null || user.isBlank()) {
			user = "ゲスト";
			session.setAttribute("userName", user);
		}
		String text = request.getParameter("text");
		if(text != null && !text.isBlank()) {
			STORE.add(new Message(text, user));
		}
		response.sendRedirect(request.getContextPath()+"/messages");
	}

}













