package br.com.starcode.parccser;

import java.util.ArrayList;
import java.util.List;

import br.com.starcode.parccser.model.AttributeOperator;
import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.Context;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.NegationSelector;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.PseudoType;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.StringValue;
import br.com.starcode.parccser.model.TypeSelector;
import br.com.starcode.parccser.model.expression.Item;
import br.com.starcode.parccser.model.expression.PseudoExpression;
import br.com.starcode.parccser.model.expression.PseudoExpressionEvaluator;
import br.com.starcode.parccser.model.expression.Type;

/**
 * That's the main class for parsing CSS selectors.
 * 
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 */
public class Parser {

    protected String content;
    protected int pos;
    protected int len;
    protected Character current;
    protected ParserListener parserListener;
    
    public static List<Selector> parse(String selector, ParserListener parserListener) throws ParserException {
    	return new Parser(selector, parserListener).interpret();
    }
    
    public static List<Selector> parse(String selector) throws ParserException {
    	return new Parser(selector, new EmptyParserListener()).interpret();
    }
    
    protected Parser(String selector, ParserListener parserListener) {
        if (selector == null || selector.trim().isEmpty()) {
            throw new IllegalArgumentException("Selector cannot be null or empty!");
        }
        if (parserListener == null) {
            throw new IllegalArgumentException("ParserListener cannot be null!");
        }
        this.parserListener = parserListener;
        this.content = selector;
        this.pos = -1;
        this.len = selector.length();
    }
    
    /**
     * Starts parsing the selector
     * @throws ParserException
     */
    public List<Selector> interpret() throws ParserException {
        next();
        return groups();
    } 
    
    protected Character next() {
        if (pos < len) {
            pos++;
        }
        if (pos < len) {
            return current = content.charAt(pos);
        }
        return current = null;
    }
    
    protected boolean end() {
        return pos >= len;
    }
    
    /**
     * selectors_group
     *  : selector [ COMMA S* selector ]*
     */
    protected List<Selector> groups() throws ParserException {

        List<Selector> groups = new ArrayList<Selector>();
        int groupNumber = 0;
        while (!end()) {
            ignoreWhitespaces();
            parserListener.beginGroup(groupNumber, pos);
            Selector selector = selector();
            groups.add(selector);
            parserListener.endGroup(selector);
            groupNumber++;
            ignoreWhitespaces();
            if (!end() && current != ',') {
                throw new ParserException("There is something left in the selector at position " + pos);
            }
            next();
        }
        return groups;
        
    }
    
    /**
     * selector
     *  : simple_selector_sequence [ combinator simple_selector_sequence ]*
     *  ;
     *  
     *  combinator
     *  : PLUS S* | GREATER S* | TILDE S* | S+
     */
    protected Selector selector() throws ParserException {
        int initialPosition = pos;
        StringBuilder sb = new StringBuilder();
        List<SimpleSelectorSequence> simpleSelectors = new ArrayList<SimpleSelectorSequence>();
        List<Combinator> combinators = new ArrayList<Combinator>();
        int lastChar = pos;
        while (!end()) {
        	//finds combinator, but not in the first iteration
        	Combinator combinator = null;
        	if (!simpleSelectors.isEmpty()) {
        		//stores if it has spaces until the next token
                boolean hasWhitespace = false;
                if (!end() && Character.isWhitespace(current)) {
                    hasWhitespace = true;
                    ignoreWhitespaces();
                }
                if (!end()) {
                    //implements "combinator" rule
                    if (current == '+') combinator = Combinator.ADJASCENT_SIBLING;
                    else if (current == '>') combinator = Combinator.CHILD;
                    else if (current == '~') combinator = Combinator.GENERAL_SIBLING;
                    //if hasn't any but spaces
                    else if (hasWhitespace) combinator = Combinator.DESCENDANT;
                    //is it the end?
                    if (combinator == null || current == ',') {
                        break;
                    }
                    //don't advance because spaces were just advanced
                    if (combinator != Combinator.DESCENDANT) {
                    	sb.append(current);
                        next();
                    } else {
                    	sb.append(' ');
                    }
                    ignoreWhitespaces();
                    if (end()) {
                        throw new ParserException("Unexpected end of selector at position " + pos);
                    }
                }
        		combinators.add(combinator);
        	}
        	//get next sequence
            SimpleSelectorSequence simpleSelectorSequence = simpleSelectorSequence();
            sb.append(simpleSelectorSequence);
            //sends combinator here (the first case it's null)
            simpleSelectors.add(simpleSelectorSequence);
            parserListener.selectorSequence(simpleSelectorSequence, combinator);
            lastChar = pos;
        }
        return new Selector(simpleSelectors, combinators, new Context(content, sb.toString(), initialPosition, lastChar));
    }
    
