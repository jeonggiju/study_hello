package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;


public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 스프링 컨테이너는 prototype Bean 을 초기화까지만 관리하기에
        // 수동으로 destroy() 를 호출한다.
        prototypeBean1.destroy();
        prototypeBean2.destroy();
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            // prototype 의 경우 bean 을 호출할 때마다 생성되기에 init 이 위의 테스트에서 두 번 호출된다.
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            // Scope 가 prototype 인 경우 빈의 생명주기가 초기화(@PostConstruct)까지 이뤄지기에 @PreDestroy 가 호출되지 않는다.
            System.out.println("PrototypeBean.destroy");
        }

    }
}
