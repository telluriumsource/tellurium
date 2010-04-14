package org.telluriumsource.tester;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler


/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 16, 2009
 * 
 */
public class QUnitHttpServer {
  //default port
  private int port = 8080;

  private HttpServer server = null;
  private QUnitHttpHandler handler;

  public QUnitHttpServer() {
    this.handler = new QUnitHttpHandler();
    this.server = HttpServer.create();
  }

  public QUnitHttpServer(int port) {
    this.handler = new QUnitHttpHandler();
    this.port = port;
    this.server = HttpServer.create();
  }

  public QUnitHttpServer(int port, HttpHandler handler) {
    this.port = port;
    this.handler = handler;
    this.server = HttpServer.create();
  }

  public void setContentType(String contentType){
    this.handler.setContentType(contentType)
  }

  public void registerHtmlBody(String url, String javascript, String body){
    this.server.createContext(url, this.handler);
    this.handler.registerTest(url, javascript, body);
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
      server.stop(50);
  }

  public String getJsFile(String file){
    return new File(ClassLoader.getSystemResource("org/telluriumsource/js/${file}.js").getFile()).text;
  }

  public String getHtmlFile(String file){
    return new File(ClassLoader.getSystemResource("org/telluriumsource/html/${file}.html").getFile()).text;
  }
}
