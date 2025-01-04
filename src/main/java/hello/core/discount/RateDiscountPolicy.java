package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.MainDiscountPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy") <- main 이기에 컴파일시 제대로 확인할 수 없다. 가령, mainDiscountPpolicy 와 같은 오타를 확인할 수 없다.
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price){
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        }else{
            return 0 ;
        }
    }
}