    /**
     * simple_selector_sequence
     *  : [ type_selector | universal ]
     *    [ HASH | class | attrib | pseudo | negation ]*
     *  | [ HASH | class | attrib | pseudo | negation ]+
     *  
     *  type_selector
     *  : [ namespace_prefix ]? element_name
     *
     * namespace_prefix ** not implemented
     *  : [ IDENT | '*' ]? '|'
     *
     * element_name
     *  : IDENT
     *
     * universal
     *  : [ namespace_prefix ]? '*'
     *
     * class
     *  : '.' IDENT
     */
    protected SimpleSelectorSequence simpleSelectorSequence() throws ParserException {
        
        List<SimpleSelector> simpleSelectorList = new ArrayList<SimpleSelector>();
        StringBuilder sb = new StringBuilder();
        boolean hasSimpleSelector = false;
        int initialPos = pos;
        if (current == '*') {
            //universal selector
            sb.append(current);
            hasSimpleSelector = true;
            TypeSelector ts = new TypeSelector("*", new Context(content, "*", initialPos, pos + 1));
            parserListener.typeSelector(ts);
            simpleSelectorList.add(ts);
            next();
        } else if (Character.isLetter(current)) {
            //type selector
            String type = identifier();
            sb.append(type);
            hasSimpleSelector = true;
            TypeSelector ts = new TypeSelector(type, new Context(content, type, initialPos, pos));
            parserListener.typeSelector(ts);
            simpleSelectorList.add(ts);
        }
        
        int selectorCount = 0;
        boolean hasPseudoElement = false;
        while (!end()) {
        	int initialLoopPos = pos;
            if (current == '.') {
                //class selector
                sb.append(current);
                next();
                String className = !end() ? identifier() : null;
                if (className == null || className.isEmpty()) {
                    throw new ParserException("Expected className at position " + pos);
                }
                sb.append(className);
                ClassSelector cs = new ClassSelector(className, new Context(content, "." + className, initialLoopPos, pos));
                simpleSelectorList.add(cs);
                parserListener.classSelector(cs);
            } else if (current == '#') {
                //hash selector
                sb.append(current);
                next();
                String id = !end() ? identifier() : null;
                if (id == null || id.isEmpty()) {
                    throw new ParserException("Expected id at position " + pos);
                }
                HashSelector hs = new HashSelector(id, new Context(content, "#" + id, initialLoopPos, pos));
                simpleSelectorList.add(hs);
                parserListener.idSelector(hs);
                sb.append(id);
            } else if (current == '[') {
                //attribute selectors
                sb.append(current);
                next();
                AttributeSelector as = attribute();
                simpleSelectorList.add(as);
                parserListener.attributeSelector(as);
                sb.append(as);
                sb.append(']');
            } else if (current == ':') {
                //pseudo-element, pseudo-class or negation
                sb.append(current);
                next();
                if (end()) {
                    throw new ParserException("Expected pseudo-element or pseudo-class at " + pos);
                }
                boolean doubleColon = false;
                if (current == ':') {
                    doubleColon = true;
                    sb.append(current);
                    next();
                }
                String ident = !end() ? identifier() : null;
                if (ident == null || ident.isEmpty()) {
                    throw new ParserException("Expected identifier at position " + pos);
                }
                boolean special = isPseudoSpecialCase(ident);
                if (special || doubleColon) {
                    //pseudo-element (double colon or special cases)
                    //allow just one
                    if (hasPseudoElement) {
                        throw new ParserException("Only one pseudo-element is allowed for each simple selector and a second one was found at position " + pos);
                    }
                    PseudoSelector ps = pseudo(ident, PseudoType.PSEUDO_ELEMENT, doubleColon);
                    simpleSelectorList.add(ps);
                    parserListener.pseudoSelector(ps);
                    sb.append(ps);
                    hasPseudoElement = true;
                } else if ("not".equalsIgnoreCase(ident)) {
                    //negation
                    NegationSelector n = negation(selectorCount);
                    simpleSelectorList.add(n);
                    sb.append(ident);
                    sb.append('(');
                    sb.append(n);
                    sb.append(')');
                } else {
                    //pseudo-class
                    PseudoSelector ps = pseudo(ident, PseudoType.PSEUDO_CLASS, false);
                    simpleSelectorList.add(ps);
                    parserListener.pseudoSelector(ps);
                    sb.append(ps);
                }
            } else {
                break;
            }
            selectorCount++;
        }
        if (!hasSimpleSelector && selectorCount == 0) {
            throw new ParserException("No real selector found at position " + pos);
        }
            
        return new SimpleSelectorSequence(simpleSelectorList, new Context(content, sb.toString(), initialPos, pos));
        
    }
    
