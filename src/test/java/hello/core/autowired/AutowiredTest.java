package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);


    }

    @Configuration
    static class TestBean{


        /**
         * 결과:
         * nobean2 = null
         * nobean3 = Optional.empty
         * */
         // 호출 안됨
        @Autowired(required = false)
        public void setNoBean1(Member nobean1){
            System.out.println("nobean1 = " + nobean1);
        }

         // null 호출
        @Autowired(required = false)
        public void setNoBean2(@Nullable Member nobean2){
            System.out.println("nobean2 = " + nobean2);
        }

         // Optional.empty 호출
        // 참고: java8 에서 제공함
        @Autowired
        public void setNoBean3(Optional<Member> nobean3){
            System.out.println("nobean3 = " + nobean3);
        }

    }
}
