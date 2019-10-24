package test;

import bootstrap.Example;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 执行顺序: @BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 * beforeClass和afterClass在当前类所有测试方法执行前后会执行一次。
 * before和after在当前类所有测试方法执行前后会执行一次。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Example.class)
public class DemoTest {


    /**
     * expected 指明期望方法抛出的异常
     * timeout 指明测试方法的超时时间
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        System.out.println("test");
    }

    @Test
    public void test2() throws Exception {
        System.out.println("test2");
    }

    @BeforeClass
    public static void beforeClass(){
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("afterClass");
    }
    @Before
    public void before(){
        System.out.println("before");
    }

    @After
    public void after(){
        System.out.println("after");
    }


}
