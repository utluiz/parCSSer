package br.com.starcode.parccser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomUseCasesTest {

    MockListener listener;

    @Before
    public void setup() {
        listener = new MockListener();
    }

    @Test
    public void withTrailingSpaces() throws Exception {
        Parser.parse("#g-mainbar > div  ", listener);
        Assert.assertEquals(6, listener.getList().size());
    }

//    @Test
//    public void withDashes() throws Exception {
//        Parser.parse("#g-mainbar > --smBtn ", listener);
//        Assert.assertEquals(8, listener.getList().size());
//    }

}
