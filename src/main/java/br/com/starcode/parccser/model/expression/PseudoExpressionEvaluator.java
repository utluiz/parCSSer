package br.com.starcode.parccser.model.expression;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * docs: http://www.w3.org/TR/css3-selectors/#nth-child-pseudo
 * nth
 *  : S* [ ['-'|'+']? INTEGER? {N} [ S* ['-'|'+'] S* INTEGER ]? |
 *         ['-'|'+']? INTEGER | {O}{D}{D} | {E}{V}{E}{N} ] S*
 */
public class PseudoExpressionEvaluator {

	private Deque<Item> dq;
	private Item current;
	
	public static EvaluatedExpression evaluate(List<Item> items) {
		return new PseudoExpressionEvaluator(items).evaluate();
	}

	public PseudoExpressionEvaluator(List<Item> items) {
		dq = new ArrayDeque<Item>(items);
	}
	
	protected void next() {
		current = dq.poll();
	}
	
	protected boolean end() {
		return current == null;
	}
	
	protected EvaluatedExpression evaluate() {
		current = null;
		if (dq.isEmpty()) return null;
		next();
		
		//test for text value
		if (Type.IDENTIFIER.equals(current.getType())
				&& !"n".equals(current.getValue())) {
				return null;
		}
		
		//test
		Signal s1 = null;
		if (Type.SIGNAL.equals(current.getType())) {
			if ("+".equals(current.getValue())) {
				s1 = Signal.PLUS;
			} else {
				s1 = Signal.MINUS;
			}
			next();
			if (end()) return null;
		}
		boolean signalConsumed = false;
		
		//check for number
		Integer int1 = 1;
		if (Type.NUMBER.equals(current.getType())) {
			try {
				int1 = Integer.parseInt(current.getValue());
			} catch (NumberFormatException e) {
				//TODO warn error
				return null;
			}
			signalConsumed = true;
			next();
		}
		
		//minus sign -> negative value
		if (Signal.MINUS.equals(s1)) {
			int1 = -int1;
		}
		
		if (end()) {
			//has only a number
			return new EvaluatedExpression(null, int1, null);
		}
		
		//check for 'n'
		Integer a = null;
		if (Type.IDENTIFIER.equals(current.getType()) && "n".equals(current.getValue())) {
			a = int1;
			signalConsumed = true;
			next();
		}
		
		//until here should have consimed signal
		if (s1 != null && !signalConsumed) {
			return null;
		}
		
		if (end()) {
			//has only a number
			return new EvaluatedExpression(a, null, null);
		}
		
		//check for other sign
		Signal s2 = null;
		if (Type.SIGNAL.equals(current.getType())) {
			if ("+".equals(current.getValue())) {
				s2 = Signal.PLUS;
			} else {
				s2 = Signal.MINUS;
			}
			next();
			if (end()) return null;
		}
		
		//check for number
		Integer b = null;
		if (Type.NUMBER.equals(current.getType())) {
			try {
				b = Integer.parseInt(current.getValue());
			} catch (NumberFormatException e) {
				//TODO warn error
				return null;
			}
			//minus sign -> negative value
			if (Signal.MINUS.equals(s2)) {
				b = -b;
			}
			next();
		}
		
		if (end()) {
			return new EvaluatedExpression(a, b, null);
		}
		
		return null;
	}
	
}
