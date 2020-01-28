package giit.java.maven.hello;

import giit.java.maven.lang.Lang;
import giit.java.maven.lang.LangRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {

    private static final String WELCOME = "Hello";
    private static final String FALLBACK_ID_WELCOME = "Hola";

    @Test
    public void test_prepareGreeting_nullName_returnGreetingWithFallback() {
        //given
        LangRepository mockRepository = alwaysReturningHelloReporsitory();
        HelloService SUT = new HelloService(mockRepository);

        //when
        String result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnGreetingWithName() {
        //given
        LangRepository mockRepository = alwaysReturningHelloReporsitory();
        HelloService SUT = new HelloService(mockRepository);
        String name = "test";

        //when
        String result = SUT.prepareGreeting(name, -1);

        //then
        assertEquals(WELCOME + " " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() {
        //given
        LangRepository mockRepository = new LangRepository() {
            Optional<Lang> findById(Long id) {
                return Optional.empty();
            }
        };
        HelloService SUT = new HelloService(mockRepository);

        //when
        String result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!", result);

    }

    @Test
    public void test_prepareGreeting_nullLang_returnGreetingWithFallbackIdLang() {
        //given
        LangRepository mockRepository = fallbackLangIdRepository();
        HelloService SUT = new HelloService(mockRepository);

        //when
        String result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    /*@Test
    public void test_prepareGreeting_textLang_returnGreetingWithFallbackIdLang() {
        //given
        LangRepository mockRepository = fallbackLangIdRepository();
        HelloService SUT = new HelloService(mockRepository);

        //when
        String result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }*/

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                if(id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private LangRepository alwaysReturningHelloReporsitory() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELCOME, ""));
            }
        };
    }

}
