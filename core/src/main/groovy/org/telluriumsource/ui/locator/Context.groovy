package org.telluriumsource.ui.locator

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 16, 2009
 * 
 */
public class Context {

  String input

  List<String> tokens = new ArrayList<String>()

  int currentPos

  public Context(String input){
    this.input = input
    currentPos = 0
  }
  
  public Token getToken(){

    return new Token()
  }

  public List<String> finalizeToken(Token token){
    tokens.add(token.toString())

    return tokens
  }

  public int getMaxLength(){
    if(input == null)
      return 0
    
    return input.length()
  }

  public char[] getWorkCopy(){
    char[] chars = input.substring(currentPos).toCharArray()
    return chars
  }

  public int moveForward(int step){
    currentPos += step

    return currentPos
  }

  public int getCurrentPosition(){
    return currentPos
  }

  public String getInput(){
    return input
  }

  public List<String> getTokens(){
    return this.tokens
  }
}