package com.typingx.me.bytecode;

import java.lang.reflect.Method;

import com.google.common.collect.ImmutableList;

import io.airlift.bytecode.*;
import static io.airlift.bytecode.Access.PUBLIC;
import static io.airlift.bytecode.Access.FINAL;
import static io.airlift.bytecode.Access.STATIC;
import static io.airlift.bytecode.Access.a;
import static io.airlift.bytecode.Parameter.arg;
import static io.airlift.bytecode.ParameterizedType.type;
import static io.airlift.bytecode.expression.BytecodeExpressions.add;
import static io.airlift.bytecode.ClassGenerator.classGenerator;

import static org.junit.Assert.*;

public class ByteCodeDemo {

    public Class<?> generateClass() {
        // 1. define the class, because all the method must own to one class in java
        ClassDefinition classDef = new ClassDefinition(a(PUBLIC, FINAL), "ByteCodeTest", type(Object.class));

        // 2. define the parametes(optional)
        // two input type with int type
        Parameter argA = arg("a", int.class);
        Parameter argB = arg("b", int.class);

        // 3. define the method define
        // return type is int type
        MethodDefinition method = classDef.declareMethod(a(PUBLIC, STATIC), "add", type(int.class),
                ImmutableList.of(argA, argB));

        // 4. define the body
        // using a inner build op add
        method.getBody().append(add(argA, argB)).retInt();

        // 5. construct the class
        Class<?> clazz = classGenerator(this.getClass().getClassLoader()).defineClass(classDef, Object.class);

        return clazz;
    }

    public static void main(String[] args) throws Exception {
        try {
            ByteCodeDemo byteCodeDemo = new ByteCodeDemo();
            Class<?> clazz = byteCodeDemo.generateClass();
            Method add = clazz.getMethod("add", int.class, int.class);
            int result = (int)add.invoke(null, 13, 42);
            assertEquals(result, 55);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Exception(e.getMessage());
        }
    }
}
