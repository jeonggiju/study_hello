package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulServiceSingleton1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulServiceSingleton2 = ac.getBean("statefulService",StatefulService.class);

        // ThreadA : A 사용자 10000원 주문
        statefulServiceSingleton1.order("userA", 10000);
        // ThreadB : B 사용자 20000원 주문
        statefulServiceSingleton2.order("userB", 20000);

        // ThreadA : 사용자 A 주문 금액 조회
        int price = statefulServiceSingleton1.getPrice();
        System.out.println("price = " + price); // except: 10000, real: 20000

        Assertions.assertThat(price).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}