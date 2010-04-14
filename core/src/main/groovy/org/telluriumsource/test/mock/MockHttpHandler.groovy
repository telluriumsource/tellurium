package org.telluriumsource.test.mock

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class MockHttpHandler implements HttpHandler {
  
  private static String HEADER = """
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
          <title>Mock HTTP Server</title>
        </head>
        <body>
  """

  private static String TRAILER = """
    </body>
    </html>
  """

  private static String ERROR_MESSAGE = """
    <div>Cannot find HTML content</div>
  """
  private static String CONTENT_TYPE = "Content-Type";

  private Map<String, String> contents = new HashMap<String, String>();

  private String contentType = "text/html";

  public void setContentType(String contentType){
    this.contentType = contentType;
  }

  public void registerBody(String url, String body){
    if(body != null)
      this.contents.put(url, HEADER + body + TRAILER);
    else
      this.contents.put(url, HEADER + TRAILER);
  }

  public void registerHtml(String url, String html){
      this.contents.put(url, html);
  }

  public void setHeader(String header) {
    this.HEADER = header;
  }

  public void setTrailer(String trailer){
    this.TRAILER = trailer;
  }
  
  public void setBody(String body){
    this.body = body;
  }

  public void handle(HttpExchange exchange) {
    String requestMethod = exchange.getRequestMethod();

    if (requestMethod.equalsIgnoreCase("GET") || requestMethod.equalsIgnoreCase("POST")) {
      Headers responseHeaders = exchange.getResponseHeaders();
      responseHeaders.set(CONTENT_TYPE, this.contentType);
      exchange.sendResponseHeaders(200, 0);

      OutputStream responseBody = exchange.getResponseBody();

/*
      Headers requestHeaders = exchange.getRequestHeaders();
      Set<String> keySet = requestHeaders.keySet();
      Iterator<String> iter = keySet.iterator();
      while (iter.hasNext()) {
        String key = iter.next();
        List values = requestHeaders.get(key);
        String s = key + " = " + values.toString() + "\n";
        responseBody.write(s.getBytes());
      }*/
      String uri = exchange.getRequestURI();
      String html = this.contents.get(uri);
      if(html == null){
        html = HEADER + ERROR_MESSAGE + TRAILER;
      }

      responseBody.write(html.getBytes());

      responseBody.close();
    }
  }
}