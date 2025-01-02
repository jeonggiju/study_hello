package hello.core.member;

public class MemberServiceImpl implements MemberService {

    /*
    * DIP(Dependency Inversion Principle)를 준수하는가? x
    * - MemberServiceImpl은 MemberRepository와 MemoryMemberRepository 둘 다 의존한다.
    * */
    private final MemberRepository memberRepository;

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

