package com.lagrange;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

@WebServlet("/compute")
public class ComputeLagrange extends HttpServlet{
	
	public static final long serialVersionUID = 1L;
	
	public ComputeLagrange() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map <String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		String points_list = request.getParameter("points_list");
		if(points_list != null && points_list.trim().length() != 0) {
			isValid = true;
			map.put("points_list", points_list);
		}
		map.put("isValid", isValid);
		write(response, map);
		System.out.println("Java here");
	}

	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
}
