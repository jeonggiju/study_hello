package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 의 역할: 싱글톤을 유지시켜준다.
 * 가령 아래의 예에서 new MemoryMemberRepository() 가 여러번 호출되도 하나의 instance 를 보장한다.
 * 하지만 없다면 다음과 같이 instance 가 다 달라짐을 확인할 수 있다.
 * =============
 * memberRepository = hello.core.member.MemoryMemberRepository@4f74980d
 * memberService.getMemberRepository() = hello.core.member.MemoryMemberRepository@6c372fe6
 * orderService.getMemberRepository() = hello.core.member.MemoryMemberRepository@58594a11
 *
 * 이 말은 즉슨 스프링 컨테이너가 빈(Bean)을 관리하지 않는 것과 동일하다. 가령 @Configuration 을 넣지 않는 다는 말은
 * - private final MemberRepository memberRepository = new memberRepositoryImpl(new MemoryRepository());
 * 와 동일 한 것이며, 이는 의존성에 위배된다.
 *
 * =============
 * @Configuration 가 없는 상태에서 AppConfig 를 soutv 로 찍으면
 * appConfig.getClass() = class hello.core.AppConfig
 * @Configuration 가 있는 상태에서 AppConfig 를 soutv 로 찍으면
 * appConfig.getClass() = class hello.core.AppConfig$$SpringCGLIB$$0
 *
 * 왜?? 스프링이 CGLIB 라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다
 * 른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것
 *
 * "CGLIB(Code Generation Library)"
 * CGLIB 는 Java 에서 동적으로 클래스를 생성하거나 확장할 수 있도록 지원하는 오픈소스 라이브러리입니다.
 * 이는 바이트코드 조작(Bytecode Manipulation)을 통해 런타임에 클래스를 생성하거나 메서드를 오버라이드하는 기능을 제공
 * */

@Configuration
public class AppConfig {

    @Bean
    public MemberRepository memberRepository() {
        /**
         * OrderService, MemberService 둘 다 memberRepository()를 호출함으로써 new MemoryMemberRepository()를 호출한다.
         * 그러면 의문,
         * 싱글톤이 깨지는 것이 아닌가? new 를 두 번 했으니 instance 가 두 개 생성되는 거 아닌가?
         * - 결과: 아니다. new MemoryMemberRepository 는 한 번만 호출된다.
         * */
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public MemberService memberService(){
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }



}