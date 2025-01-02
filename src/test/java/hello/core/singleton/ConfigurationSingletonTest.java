package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        /**
         * 결과: 셋의 instance 는 모두 동일하다.
         * memberRepository = hello.core.member.MemoryMemberRepository@73194df
         * memberService.getMemberRepository() = hello.core.member.MemoryMemberRepository@73194df
         * orderService.getMemberRepository() = hello.core.member.MemoryMemberRepository@73194df
         * */
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberService.getMemberRepository() = " + memberService.getMemberRepository());
        System.out.println("orderService.getMemberRepository() = " + orderService.getMemberRepository());

        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberService.getMemberRepository());
    }

    /**
     * 출력 결과:
     * expect: appConfig = class hello.core.AppConfig
     * real: appConfig = class hello.core.AppConfig$$SpringCGLIB$$0
     * "CGLIB" 가 붙여짐을 확인할 수 있다.
     * */
    @Test
    void configurationDeep(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = ac.getBean(AppConfig.class);

        System.out.println("appConfig.getClass() = " + appConfig.getClass());
    }
}
