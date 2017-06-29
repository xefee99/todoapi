package kr.ac.seoultech.todo.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseUtil {

	public static void write(HttpServletResponse response, int HttpStatusCode, Object result) throws IOException {
		write(response, HttpStatusCode, result, true);
	}
	
	public static void write(HttpServletResponse response, int HttpStatusCode, Object result, boolean includeNulls) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatusCode);
		
		String resultJson = convertToJson(result, includeNulls);
		if (resultJson != null) {
			PrintWriter out = response.getWriter();
			out.print(resultJson);
			out.flush();
		}		
	}

	private static String convertToJson(Object result, boolean includeNulls) {
		if (result == null) return null;
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		if (includeNulls) gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();
		
		String json = gson.toJson(result);
		return json;
	}
	
}
