package next.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Junit3Runner {
    @Test
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;

        Method[] methods = clazz.getDeclaredMethods();

        for(Method method : methods) {
            method.invoke(clazz.getDeclaredConstructor().newInstance());
        }

    }
}