    /**
     * attrib
     *  : '[' S* [ namespace_prefix ]? IDENT S*
     *        [ [ PREFIXMATCH |
     *            SUFFIXMATCH |
     *            SUBSTRINGMATCH |
     *            '=' |
     *            INCLUDES |
     *            DASHMATCH ] S* [ IDENT | STRING ] S*
     *        ]? ']'
     */
    protected AttributeSelector attribute() throws ParserException {
        
    	int initialPos = pos;
        StringBuilder sb = new StringBuilder();
        ignoreWhitespaces();
        String name = !end() ? identifier() : null;
        if (name == null || name.isEmpty()) {
            throw new ParserException("Expected attribute name at position " + pos);
        }
        sb.append(name);
        ignoreWhitespaces();
        if (end()) {
            throw new ParserException("Unexpected end of selector selector at position " + pos);
        }
        AttributeOperator operator = null;
        if (current == '=') {
            operator = AttributeOperator.EQUALS;
            next();
        } else if (current != ']') {
            if (current == '~') operator = AttributeOperator.INCLUDES; 
            else if (current == '|') operator = AttributeOperator.DASH_MATCH; 
            else if (current == '^') operator = AttributeOperator.PREFIX_MATCH; 
            else if (current == '$') operator = AttributeOperator.SUFFIX_MATCH; 
            else if (current == '*') operator = AttributeOperator.SUBSTRING_MATCH;
            else throw new ParserException("Invalid operator ('" + current + "') at position " + pos);
            next();
            if (end() || current != '=') {
                throw new ParserException("Expected '=' sign at position " + pos);
            }
            next();
        }
        
        ignoreWhitespaces();
        if (end()) {
            throw new ParserException("Unexpected end of attribute selector at position " + pos);
        }
        
        //value
        StringValue value = null;
        if (operator != null) {
            sb.append(operator.getSign());
            if (current == '\'' || current == '"') {
                char quote = current;
                value = string();
                sb.append(quote);
                sb.append(value.getContext().toString());
                sb.append(quote);
            } else {
            	int identPos = pos;
                String ident = identifier();
                value = new StringValue(new Context(content, ident, identPos, pos), ident);
                sb.append(value);
            }
        }
        
        ignoreWhitespaces();
        
        //end of attribute
        if (end() || current != ']') {
            throw new ParserException("Token ']' expected at position " + pos);
        }
        int endPos = pos;
        next();
        
        return new AttributeSelector(name, operator, value, new Context(content, sb.toString(), initialPos, endPos));
        
    }

    /**
     * string    {string1}|{string2}
     * string1   \"([^\n\r\f\\"]|\\{nl}|{nonascii}|{escape})*\"
     * string2   \'([^\n\r\f\\']|\\{nl}|{nonascii}|{escape})*\'
     * nonascii  [^\0-\177]
     * escape    {unicode}|\\[^\n\r\f0-9a-f]
     * unicode   \\[0-9a-f]{1,6}(\r\n|[ \n\r\t\f])?
     * nl        \n|\r\n|\r|\f
     */
    protected StringValue string() throws ParserException {
        Character openQuote = current;
        int initialPosition = pos;
        boolean escape = false;
        next();
        StringBuilder value = new StringBuilder(); 
        StringBuilder string = new StringBuilder();
        while (!end()) {
            if (escape) {
                //TODO implement UNICODE
                if (current == openQuote || current == '\\') {
                    value.append(current);
                    string.append(current);
                    escape = false;
                } else {
                    throw new ParserException("Invalid escape character at position " + pos);
                }
            } else {
            	if (current == openQuote) break;
                string.append(current);
                if (current == '\\') {
                    escape = true;
                } else {
                    value.append(current);
                }
            }
            next();
        }
        
        if (current != openQuote) {
            throw new ParserException("String not closed!");
        }
        next();
        return new StringValue(new Context(content, string.toString(), initialPosition, pos), value.toString());
        
    }
    
