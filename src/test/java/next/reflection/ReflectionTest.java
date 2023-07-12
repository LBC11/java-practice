package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
    public void showClass() {
        SoftAssertions s = new SoftAssertions();
        Class<Question> clazz = Question.class;
        logger.debug("Class Name {}", clazz.getName());

        for(Field field : clazz.getDeclaredFields()) {
            logger.debug("field Name {}", field.getName());
        }

        for(Method method : clazz.getDeclaredMethods()) {
            logger.debug("method Name {}", method.getName());
        }

        for(Constructor constructor : clazz.getDeclaredConstructors()) {
            logger.debug("constructor Name {}", constructor.getName());
        }
    }

    @Test
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;

        Student student = clazz.getDeclaredConstructor().newInstance();
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "재성");
        name.setAccessible(false);

        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.set(student, 25);
        age.setAccessible(false);

        logger.debug(student.getName());
        logger.debug(String.valueOf(student.getAge()));
    }

    @Test
    public void create() throws Exception {
        Class<User> clazz = User.class;

        User user = clazz.getDeclaredConstructor().newInstance();

        Field name = user.getClass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(user, "dddd");
        name.setAccessible(false);

        logger.debug(user.getName());
        logger.debug(String.valueOf(user.getAge()));
    }

    @Test
    public void EstimateTime() throws Exception {
        Class<TimeRunner> clazz = TimeRunner.class;

        TimeRunner timeRunner = clazz.getDeclaredConstructor().newInstance();

        long start = System.currentTimeMillis();

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if(declaredMethod.isAnnotationPresent(ElapsedTime.class)) {
                declaredMethod.invoke(timeRunner, 1000);
            }
        }

        assertThat(System.currentTimeMillis() - start).isCloseTo(1000, Offset.offset(500L));
    }
}
