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
		
	}
}
