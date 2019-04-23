package com.chatbot.helper;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToolsTest {

    @Test
    public void multiply() {
        int result  = Tools.multiply(9,5);
     //   Assert.assertTrue(result == 45);
        Assert.assertTrue("Result matched" ,result == 45);

    }
}