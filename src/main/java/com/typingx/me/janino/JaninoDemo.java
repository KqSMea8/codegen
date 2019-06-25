package com.typingx.me.janino;

import java.lang.reflect.InvocationTargetException;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.*;

import static org.junit.Assert.assertEquals;


public class JaninoDemo {
    public static void main(String[] args) {
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.cook("3 + 4");
            int result = (int) ee.evaluate(null);
            assertEquals(result, 7);
        } catch (CompileException | InvocationTargetException e) {
            System.out.println(e.toString());
        }
    }
}
