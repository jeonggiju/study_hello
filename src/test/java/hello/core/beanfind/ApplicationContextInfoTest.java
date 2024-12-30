package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBeans(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // iter -> tab : for문이 자동으로 만들어진다.
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            // soutv : value 명을 출력
            // soutm : method 명을 출력
            System.out.println("name = " + beanDefinitionName + ", object = " + bean);
        }
    }
    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBeans(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // iter -> tab : for문이 자동으로 만들어진다.
        for (String beanDefinitionName : beanDefinitionNames) {
            // 빈에 대한 메타데이터 정보
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // Role BeanDefinition.ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            // Role BeanDefinition.ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + ", object = " + bean);
            }
        }
    }
}
