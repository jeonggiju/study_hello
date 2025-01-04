package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @RequiredArgsConstructor
 *  - final 이 붙으면 무조건 필수값이 된다. final 이 붙은거에 한에서 생성자를 호출해준다.
 *  다음코드와 동일
 *  @Autowired // 생성자가 1개 있으면 생략가능
 *     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
 *         System.out.println("1. OrderServiceImpl.OrderServiceImpl");
 *         this.memberRepository = memberRepository;
 *         this.discountPolicy = discountPolicy;
 *     }
 *
 * @Primary
 * - 만일 빈 타입이 같은 것이 여러 개라면 우선권을 가지게 된다.
 * */

@Component
//@RequiredArgsConstructor
@Primary
public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    @Autowired()
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("2. OrderServiceImpl.setMemberRepository");
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("2. OrderServiceImpl.setDiscountPolicy");
//        this.discountPolicy = discountPolicy;
//    }


    /**
     * @Autowired
     * Autowired 의 경우 타입 매칭을 시도한다.
     * 만일 여러 개의 빈이 있다면 이름으로 빈 이름을 추가 매칭한다.
     * 아래의 경우 MemberRepository -> memberRepository 순으로 시도한다.
     *
     * @Qualifier("mainDiscountPolicy")
     * 미리 설정한 mainDiscountPolicy 라는 이름의 Component 를 찾는다.
     * 만일, 찾지 못한다면 mainDiscountPolicy 라는 이름을 가진 bean 을 찾는다. <- 이런 상황이 오지 않도록 코딩해야한다.
     * */
    @Autowired // 생성자가 1개 있으면 생략가능
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     * SRP(Single Responsibility Principle)이 잘지켜진것.
     * 왜? Order입장에서는 Discount가 뭐하는지 모른다.
     * */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 섹션6-5 test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