    /**
     * num       [0-9]+|[0-9]*\.[0-9]+ 
     */
    protected String number() throws ParserException {
        StringBuilder sb = new StringBuilder();
        sb.append(current);
        next();
        boolean hasDot = false;
        while (!end() && (Character.isDigit(current) || current == '.')) {
            if (current == '.') {
                if (hasDot) {
                    throw new ParserException("Unexpected '.' at position " + pos);
                }
                hasDot = true;
            }
            sb.append(current);
            next();
        }
        return sb.toString();
    }

    /**
     * negation
     *  : NOT S* negation_arg S* ')'
     *
     * negation_arg
     *  : type_selector | universal | HASH | class | attrib | pseudo
     *  
     *  TODO include "not" or not? It would be necessary to use parameters
     */
    protected NegationSelector negation(int number) throws ParserException {
        
        StringBuilder sb = new StringBuilder();
        ignoreWhitespaces();
        if (end() || current != '(') {
            throw new ParserException("Expected '(' at position " + pos);
        }
        //sb.append(current);
        int parenthesisPos = pos;
        next();
        ignoreWhitespaces();
        if (end()) {
            throw new ParserException("Selector expected at position " + pos);
        }
        int initialPos = pos;
        SimpleSelector simpleSelector = null;
        if (current == '*') {
            //universal selector
            sb.append(current);
            TypeSelector ts = new TypeSelector("*", new Context(content, "*", initialPos, pos + 1));
            parserListener.negationTypeSelector(ts);
            simpleSelector = ts;
            next();
        } else if (Character.isLetter(current)) {
            //type selector
            String type = identifier();
            sb.append(type);
            TypeSelector ts = new TypeSelector(type, new Context(content, type, initialPos, pos));
            parserListener.negationTypeSelector(ts);
            simpleSelector = ts;
        } else if (current == '.') {
            //class selector
            sb.append(current);
            next();
            String className = !end() ? identifier() : null;
            if (className == null || className.isEmpty()) {
                throw new ParserException("Expected className at position " + pos);
            }
            ClassSelector cs = new ClassSelector(className, new Context(content, "." + className, initialPos, pos));
            simpleSelector = cs;
            parserListener.negationClassSelector(cs);
            sb.append(className);
        } else if (current == '#') {
            //hash selector
            sb.append(current);
            next();
            String id = !end() ? identifier() : null;
            if (id == null || id.isEmpty()) {
                throw new ParserException("Expected id at position " + pos);
            }
            HashSelector hs = new HashSelector(id, new Context(content, "#" + id, initialPos, pos));
            simpleSelector = hs;
            parserListener.negationIdSelector(hs);
            sb.append(id);
        } else if (current == '[') {
            //attribute selectors
            sb.append(current);
            next();
            AttributeSelector as = attribute();
            simpleSelector = as;
            parserListener.negationAttributeSelector(as);
            sb.append(as.getContext());
            sb.append(']');
        } else if (current == ':') {
            //pseudo-class only
            sb.append(current);
            next();
            if (end()) {
                throw new ParserException("Expected pseudo-element or pseudo-class at " + pos);
            }
            boolean doubleColon = false;
            if (current == ':') {
                doubleColon = true;
                sb.append(current);
                next();
            }
            String ident = !end() ? identifier() : null;
            if (ident == null || ident.isEmpty()) {
                throw new ParserException("Expected identifier at position " + pos);
            }
            if ("not".equalsIgnoreCase(ident) || isPseudoSpecialCase(ident) || doubleColon) {
                throw new ParserException("Expected pseudo-class (starting with ':', except ':not', ':first-line', ':first-letter', ':before' and ':after') at position " + pos);
            }
            PseudoSelector ps = pseudo(ident, PseudoType.PSEUDO_CLASS, doubleColon);
            simpleSelector = ps;
            parserListener.negationPseudoSelector(ps);
            sb.append(ps.getContext());
        } else {
            throw new ParserException("Selector expected at position " + pos);
        }
        
        ignoreWhitespaces();
        if (end() || current != ')') {
            throw new ParserException("Expected ')' at position " + pos);
        }
        //sb.append(current);
        next();
            
        return new NegationSelector(simpleSelector, new Context(content, sb.toString(), parenthesisPos, pos));
        
    }

