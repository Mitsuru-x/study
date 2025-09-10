package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TweetServlet
 */
@WebServlet("/TweetServlet")
public class TweetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	private static final List<Tweet> tweets = Collections.synchronizedList( new ArrayList<>());
	
	private static class Tweet {
		final String text;
		final String time;
		Tweet(String text,String time){
			this.text = text;
			this.time = time;
		}
	}
	private static DateTimeFormatter FMT =
			DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	private static String escape(String s) {
		if(s == null) return "";
		return s.replace("&", "&amp")
				.replace("<", "&lt")
				.replace(">", "&gt;")
				.replace("\"", "&quot;");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String raw = request.getParameter("text");
		
		if(raw != null && !raw.isBlank()) {
			String safe = escape(raw.trim());
			String now = LocalDateTime.now().format(FMT);
			
			tweets.add(0,new Tweet(safe , now));
		}
		
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
			out.println("<!doctype html><meta charset=\"UTF-8\">");
			out.println("<h1>MiniTweet タイムライン</h1>");
			out.println("<ul>");
			synchronized (tweets) {
				for(Tweet t : tweets) {
					out.println("<li><span style=\"color:#666\">["+t.time+"]</span>"+t.text+"</li>");
				}
			}
			out.println("</ul>");
			out.println("<p><a href=\""+request.getContextPath()+"/tweet.jsp\">戻る</a></p>");
		}
	}

}
