package hello.core.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component // bean 으로 등록될 때는 MemoryMemberRepository 로 등록된다.
public class MemoryMemberRepository implements MemberRepository {
    /*
    * HashMap -> ConcurrentHashMap을 쓰는게 좋음
    * why? 동시성 이슈 때문임
    * */
    private static Map<Long, Member> store = new HashMap<Long, Member>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId){
        return store.get(memberId);
    }
}
