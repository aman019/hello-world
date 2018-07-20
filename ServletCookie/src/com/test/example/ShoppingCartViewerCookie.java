package com.test.example;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShoppingCartViewerCookie extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
      IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

      String sessionid = null;
//    Cookie[] cookies = req.getCookies();
//    if (cookies != null) {
//      for (int i = 0; i < cookies.length; i++) {
//        if (cookies[i].getName().equals("sessionid")) {
//          sessionid = cookies[i].getValue();
//          break;
//        }
//      }
//    }

        
    Cookie[] cookies = req.getCookies();
    if(cookies !=null){
    for(Cookie cookie : cookies){
    	if(cookie.getName().equals("JSESSIONID")) sessionid = cookie.getValue();
    }
    }
    
    
    // If the session ID wasn't sent, generate one.
    // Then be sure to send it to the client with the response.
    if (sessionid == null) {
      sessionid = generateSessionId();
      Cookie c = new Cookie("sessionid", sessionid);
      res.addCookie(c);
    }

    out.println("<HEAD><TITLE>JSessionID Details</TITLE></HEAD>");
    out.println("<BODY>");
    out.println("Session id : " + sessionid);
    out.println("JSession id : " + retrieveSessionID());
    out.println("</BODY></HTML>");
  }

  private static String generateSessionId() throws UnsupportedEncodingException {
    String uid = new java.rmi.server.UID().toString(); // guaranteed unique
    return URLEncoder.encode(uid,"UTF-8"); // encode any special chars
  }

  private static String[] getItemsFromCart(String sessionid) {
    return new String[]{"a","b"};  
  }
  
  public String retrieveSessionID() {
      
      
      String key = "";
      String id = "";
        
      try {
        URL url = new URL ( "https://jiraict.cnhind.com/rest/API/latest/issues/QAF-1");
        URLConnection urlConnection = url.openConnection();
                    
        if (urlConnection != null)
        {
             for (int i = 1;(key = urlConnection.getHeaderFieldKey(i)) != null; i++)
             {
                  if (key.equalsIgnoreCase("set-cookie"))
                  {
                       id = urlConnection.getHeaderField(key);
                       id = id.substring(0, id.indexOf(";"));
                  }     //if
             }     //for
        }     //if
      } catch (IOException e) {
                                                             e.printStackTrace();
      }     //catch
      
        return id;
}     //getSessionID
  
}