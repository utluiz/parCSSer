package br.com.starcode.parccser;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.starcode.parccser.model.AttributeOperator;
import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class SimpleSelectorParserTest {
	
	MockListener listener;
	
	@Before
	public void setup() {
		listener = new MockListener();
	}

    @Test
    public void universal() throws Exception {
        
        List<Selector> selectors = Parser.parse("*", listener);
        Assert.assertEquals(4, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("typeSelector=*", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|*", listener.getLista().get(2));
        Assert.assertEquals("endGroup=0", listener.getLista().get(3));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("*", sel1.getContext().toString());
        Assert.assertEquals("*", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(1, sel1.getContext().getEndPosition());
        
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("*", sss.getContext().toString());
        Assert.assertEquals("*", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(1, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("*", ts.getContext().toString());
        Assert.assertEquals("*", ts.getType());
        Assert.assertTrue(ts.isUniversal());
        Assert.assertEquals("*", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(1, ts.getContext().getEndPosition());
        
    }
    
    @Test
    public void element() throws Exception {
        
    	List<Selector> selectors = Parser.parse("table", listener);
        Assert.assertEquals(4, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("typeSelector=table", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|table", listener.getLista().get(2));
        Assert.assertEquals("endGroup=0", listener.getLista().get(3));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("table", sel1.getContext().toString());
        Assert.assertEquals("table", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(5, sel1.getContext().getEndPosition());
        
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("table", sss.getContext().toString());
        Assert.assertEquals("table", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(5, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("table", ts.getContext().toString());
        Assert.assertEquals("table", ts.getType());
        Assert.assertTrue(!ts.isUniversal());
        Assert.assertEquals("table", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(5, ts.getContext().getEndPosition());
        
    }
    
    @Test
    public void classSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse(".class", listener);
        Assert.assertEquals(4, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("classSelector=class", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|.class", listener.getLista().get(2));
        Assert.assertEquals("endGroup=0", listener.getLista().get(3));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals(".class", sel1.getContext().toString());
        Assert.assertEquals(".class", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(6, sel1.getContext().getEndPosition());
        
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals(".class", sss.getContext().toString());
        Assert.assertEquals(".class", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(6, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof ClassSelector);
        
        ClassSelector ts = (ClassSelector) ss;
        Assert.assertEquals(".class", ts.getContext().toString());
        Assert.assertEquals("class", ts.getClassName());
        Assert.assertEquals(".class", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(6, ts.getContext().getEndPosition());
        
    }
    
    @Test
    public void idSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("#identifier", listener);
        Assert.assertEquals(4, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("idSelector=identifier", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|#identifier", listener.getLista().get(2));
        Assert.assertEquals("endGroup=0", listener.getLista().get(3));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("#identifier", sel1.getContext().toString());
        Assert.assertEquals("#identifier", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(11, sel1.getContext().getEndPosition());
        
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("#identifier", sss.getContext().toString());
        Assert.assertEquals("#identifier", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(11, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof HashSelector);
        
        HashSelector ts = (HashSelector) ss;
        Assert.assertEquals("#identifier", ts.getContext().toString());
        Assert.assertEquals("identifier", ts.getName());
        Assert.assertEquals("#identifier", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(11, ts.getContext().getEndPosition());
        
    }
    
    @Test
    public void attributeSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("[name='test']", listener);
        Assert.assertEquals(4, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("attributeSelector=name='test'", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|[name='test']", listener.getLista().get(2));
        Assert.assertEquals("endGroup=0", listener.getLista().get(3));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("[name='test']", sel1.getContext().toString());
        Assert.assertEquals("[name='test']", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(13, sel1.getContext().getEndPosition());
        
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("[name='test']", sss.getContext().toString());
        Assert.assertEquals("[name='test']", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(13, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        AttributeSelector ts = (AttributeSelector) ss;
        Assert.assertEquals("name='test'", ts.getContext().toString());
        Assert.assertEquals("name", ts.getName());
        Assert.assertEquals(AttributeOperator.EQUALS, ts.getOperator());
        Assert.assertEquals("test", ts.getValue().getActualValue());
        Assert.assertEquals("[name='test']", ts.getContext().getSelector());
        Assert.assertEquals(1, ts.getContext().getStartPosition());
        Assert.assertEquals(12, ts.getContext().getEndPosition());
        
    }
    
    @Test
    public void attributeEscapeSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("[name='\\'test\\'']", listener);
        Assert.assertEquals(4, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("attributeSelector=name='\\'test\\''", listener.getLista().get(1));
        Assert.assertEquals("endGroup=0", listener.getLista().get(3));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("[name='\\'test\\'']", sel1.getContext().toString());
        Assert.assertEquals("[name='\\'test\\'']", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(17, sel1.getContext().getEndPosition());
        
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("[name='\\'test\\'']", sss.getContext().toString());
        Assert.assertEquals("[name='\\'test\\'']", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(17, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        AttributeSelector ts = (AttributeSelector) ss;
        Assert.assertEquals("name='\\'test\\''", ts.getContext().toString());
        Assert.assertEquals("name", ts.getName());
        Assert.assertEquals(AttributeOperator.EQUALS, ts.getOperator());
        Assert.assertEquals("'test'", ts.getValue().getActualValue());
        Assert.assertEquals("[name='\\'test\\'']", ts.getContext().getSelector());
        Assert.assertEquals(1, ts.getContext().getStartPosition());
        Assert.assertEquals(16, ts.getContext().getEndPosition());
        
    }
    
    @Test
    public void childSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("#id > .class", listener);
        Assert.assertEquals(6, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("idSelector=id", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|#id", listener.getLista().get(2));
        Assert.assertEquals("classSelector=class", listener.getLista().get(3));
        Assert.assertEquals("selectorSequence=>|.class", listener.getLista().get(4));
        Assert.assertEquals("endGroup=0", listener.getLista().get(5));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(2, sel1.getSelectors().size());
        Assert.assertEquals(1, sel1.getCombinators().size());
        Assert.assertEquals("#id>.class", sel1.getContext().toString());
        Assert.assertEquals("#id > .class", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(12, sel1.getContext().getEndPosition());
        
        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("#id", sss.getContext().toString());
        Assert.assertEquals("#id > .class", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(3, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof HashSelector);
        
        HashSelector ts = (HashSelector) ss;
        Assert.assertEquals("#id", ts.getContext().toString());
        Assert.assertEquals("id", ts.getName());
        Assert.assertEquals("#id > .class", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(3, ts.getContext().getEndPosition());
        
        //combinator
        Combinator c = sel1.getCombinators().get(0);
        Assert.assertEquals(Combinator.CHILD, c);
        
        //selector 2
        SimpleSelectorSequence sss2 = sel1.getSelectors().get(1);
        Assert.assertEquals(1, sss2.getSimpleSelectors().size());
        Assert.assertEquals(".class", sss2.getContext().toString());
        Assert.assertEquals("#id > .class", sss2.getContext().getSelector());
        Assert.assertEquals(6, sss2.getContext().getStartPosition());
        Assert.assertEquals(12, sss2.getContext().getEndPosition());
        
        SimpleSelector ss2 = sss2.getSimpleSelectors().get(0);
        Assert.assertTrue(ss2 instanceof ClassSelector);
        
        ClassSelector ts2 = (ClassSelector) ss2;
        Assert.assertEquals(".class", ts2.getContext().toString());
        Assert.assertEquals("class", ts2.getClassName());
        Assert.assertEquals("#id > .class", ts2.getContext().getSelector());
        Assert.assertEquals(6, ts2.getContext().getStartPosition());
        Assert.assertEquals(12, ts2.getContext().getEndPosition());
        
    }
    
    @Test
    public void siblingDescendantSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("a ~ #id .class", listener);
    	
        Assert.assertEquals(8, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("typeSelector=a", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|a", listener.getLista().get(2));
        Assert.assertEquals("idSelector=id", listener.getLista().get(3));
        Assert.assertEquals("selectorSequence=~|#id", listener.getLista().get(4));
        Assert.assertEquals("classSelector=class", listener.getLista().get(5));
        Assert.assertEquals("selectorSequence= |.class", listener.getLista().get(6));
        Assert.assertEquals("endGroup=0", listener.getLista().get(7));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(3, sel1.getSelectors().size());
        Assert.assertEquals(2, sel1.getCombinators().size());
        Assert.assertEquals("a~#id .class", sel1.getContext().toString());
        Assert.assertEquals("a ~ #id .class", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(14, sel1.getContext().getEndPosition());
        
        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("a", sss.getContext().toString());
        Assert.assertEquals("a ~ #id .class", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(1, sss.getContext().getEndPosition());
        
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("a", ts.getContext().toString());
        Assert.assertEquals("a", ts.getType());
        Assert.assertEquals("a ~ #id .class", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(1, ts.getContext().getEndPosition());
        
        //combinator 1
        Combinator c = sel1.getCombinators().get(0);
        Assert.assertEquals(Combinator.GENERAL_SIBLING, c);
        
        //selector 2
        SimpleSelectorSequence sss2 = sel1.getSelectors().get(1);
        Assert.assertEquals(1, sss2.getSimpleSelectors().size());
        Assert.assertEquals("#id", sss2.getContext().toString());
        Assert.assertEquals("a ~ #id .class", sss2.getContext().getSelector());
        Assert.assertEquals(4, sss2.getContext().getStartPosition());
        Assert.assertEquals(7, sss2.getContext().getEndPosition());
        
        SimpleSelector ss2 = sss2.getSimpleSelectors().get(0);
        Assert.assertTrue(ss2 instanceof HashSelector);
        
        HashSelector ts2 = (HashSelector) ss2;
        Assert.assertEquals("#id", ts2.getContext().toString());
        Assert.assertEquals("id", ts2.getName());
        Assert.assertEquals("a ~ #id .class", ts2.getContext().getSelector());
        Assert.assertEquals(4, ts2.getContext().getStartPosition());
        Assert.assertEquals(7, ts2.getContext().getEndPosition());
        
        //combinator 2
        Combinator c2 = sel1.getCombinators().get(0);
        Assert.assertEquals(Combinator.GENERAL_SIBLING, c2);
        
        //selector 3
        SimpleSelectorSequence sss3 = sel1.getSelectors().get(2);
        Assert.assertEquals(1, sss3.getSimpleSelectors().size());
        Assert.assertEquals(".class", sss3.getContext().toString());
        Assert.assertEquals("a ~ #id .class", sss3.getContext().getSelector());
        Assert.assertEquals(8, sss3.getContext().getStartPosition());
        Assert.assertEquals(14, sss3.getContext().getEndPosition());
        
        SimpleSelector ss3 = sss3.getSimpleSelectors().get(0);
        Assert.assertTrue(ss3 instanceof ClassSelector);
        
        ClassSelector ts3 = (ClassSelector) ss3;
        Assert.assertEquals(".class", ts3.getContext().toString());
        Assert.assertEquals("class", ts3.getClassName());
        Assert.assertEquals("a ~ #id .class", ts3.getContext().getSelector());
        Assert.assertEquals(8, ts3.getContext().getStartPosition());
        Assert.assertEquals(14, ts3.getContext().getEndPosition());
        
    }
    
    @Test
    public void composedSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("div.class1#id.class2", listener);

    	Assert.assertEquals(7, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("typeSelector=div", listener.getLista().get(1));
        Assert.assertEquals("classSelector=class1", listener.getLista().get(2));
        Assert.assertEquals("idSelector=id", listener.getLista().get(3));
        Assert.assertEquals("classSelector=class2", listener.getLista().get(4));
        Assert.assertEquals("selectorSequence=null|div.class1#id.class2", listener.getLista().get(5));
        Assert.assertEquals("endGroup=0", listener.getLista().get(6));
        
        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals("div.class1#id.class2", sel1.getContext().toString());
        Assert.assertEquals("div.class1#id.class2", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(20, sel1.getContext().getEndPosition());
        
        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(4, sss.getSimpleSelectors().size());
        Assert.assertEquals("div.class1#id.class2", sss.getContext().toString());
        Assert.assertEquals("div.class1#id.class2", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(20, sss.getContext().getEndPosition());
        
        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("div", ts.getContext().toString());
        Assert.assertEquals("div", ts.getType());
        Assert.assertEquals("div.class1#id.class2", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(3, ts.getContext().getEndPosition());
        
        //simple selector 2
        SimpleSelector ss2 = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss2 instanceof ClassSelector);
        
        ClassSelector ts2 = (ClassSelector) ss2;
        Assert.assertEquals(".class1", ts2.getContext().toString());
        Assert.assertEquals("class1", ts2.getClassName());
        Assert.assertEquals("div.class1#id.class2", ts2.getContext().getSelector());
        Assert.assertEquals(3, ts2.getContext().getStartPosition());
        Assert.assertEquals(10, ts2.getContext().getEndPosition());
        
        //simple selector 3
        SimpleSelector ss3 = sss.getSimpleSelectors().get(2);
        Assert.assertTrue(ss3 instanceof HashSelector);
        
        HashSelector ts3 = (HashSelector) ss3;
        Assert.assertEquals("#id", ts3.getContext().toString());
        Assert.assertEquals("id", ts3.getName());
        Assert.assertEquals("div.class1#id.class2", ts3.getContext().getSelector());
        Assert.assertEquals(10, ts3.getContext().getStartPosition());
        Assert.assertEquals(13, ts3.getContext().getEndPosition());
        
        //simple selector 4
        SimpleSelector ss4 = sss.getSimpleSelectors().get(3);
        Assert.assertTrue(ss4 instanceof ClassSelector);
        
        ClassSelector ts4 = (ClassSelector) ss4;
        Assert.assertEquals(".class2", ts4.getContext().toString());
        Assert.assertEquals("class2", ts4.getClassName());
        Assert.assertEquals("div.class1#id.class2", ts4.getContext().getSelector());
        Assert.assertEquals(13, ts4.getContext().getStartPosition());
        Assert.assertEquals(20, ts4.getContext().getEndPosition());
        
    }
    
    @Test
    public void attributeMultipleSelector() throws Exception {
        
    	String selector = "*[_onlyName], a[href~=https] ,.class[name|=\"name\"] , "
                + "span#composed-id[attr=_val],[z-indez*='1'],"
                + "[href][data-aria^='1']";
    	
    	List<Selector> selectors = Parser.parse(selector, listener);
        
        //group 0
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("typeSelector=*", listener.getLista().get(1));
        Assert.assertEquals("attributeSelector=_onlyName", listener.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|*[_onlyName]", listener.getLista().get(3));
        Assert.assertEquals("endGroup=0", listener.getLista().get(4));
        
        //group 1
        Assert.assertEquals("beginGroup=1", listener.getLista().get(5));
        Assert.assertEquals("typeSelector=a", listener.getLista().get(6));
        Assert.assertEquals("attributeSelector=href~=https", listener.getLista().get(7));
        Assert.assertEquals("selectorSequence=null|a[href~=https]", listener.getLista().get(8));
        Assert.assertEquals("endGroup=1", listener.getLista().get(9));
        
        //group 2
        Assert.assertEquals("beginGroup=2", listener.getLista().get(10));
        Assert.assertEquals("classSelector=class", listener.getLista().get(11));
        Assert.assertEquals("attributeSelector=name|=\"name\"", listener.getLista().get(12));
        Assert.assertEquals("selectorSequence=null|.class[name|=\"name\"]", listener.getLista().get(13));
        Assert.assertEquals("endGroup=2", listener.getLista().get(14));
        
        //group 3
        Assert.assertEquals("beginGroup=3", listener.getLista().get(15));
        Assert.assertEquals("typeSelector=span", listener.getLista().get(16));
        Assert.assertEquals("idSelector=composed-id", listener.getLista().get(17));
        Assert.assertEquals("attributeSelector=attr=_val", listener.getLista().get(18));
        Assert.assertEquals("selectorSequence=null|span#composed-id[attr=_val]", listener.getLista().get(19));
        Assert.assertEquals("endGroup=3", listener.getLista().get(20));
        
        //group 4
        Assert.assertEquals("beginGroup=4", listener.getLista().get(21));
        Assert.assertEquals("attributeSelector=z-indez*='1'", listener.getLista().get(22));
        Assert.assertEquals("selectorSequence=null|[z-indez*='1']", listener.getLista().get(23));
        Assert.assertEquals("endGroup=4", listener.getLista().get(24));
        
        //group 5
        Assert.assertEquals("beginGroup=5", listener.getLista().get(25));
        Assert.assertEquals("attributeSelector=href", listener.getLista().get(26));
        Assert.assertEquals("attributeSelector=data-aria^='1'", listener.getLista().get(27));
        Assert.assertEquals("selectorSequence=null|[href][data-aria^='1']", listener.getLista().get(28));
        Assert.assertEquals("endGroup=5", listener.getLista().get(29));
        
        //elements
        Assert.assertEquals(6, selectors.size());
        
        //group 0 -----------------------------------------------------------------------------------
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals("*[_onlyName]", sel1.getContext().toString());
        Assert.assertEquals(selector, sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(12, sel1.getContext().getEndPosition());
        
        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("*[_onlyName]", sss.getContext().toString());
        Assert.assertEquals(selector, sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(12, sss.getContext().getEndPosition());
        
        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("*", ts.getContext().toString());
        Assert.assertEquals("*", ts.getType());
        Assert.assertTrue(ts.isUniversal());
        Assert.assertEquals(selector, ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(1, ts.getContext().getEndPosition());
        
        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        AttributeSelector as = (AttributeSelector) ss;
        Assert.assertEquals("_onlyName", as.getContext().toString());
        Assert.assertEquals("_onlyName", as.getName());
        Assert.assertNull(as.getOperator());
        Assert.assertNull(as.getValue());
        Assert.assertEquals(selector, as.getContext().getSelector());
        Assert.assertEquals(2, as.getContext().getStartPosition());
        Assert.assertEquals(11, as.getContext().getEndPosition());
        
        //group 1 -----------------------------------------------------------------------------------
        Selector sel2 = selectors.get(1);
        Assert.assertEquals(1, sel2.getSelectors().size());
        Assert.assertEquals("a[href~=https]", sel2.getContext().toString());
        Assert.assertEquals(selector, sel2.getContext().getSelector());
        Assert.assertEquals(14, sel2.getContext().getStartPosition());
        Assert.assertEquals(28, sel2.getContext().getEndPosition());
        
        //selector 1
        sss = sel2.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("a[href~=https]", sss.getContext().toString());
        Assert.assertEquals(selector, sss.getContext().getSelector());
        Assert.assertEquals(14, sss.getContext().getStartPosition());
        Assert.assertEquals(28, sss.getContext().getEndPosition());
        
        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts2 = (TypeSelector) ss;
        Assert.assertEquals("a", ts2.getContext().toString());
        Assert.assertEquals("a", ts2.getType());
        Assert.assertFalse(ts2.isUniversal());
        Assert.assertEquals(selector, ts2.getContext().getSelector());
        Assert.assertEquals(14, ts2.getContext().getStartPosition());
        Assert.assertEquals(15, ts2.getContext().getEndPosition());
        
        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        as = (AttributeSelector) ss;
        Assert.assertEquals("href~=https", as.getContext().toString());
        Assert.assertEquals("href", as.getName());
        Assert.assertEquals(AttributeOperator.INCLUDES, as.getOperator());
        Assert.assertEquals("https", as.getValue().getActualValue());
        Assert.assertEquals("https", as.getValue().getContext().toString());
        Assert.assertEquals(selector, as.getContext().getSelector());
        Assert.assertEquals(16, as.getContext().getStartPosition());
        Assert.assertEquals(27, as.getContext().getEndPosition());
        
        //group 2 -----------------------------------------------------------------------------------
        Selector sel3 = selectors.get(2);
        Assert.assertEquals(1, sel3.getSelectors().size());
        Assert.assertEquals(".class[name|=\"name\"]", sel3.getContext().toString());
        Assert.assertEquals(selector, sel3.getContext().getSelector());
        Assert.assertEquals(30, sel3.getContext().getStartPosition());
        Assert.assertEquals(50, sel3.getContext().getEndPosition());
        
        //selector 1
        sss = sel3.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals(".class[name|=\"name\"]", sss.getContext().toString());
        Assert.assertEquals(selector, sss.getContext().getSelector());
        Assert.assertEquals(30, sss.getContext().getStartPosition());
        Assert.assertEquals(50, sss.getContext().getEndPosition());
        
        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof ClassSelector);
        
        ClassSelector ts4 = (ClassSelector) ss;
        Assert.assertEquals(".class", ts4.getContext().toString());
        Assert.assertEquals("class", ts4.getClassName());
        Assert.assertEquals(selector, ts4.getContext().getSelector());
        Assert.assertEquals(30, ts4.getContext().getStartPosition());
        Assert.assertEquals(36, ts4.getContext().getEndPosition());
        
        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        as = (AttributeSelector) ss;
        Assert.assertEquals("name|=\"name\"", as.getContext().toString());
        Assert.assertEquals("name", as.getName());
        Assert.assertEquals(AttributeOperator.DASH_MATCH, as.getOperator());
        Assert.assertEquals("name", as.getValue().getActualValue());
        Assert.assertEquals("name", as.getValue().getContext().toString());
        Assert.assertEquals(selector, as.getContext().getSelector());
        Assert.assertEquals(37, as.getContext().getStartPosition());
        Assert.assertEquals(49, as.getContext().getEndPosition());
        
        //group 3 -----------------------------------------------------------------------------------
        Selector sel4 = selectors.get(3);
        Assert.assertEquals(1, sel4.getSelectors().size());
        Assert.assertEquals("span#composed-id[attr=_val]", sel4.getContext().toString());
        Assert.assertEquals(selector, sel4.getContext().getSelector());
        Assert.assertEquals(53, sel4.getContext().getStartPosition());
        Assert.assertEquals(80, sel4.getContext().getEndPosition());
        
        //selector 1
        sss = sel4.getSelectors().get(0);
        Assert.assertEquals(3, sss.getSimpleSelectors().size());
        Assert.assertEquals("span#composed-id[attr=_val]", sss.getContext().toString());
        Assert.assertEquals(selector, sss.getContext().getSelector());
        Assert.assertEquals(53, sss.getContext().getStartPosition());
        Assert.assertEquals(80, sss.getContext().getEndPosition());
        
        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts5 = (TypeSelector) ss;
        Assert.assertEquals("span", ts5.getContext().toString());
        Assert.assertEquals("span", ts5.getType());
        Assert.assertFalse(ts5.isUniversal());
        Assert.assertEquals(selector, ts5.getContext().getSelector());
        Assert.assertEquals(53, ts5.getContext().getStartPosition());
        Assert.assertEquals(57, ts5.getContext().getEndPosition());
        
        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof HashSelector);
        
        HashSelector hs = (HashSelector) ss;
        Assert.assertEquals("#composed-id", hs.getContext().toString());
        Assert.assertEquals("composed-id", hs.getName());
        Assert.assertEquals(selector, hs.getContext().getSelector());
        Assert.assertEquals(57, hs.getContext().getStartPosition());
        Assert.assertEquals(69, hs.getContext().getEndPosition());
        
        //simple selector 3
        ss = sss.getSimpleSelectors().get(2);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        as = (AttributeSelector) ss;
        Assert.assertEquals("attr=_val", as.getContext().toString());
        Assert.assertEquals("attr", as.getName());
        Assert.assertEquals(AttributeOperator.EQUALS, as.getOperator());
        Assert.assertEquals("_val", as.getValue().getActualValue());
        Assert.assertEquals("_val", as.getValue().getContext().toString());
        Assert.assertEquals(selector, as.getContext().getSelector());
        Assert.assertEquals(70, as.getContext().getStartPosition());
        Assert.assertEquals(79, as.getContext().getEndPosition());
        
        //group 4 -----------------------------------------------------------------------------------
        Selector sel5 = selectors.get(4);
        Assert.assertEquals(1, sel5.getSelectors().size());
        Assert.assertEquals("[z-indez*='1']", sel5.getContext().toString());
        
        //selector 1
        sss = sel5.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("[z-indez*='1']", sss.getContext().toString());
        
        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        as = (AttributeSelector) ss;
        Assert.assertEquals("z-indez*='1'", as.getContext().toString());
        Assert.assertEquals("z-indez", as.getName());
        Assert.assertEquals(AttributeOperator.SUBSTRING_MATCH, as.getOperator());
        Assert.assertEquals("1", as.getValue().getActualValue());
        Assert.assertEquals("1", as.getValue().getContext().toString());
        
        //group 5 -----------------------------------------------------------------------------------
        Selector sel6 = selectors.get(5);
        Assert.assertEquals(1, sel6.getSelectors().size());
        Assert.assertEquals("[href][data-aria^='1']", sel6.getContext().toString());
        
        //selector 1
        sss = sel6.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("[href][data-aria^='1']", sss.getContext().toString());
        
        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        as = (AttributeSelector) ss;
        Assert.assertEquals("href", as.getContext().toString());
        Assert.assertEquals("href", as.getName());
        Assert.assertNull(as.getOperator());
        Assert.assertNull(as.getValue());
        
        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof AttributeSelector);
        
        as = (AttributeSelector) ss;
        Assert.assertEquals("data-aria^='1'", as.getContext().toString());
        Assert.assertEquals("data-aria", as.getName());
        Assert.assertEquals(AttributeOperator.PREFIX_MATCH, as.getOperator());
        Assert.assertEquals("1", as.getValue().getActualValue());
        Assert.assertEquals("1", as.getValue().getContext().toString());
        
    }
    
    @Test
    public void adjascentGroupSelector() throws Exception {
        
    	List<Selector> selectors = Parser.parse("li + li.class1.class2,table#id tr", listener);

    	Assert.assertEquals(15, listener.getLista().size());
        Assert.assertEquals("beginGroup=0", listener.getLista().get(0));
        Assert.assertEquals("typeSelector=li", listener.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|li", listener.getLista().get(2));
        Assert.assertEquals("typeSelector=li", listener.getLista().get(3));
        Assert.assertEquals("classSelector=class1", listener.getLista().get(4));
        Assert.assertEquals("classSelector=class2", listener.getLista().get(5));
        Assert.assertEquals("selectorSequence=+|li.class1.class2", listener.getLista().get(6));
        Assert.assertEquals("endGroup=0", listener.getLista().get(7));
        Assert.assertEquals("beginGroup=1", listener.getLista().get(8));
        Assert.assertEquals("typeSelector=table", listener.getLista().get(9));
        Assert.assertEquals("idSelector=id", listener.getLista().get(10));
        Assert.assertEquals("selectorSequence=null|table#id", listener.getLista().get(11));
        Assert.assertEquals("typeSelector=tr", listener.getLista().get(12));
        Assert.assertEquals("selectorSequence= |tr", listener.getLista().get(13));
        Assert.assertEquals("endGroup=1", listener.getLista().get(14));
        
        Assert.assertEquals(2, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(2, sel1.getSelectors().size());
        Assert.assertEquals(1, sel1.getCombinators().size());
        Assert.assertEquals("li+li.class1.class2", sel1.getContext().toString());
        Assert.assertEquals("li + li.class1.class2,table#id tr", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(21, sel1.getContext().getEndPosition());
        
        Assert.assertEquals(Combinator.ADJASCENT_SIBLING, sel1.getCombinators().get(0));
        
        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals("li", sss.getContext().toString());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(2, sss.getContext().getEndPosition());
        
        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);
        
        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("li", ts.getContext().toString());
        Assert.assertEquals("li", ts.getType());
        
        //selector 2
        sss = sel1.getSelectors().get(1);
        Assert.assertEquals(3, sss.getSimpleSelectors().size());
        Assert.assertEquals("li.class1.class2", sss.getContext().toString());
        
        //simple selector 1
        SimpleSelector ss2 = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss2 instanceof TypeSelector);
        ts = (TypeSelector) ss2;
        Assert.assertEquals("li", ts.getContext().toString());
        Assert.assertEquals("li", ts.getType());
        
        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof ClassSelector);
        ClassSelector ts4 = (ClassSelector) ss;
        Assert.assertEquals(".class1", ts4.getContext().toString());
        Assert.assertEquals("class1", ts4.getClassName());
        
        //simple selector 3
        ss = sss.getSimpleSelectors().get(2);
        Assert.assertTrue(ss instanceof ClassSelector);
        ts4 = (ClassSelector) ss;
        Assert.assertEquals(".class2", ts4.getContext().toString());
        Assert.assertEquals("class2", ts4.getClassName());
        
    }
    
}
