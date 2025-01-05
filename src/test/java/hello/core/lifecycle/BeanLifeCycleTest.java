package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfiguration.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // 컨테이너 닫기 -> 빈이 소멸하면서 소멸 메서드가 호출된다.
    }

    @Configuration
    static class LifeCycleConfiguration{
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean()
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello.com");
            return networkClient;
        }
    }
}
