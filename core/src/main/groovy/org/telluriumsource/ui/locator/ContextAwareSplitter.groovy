package org.telluriumsource.ui.locator

/**
 *  Split the context not simply by white space.
 *
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *  Date: Apr 16, 2009
 *
 */
public class ContextAwareSplitter {
  public static final String SPACE = " "
  public static final char WHITE_SPACE = ' '
  public static final char LEFT_BRACKET = '['
  public static final char RIGHT_BRACKET = ']'
  public static final char LEFT_PARENTHESIS = '('
  public static final char RIGHT_PARENTHESIS = ')'

  private State state = State.START

  public String[] split(String input){
    if(input == null)
      return null

    Context context = start(input)
    List<String> list = context.getTokens()
    
    return list.toArray()
  }

  protected Context start(String input){
    state = State.START
    Context context = new Context(input)
    if(input.startsWith(SPACE))
      return wait(context)

    return rolling(context)
  }

  protected Context wait(Context context){
    state = State.WAIT
    char[] workcopy = context.getWorkCopy()
    int count = 0
    for(char achar: workcopy){
      if(WHITE_SPACE != achar){
        state = State.ROLLING
        context.moveForward(count)
        break
      }else{
        ++count
      }
    }

    if(state == State.WAIT){
      //should reach the end of the input string, return
      state = State.END
      context.moveForward(count)
      return context
    }
    
    return rolling(context)
  }

  protected Context rolling(Context context){
    state = State.ROLLING
    Token token = context.getToken()
    char[] chars = context.getWorkCopy()
    Stack<String> stack = new Stack<String>()
    int count = 0
    for(char achar: chars){
      if(LEFT_BRACKET == achar){
        token.append(achar)
        stack.push(achar)
        ++count
      }else if(RIGHT_BRACKET == achar){
        token.append(achar)
        stack.pop()
        ++count
      }else if(LEFT_PARENTHESIS == achar){
        token.append(achar)
        stack.push(achar)
        ++count
      }else if(RIGHT_PARENTHESIS == achar){
        token.append(achar)
        stack.pop()
        ++count
      }else if(WHITE_SPACE == achar){
        if(stack.size() == 0){
          //we are safe to split here
          context.finalizeToken(token)
          context.moveForward(count)
          state = State.WAIT
          break
        }else{
          token.append(achar)
          ++count
        }
      }else{
          token.append(achar)
          ++count                  
      }
    }

    if(state == State.WAIT){
      return wait(context)
    }else{
      //reach the end of the input string
      context.finalizeToken(token)
      context.moveForward(count)
      state = State.END

      return context
    }
  }

}