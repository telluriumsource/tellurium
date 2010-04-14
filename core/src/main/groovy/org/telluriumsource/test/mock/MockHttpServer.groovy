package org.telluriumsource.test.mock

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class MockHttpServer {
  //default port
  private int port = 8080;

  private HttpServer server = null;
  private MockHttpHandler handler;

  private String htmlPath = "org/telluriumsource/html";

  public MockHttpServer() {
    this.handler = new MockHttpHandler();
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port) {
    this.handler = new MockHttpHandler();
    this.port = port;
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port, HttpHandler handler) {
    this.port = port;
    this.handler = handler;
    this.server = HttpServer.create();
  }

  public void setHtmlClassPath(String path){
    this.htmlPath = path;
  }

  public void setContentType(String contentType){
    this.handler.setContentType(contentType)
  }
  
  public void registerHtmlBody(String url, String body){
    this.server.createContext(url, this.handler);
    this.handler.registerBody(url, body);
  }

  public void registerHtml(String url, String html){
    this.server.createContext(url, this.handler);
    this.handler.registerHtml(url, html);
  }

  public void setServerPort(int port){
    this.port = port;
  }

  public void start() {
    server.bind(new InetSocketAddress(this.port), 0);
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  public void stop(){
    if(server != null)
      server.stop(1);
  }

  public String getHtmlFile(String file){
    return new File(ClassLoader.getSystemResource("${this.htmlPath}/${file}.html").getFile()).text;
  }
}