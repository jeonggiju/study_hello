package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 !=  memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
        /**
         * 결과값:
         * memberService1 = hello.core.member.MemberServiceImpl@7fc229ab
         * memberService2 = hello.core.member.MemberServiceImpl@2cbb3d47
         *
         * 이렇게 되면 호출할 때 마다 jvm 메모리에 계속 객체가 생성되서 올라간다.
         * 웹 어플리케이션은 고객 요청이 많다. 가령, 특정회사는 TPS(Transactions Per Second)가 50000까지 나온다.
         * 만일 이렇게 싱글톤이 적용되지 않으면 초당 5만 개의 객체가 생성되는 것이다.
         *
         * 해결방안으로는 해당 객체가 딱 1개만 생성되고, 이를 공유하는 것이다.
         * */
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        /**
         * 결과값
         * singletonService1 = hello.core.singleton.SingletonService@1a0dcaa
         * singletonService2 = hello.core.singleton.SingletonService@1a0dcaa
         *
         * 참고:
         * - isSameAs : 두 객체가 메모리에서 같은 위치(참조)를 가리키는지 비교한다.
         * - isEqualTo : 두 객체의 내용(값)이 같은지 비교한다.
         * */

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // 1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);



        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        /**
         * 결과값:
         * memberService1 = hello.core.member.MemberServiceImpl@6eb2384f
         * memberService2 = hello.core.member.MemberServiceImpl@6eb2384f
         * */

        // memberService1 !=  memberService2
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
