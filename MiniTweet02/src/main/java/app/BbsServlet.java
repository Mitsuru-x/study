package app;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BbsServlet
 */
@WebServlet("/BbsServlet")
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final java.util.List<String>messages=
			java.util.Collections.synchronizedList(new java.util.ArrayList<>());
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!doctype html");
		out.println("<html><head><meta charset=\"UTF-8\"><title>掲示板</title></head><body>");
		out.println("<h1>簡単な掲示板</h1>");
		out.println("<form method=\"post\">");
		out.println("メッセージ:<input type=\"text\" name=\"msg\">");
		out.println("<input type=\"submit\" value=\"投稿\">");
		out.println("</formn>");
		out.println("<h2>投稿一覧</h2>");
		out.println("<ul>");
		synchronized (messages) {
		    for (String m : messages) {
		        out.println("<li>" + escape(m) + "</li>");
		    }
		}
		out.println("</ul>");

		out.println("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    String msg = request.getParameter("msg");
	    if (msg != null) {
	        msg = msg.trim();
	        if (!msg.isEmpty()) {
	            messages.add(msg);     // ← メモリに保存
	        }
	    }
	    // PRG: 送信後はGETにリダイレクトして二重投稿を防ぐ
	    response.sendRedirect("BbsServlet"); // マッピングに合わせて
	}
	private static String escape(String s) {
	    return s.replace("&", "&amp;")
	            .replace("<", "&lt;")
	            .replace(">", "&gt;")
	            .replace("\"", "&quot;");
	}



}
