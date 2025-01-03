package hello.core.scan.filter;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig
                .class);

        BeanA beanA = ac.getBean(BeanA.class); // MyIncludeComponent
        Assertions.assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB",BeanB.class) // MyExcludeComponent
        );
    }

    @Configuration
    @ComponentScan(
            // type = FilterType.ANNOTATION 이 default 라서 생략이 가능하다.
            includeFilters = @Filter(type= FilterType.ANNOTATION, classes=MyIncludeComponent.class),
            excludeFilters = {
//                    @Filter(type = FilterType.ASSIGNABLE_TYPE, classes=BeanA.class), // BeanA 도 exclude 하고자 한다면.
                    @Filter(type= FilterType.ANNOTATION, classes=MyExcludeComponent.class)
            }
    )
    static class ComponentFilterAppConfig {}
}
