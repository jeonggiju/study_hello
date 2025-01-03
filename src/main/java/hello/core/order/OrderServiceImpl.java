package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

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

    @Autowired // 생성자가 1개 있으면 생략가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
