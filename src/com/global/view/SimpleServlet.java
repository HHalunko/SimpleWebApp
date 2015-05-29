package com.global.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;



import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.json.JSONException;
import org.json.JSONObject;

import com.global.model.SearchEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet(description = "Some description", urlPatterns = { "/SimpleServlet" })
@MultipartConfig
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");

		if (ServletFileUpload.isMultipartContent(request)) {

			try {

				Part filePart = request.getPart("inputFile");

				InputStream fileContent = filePart.getInputStream();
				String folderPath = "C:\\temp";
				File inputFile = new File(folderPath);
				inputFile.mkdirs();
				File file = new File(folderPath+"\\result.txt");
				OutputStream os = new FileOutputStream(file);
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = fileContent.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				fileContent.close();
				os.close();
				String q = request.getParameter("q");
				Integer limit;
				Integer length;
				if (!request.getParameter("limit").equals(""))
					limit = new Integer(request.getParameter("limit"));
				else
					limit = new Integer(0);
				if (!request.getParameter("length").equals(""))
					length = new Integer(request.getParameter("length"));
				else
					length = new Integer(0);
				Boolean metaData = new Boolean(request.getParameter("metaData"));
				JSONObject Jobj = new JSONObject();
				JSONObject Jmd = new JSONObject();
				ArrayList<String> list = new ArrayList<String>();
				try {
					String[] arr = SearchEngine
							.searchMethog(file, limit.intValue(), q,
									length.intValue(), metaData.booleanValue())
							.toString().split("\r\n");
					if (q.equals(null)) {
						try {
							Jobj.put("text", file.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						for (int i = 0; i < arr.length; i++) {
							list.add(arr[i]);
						}
					}
				} catch (NullPointerException e) {
					response.sendRedirect("index.html");
				}

				Jobj.put("text", list);

				if (metaData.equals(true)) {

					Jmd.put("fileName", file.getName());
					Jmd.put("fileSize", file.length());
					Jmd.put("fileCreationDate",
							new Date(file.lastModified()));
					Jobj.put("metaData", Jmd);

				}
				PrintWriter out = response.getWriter();
				out.write(Jobj.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}