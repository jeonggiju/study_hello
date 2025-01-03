package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    /*
    * DIP(Dependency Inversion Principle)를 준수하는가? x
    * - MemberServiceImpl은 MemberRepository와 MemoryMemberRepository 둘 다 의존한다.
    * */
    private final MemberRepository memberRepository;

    /**
     * Spring 이 자동으로 MemberRepository 에 맞는, 타입에 맞는 Component(여기선 MemoryMemberRepository) 를 주입해준다.
     * ac.getBean(MemberRepository memberRepository.class) 따위와 같다.
     * */
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 섹션6-5 test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