    /**
     * pseudo
     *  // '::' starts a pseudo-element, ':' a pseudo-class 
     *  // Exceptions: :first-line, :first-letter, :before and :after. 
     *  // Note that pseudo-elements are restricted to one per selector and 
     *  // occur only in the last simple_selector_sequence. 
     *  : ':' ':'? [ IDENT | functional_pseudo ]
     *  ;
     *  
     * functional_pseudo
     *  : FUNCTION S* expression ')'
     */
    protected PseudoSelector pseudo(String ident, PseudoType type, boolean doubleColon) throws ParserException {
        
    	int initialPos = pos;
        StringBuilder sb = new StringBuilder();
        sb.append(ident);
        PseudoExpression expression = null;
        if (!end() && current == '(') {
            sb.append(current);
            next();
            ignoreWhitespaces();
            if (end()) {
                throw new ParserException("Expected expression at position " + pos);
            }
            
            //expression
            expression = expression();
            sb.append(expression.getContext());
            
            //close parenthesis
            ignoreWhitespaces();
            if (end() || current != ')') {
                throw new ParserException("Expected ')' at position " + pos);
            }
            sb.append(current);
            next();
        }
        return new PseudoSelector(ident, type, doubleColon, expression, new Context(content, sb.toString(), initialPos, pos));
        
    }
    
    /**
     * expression
     *  // In CSS3, the expressions are identifiers, strings, 
     *  // or of the form "an+b" 
     *  : [ [ PLUS | '-' | DIMENSION | NUMBER | STRING | IDENT ] S* ]+
     *  
     *  {num}{ident}     return DIMENSION;
     *  {num}            return NUMBER;
     */
    protected PseudoExpression expression() throws ParserException {
        
        List<Item> items = new ArrayList<Item>();
        StringBuilder sb = new StringBuilder();
        int initialPos = pos;
        
        if (current == '\'' || current == '"') {
        	//string
        	Character quote = current;
            StringValue s = string();
            sb.append(quote);
            sb.append(s.getContext().toString());
            sb.append(quote);
            items.add(new Item(Type.STRING, s.getActualValue()));
            
            return new PseudoExpression(items, null, s.getActualValue(), new Context(content, sb.toString(), initialPos, pos));
        } else {
        	
        	while (!end()) {
	            if (current == '+') {
	                items.add(new Item(Type.SIGNAL, "+"));
	                sb.append(current);
	                next();
	            } else if (current == '-') {
	                items.add(new Item(Type.SIGNAL, "-"));
	                sb.append(current);
	                next();
	            } else if (Character.isLetter(current)) {
	                //identifier
	                String ident = simpleIdentifier();
	                sb.append(ident);
	                items.add(new Item(Type.IDENTIFIER, ident));
	            } else if (Character.isDigit(current)) {
	                //number or dimension
	                String number = number();
	                sb.append(number);
	                items.add(new Item(Type.NUMBER, number));
	            } else {
	                break;
	            }
	            ignoreWhitespaces();
	        }
        	
        }
        return new PseudoExpression(items, PseudoExpressionEvaluator.evaluate(items), sb.toString(), new Context(content, sb.toString(), initialPos, pos));
        
    }
    
    /**
     * ident     [-]?{nmstart}{nmchar}*
     * name      {nmchar}+
     * nmstart   [_a-z]|{nonascii}|{escape}
     * nonascii  [^\0-\177]
     * unicode   \\[0-9a-f]{1,6}(\r\n|[ \n\r\t\f])?
     * escape    {unicode}|\\[^\n\r\f0-9a-f]
     * nmchar    [_a-z0-9-]|{nonascii}|{escape}
     * num       [0-9]+|[0-9]*\.[0-9]+
     */
    protected String identifier() throws ParserException {
        //TODO implement unicode, escape, [-], nonascii
        StringBuilder sb = new StringBuilder();
        
        //validates first character
        if (!end() && !Character.isLetter(current) && current != '_') {
            return "";
        }
        while (!end() && (Character.isLetterOrDigit(current) || current == '-' || current == '_')) {
            sb.append(current);
            next();
        }
        return sb.toString();
    }
    
    protected String simpleIdentifier() throws ParserException {
        StringBuilder sb = new StringBuilder();
        while (!end() && (Character.isLetter(current))) {
            sb.append(current);
            next();
        }
        return sb.toString();
    }
    
    /**
     * pseudo (Special cases)
     *  // '::' starts a pseudo-element, ':' a pseudo-class 
     *  // Exceptions: :first-line, :first-letter, :before and :after. 
     */
    protected boolean isPseudoSpecialCase(String identifier) {
        return "first-line".equalsIgnoreCase(identifier) ||
                "first-letter".equalsIgnoreCase(identifier) ||
                "before".equalsIgnoreCase(identifier) ||
                "after".equalsIgnoreCase(identifier);
    }

    protected void ignoreWhitespaces() throws ParserException {
        if (!end() && Character.isWhitespace(current)) {
            while (!end() && Character.isWhitespace(current)) {
                next();
            }
        }
    }
    
}
