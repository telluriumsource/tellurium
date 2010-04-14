package org.telluriumsource.tester;

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.Headers

/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 16, 2009
 *
 */

public class QUnitHttpHandler implements HttpHandler {

  /*
 <script src="http://code.jquery.com/jquery-1.4.js"> </script>
 <link rel="stylesheet" href="http://github.com/jquery/qunit/raw/master/qunit/qunit.css" type="text/css" media="screen" />
 <script type="text/javascript" src="http://github.com/jquery/qunit/raw/master/qunit/qunit.js"></script>

   */
  private static String HEADER10 = """
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
          <title>QUnit Tester</title>
          <link rel="stylesheet" href="qunit/qunit.css" type="text/css"/>
          <script src="qunit/jquery-1.4.js"> </script>
          <script type="text/javascript" src="qunit/qunit.js"></script>
          <script type="text/javascript">
            \$(document).ready(function(){
"""

  private static String HEADER1 = """
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
          <title>QUnit Tester</title>
          <script src="http://code.jquery.com/jquery-1.4.js"> </script>
          <link rel="stylesheet" href="http://github.com/jquery/qunit/raw/master/qunit/qunit.css" type="text/css" media="screen" />
          <script type="text/javascript" src="http://github.com/jquery/qunit/raw/master/qunit/qunit.js"></script>
          <script type="text/javascript">
            \$(document).ready(function(){
"""
  private static String HEADER2 = """
            });          
          </script>
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

  public void registerTest(String url, String javascript, String body){
    if(body != null)
      this.contents.put(url, HEADER1 + javascript + HEADER2 + body + TRAILER);
    else
      this.contents.put(url, HEADER1 + javascript + HEADER2 + TRAILER);
  }

  public void registerHtml(String url, String html){
      this.contents.put(url, html);
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
        html = HEADER1 + HEADER2 + ERROR_MESSAGE + TRAILER;
      }

      responseBody.write(html.getBytes());

      responseBody.close();
    }
  }
}