package br.com.starcode.parccser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class ErrorSelectorParserTest {

    @Test
    public void nullValue() {
        
        boolean hasException = false;
        try {
            new Parser(null, null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void empty() {
        
        boolean hasException = false;
        try {
            new Parser("", null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void testNull() {
        
        boolean hasException = false;
        try {
            new Parser("*", null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void endUnexpected() {
        
        boolean hasException = false;
        try {
            new Parser("a > ", new MockListener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void contentLeft() {
        
        boolean hasException = false;
        try {
            new Parser("a z ^", new MockListener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
   /* @Test
    public void expectIdentifier() {
        
        boolean hasException = false;
        try {
            new Parser("[a=]", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }*/

    @Test
    public void expectIdentifier2() {
        
        boolean hasException = false;
        try {
            new Parser("[=]", new MockListener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectToken() {
        
        boolean hasException = false;
        try {
            new Parser("[a", new MockListener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectIdentifier3() {
        
        boolean hasException = false;
        try {
            new Parser("a#", new MockListener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectIdentifier4() {
        
        boolean hasException = false;
        try {
            new Parser("x.", new MockListener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void valueOnlyNumber() {
        
        boolean hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser("[val=1]", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void mixedQuotes() {
        
        boolean hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser("[val='\"]", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
}
