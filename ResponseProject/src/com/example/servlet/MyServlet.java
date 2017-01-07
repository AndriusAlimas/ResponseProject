package com.example.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
// this time this servlet deals with GET and POST request
public class MyServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException{
		// ServletResponse interface has a method setContentType
		// Sets the content type of the response being sent to the client, if the response has not 
		// been committed yet. The given content type may include a character encoding specification,
		// for example, text/html;charset=UTF-8. The response's character encoding is only set from the
		// given content type if this method is called before getWriter is called.
		// This method may be called repeatedly to change content type and character encoding. This
		// method has no effect if called after the response has been committed. It does not set the 
		// response's character encoding if it is called after getWriter has been called or after the
		// response has been committed.Containers must communicate the content type and the character
		// encoding used for the servlet response's writer to the client if the protocol provides a 
		// way for doing so. In the case of HTTP, the Content-Type header is used.
		response.setContentType("text/html");
		
		/* ServletResponse interface has a method getWriter()
		 Returns a PrintWriter object that can send character text to the client. The PrintWriter 
		 uses the character encoding returned by getCharacterEncoding(). If the response's character
		 encoding has not been specified as described in getCharacterEncoding (i.e., the method just
		 returns the default value ISO-8859-1), getWriter updates it to ISO-8859-1. Calling flush() on
		 the PrintWriter commits the response.Either this method or getOutputStream() may be called
		to write the body, not both. */
        PrintWriter out = response.getWriter();
        
        /* ServletResponse interface has method getBufferSize()
           Sets the preferred buffer size for the body of the response. The servlet container will use a
           buffer at least as large as the size requested. The actual buffer size used can be found 
           using getBufferSize.A larger buffer allows more content to be written before anything is 
           actually sent, thus providing the servlet with more time to set appropriate status codes
           and headers. A smaller buffer decreases server memory load and allows the client to start 
           receiving data more quickly. This method must be called before any response body content is
           written; if content has been written or the response object has been committed, this method 
         throws an IllegalStateException. */
        int bufferSize = response.getBufferSize();
        out.println("<html><head><title>My Response Page</title></head>"
        		+ "<body><h2>Used buffer response size: " + bufferSize + "</body></html>");
	}
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)throws ServletException,IOException{
			// using request object getParameter value to know if you want to download a file
			// you get this parameter from form and store to String value to use it later
		 	String answer = request.getParameter("download");
		 	// just simply file name, later we will use to concatinate realPath 
	        String file_name = "love.jpg";
	        // using response object we set content type will be image jpeg, that we want
	        response.setContentType("image/jpeg");
	     
	        int n_byte = 0;
	        // using switch statment we check answers what to do next:
	        switch (answer) {
	        // download case:
            case "download": {
            	// this - is simply running servlet, each servlets has own servletContext object
            	// when we get servlet context object we can do some actions with it
            	// one of them to get realPath
                String realPath = this.getServletContext().getRealPath(File.separator);
                // File where should server look for:
                File fileToDownload = new File(realPath + "/img/" + file_name);
                
                // HttpServletResponse interface has method addHeader(String name, String value)
                // Adds a response header with the given name and value. This method allows
                // response headers to have multiple values.
                // this how we set to download file, to add in header information:
                response.addHeader("Content-Disposition", "attachment; filename=" + file_name);
                
                // ServletResponse interface has setContentLength(int) method
                // Sets the length of the content body in the response In HTTP servlets,
                // this method sets the HTTP Content-Length header.
                // remember from File method length() you get long, but this method required int
                // type in argument so you have to cast it:
                response.setContentLength((int)fileToDownload.length());
                
                FileInputStream in = new FileInputStream(fileToDownload);
                /* ServletResponse interface has method getOutputStream() 
                 Returns a ServletOutputStream suitable for writing binary
                 data in the response. The servlet container does not encode the binary data.Calling
                 flush() on the ServletOutputStream commits the response. Either this method or 
                 getWriter() may be called to write the body, not both. */
                ServletOutputStream servletOutputStream = response.getOutputStream();
                while ((n_byte = in.read()) != -1) {
                    servletOutputStream.write(n_byte);
                }
                break;
            }
            // if any other receive answer action happen this: 
            default: {
                ServletContext servletContext = this.getServletContext();
                // as well servletContext object has method to get from resource file as byte stream
                InputStream in = servletContext.getResourceAsStream("/img/" + file_name);
                byte[] bytes = new byte[1024];
                ServletOutputStream servletOutputStream = response.getOutputStream();
                while ((n_byte = in.read(bytes)) != -1) {
                    servletOutputStream.write(bytes, 0, n_byte);
                }
                servletOutputStream.flush();
                servletOutputStream.close();
            }
        }
	}
}
