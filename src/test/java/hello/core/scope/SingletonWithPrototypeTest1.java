package hello.core.scope;

import hello.core.AppConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean = ac.getBean(ClientBean.class);
        int count1 = clientBean.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        // singleton 의 경우 하나만 생성해서 여러 클라이언트가 참조된다.
        // singleton 안의 prototype 은 또한 한 번 생성된다. 그렇기에 singleton 안의 prototype 은 그대로 유지된다.
        // 만일 prototype 이 계속 생성되는 것을 원한다면 Provider 를 사용한다.
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    @Component
    static class ClientBean{

        /**
         * 해결책 1. 스프링 컨테이너를 요청
         * - 이처럼 의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependency Lookup(DL) 의존관계 조회(탐색) 이라한다.
         * - 허나 컨테이너 전체를 주입받게 되면, 스프링 컨테이너에 종속적인 코드가 되고, 단위테스트도 어려워진다.
         *       @Autowired
         *        private ApplicationContext ac;
         * */

        /**
         * 해결책 2. ObjectFactory, ObjectProvider
         *       @Autowired
         *       private ObjectProvider<PrototypeBean> prototypeBeanProvider;
         * */

        @Autowired
        private Provider<PrototypeBean> provider;

//        private final PrototypeBean prototypeBean; // 생성 시점에 주입 x01

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic(){
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

//    @Scope("singleton")
//    @Component
//    static class ClientBean1{
//        private final PrototypeBean prototypeBean; // 생성 시점에 주입 x02
//
//        @Autowired
//        public ClientBean1(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//
//        public int logic(){
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }

    @Scope("prototype")
    @Component
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
