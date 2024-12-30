package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    /**
    * 이 코드는 "구현"에 의존한 코드이다.
    * "역할"에 의존한 코드가 아니다.
    * */
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        MemberServiceImpl memberService = ac.getBean("memberService" ,MemberServiceImpl.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    /*
    * org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'xxxxx' available
    *
    * 아래 코드는 에러가 뜬다.
    * */
    @Test
    @DisplayName("빈 이름으로 조회x")
    void findBeanByNameX(){
        /**
            오른쪽의 람다식을 실행하면 예외(Exception)가 떠야한다.
        */
        assertThrows(NoSuchBeanDefinitionException.class, ()->{
            ac.getBean("xxxxx", MemberServiceImpl.class);
        });

    }

}
