package univ.lecture.riotapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.logging.*;

/**
 * Calculator application
 */
public class CalcApp {
	private Stack<String> tokenStack = new Stack<>();
	private List<String> tokenArrayString = new ArrayList<>();
	
	private boolean isOperator(String str){
		boolean result = false;
		if("+".equals(str) || "/".equals(str) || "-".equals(str) || "x".equals(str)){
			result = true;
		}
		
		return result;
	}
	
	private boolean isDouble(String s) {
		boolean result = false;

		try {
			double temp = Double.parseDouble(s);
			if(Double.isNaN(temp)){
				return false;
			}
			result = true;
		} catch (NumberFormatException e) {
    		Logger logger = Logger.getLogger("Exception");
    		String except= e.toString();
    		logger.log(Level.INFO,except);
			result = false;
		}
		return result;
	}

    public double calc(String[] tokens) {
        
        for(int i = 0; i < tokens.length; i++){
        	if("(".equals(tokens[i])){
        		tokenStack.push("(");
        	} else if(")".equals(tokens[i])){
        		while(!"(".equals(tokenStack.peek())){
        			tokenArrayString.add(tokenStack.pop());
        		}
        		tokenStack.pop();
        	} else if(isOperator(tokens[i])){
        		tokenStack.push(tokens[i]);
        	} else if(isDouble(tokens[i])){
        		tokenArrayString.add(tokens[i]);
        	} else {
        		Logger logger = Logger.getLogger("Error");
        		String result="Invaild Operator";
        		logger.log(Level.INFO,result);
        		break;
        	}
        }
        
        while(!tokenStack.isEmpty()){
        	tokenArrayString.add(tokenStack.pop());
        }
        

        double firstOperand;
        double secondOperand;
        
        for(int i = 0; i < tokenArrayString.size(); i++){
        	String token = tokenArrayString.get(i);
        	if(isOperator(token)){
        		firstOperand = Double.parseDouble(tokenStack.pop());
        		secondOperand = Double.parseDouble(tokenStack.pop());
        		Operator operator = Operator.findOperator(token);
        		tokenStack.push(Double.toString(operator.evaluate(firstOperand, secondOperand)));
        	} else {
        		tokenStack.push(token);
        	}
        }
        
        return Double.parseDouble(tokenStack.pop());
    }
    /**
    public static void main( String[] args ) {
        final CalcApp app = new CalcApp();
        final StringBuilder outputs = new StringBuilder();
        Arrays.asList(args).forEach(value -> outputs.append(value + " "));
	
		Logger logger = Logger.getLogger("StringPrint");
		
		String result="Addition of value : "+outputs+" = "+Double.toString(app.calc(args));
		
		logger.log(Level.INFO,result);
    }
    */
}
