package inflearn.springboot.jpashop.service;

import inflearn.springboot.jpashop.domain.Member;
import inflearn.springboot.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) /* JPA는 무조건 Transaction 안에서 실행되어야 한다. */ /* JPA 사용 내에서 조회하는 곳에 사용하면 성능이 최적화 된다. */
@RequiredArgsConstructor /* final만 있는 필드만 생성자로 만들어준다. */
public class MemberService {

    private final MemberRepository memberRepository; /* 변경할 일이 없으니 final로 선언 */

//    @Autowired /* 필드 주입, setter 주입, 생성자 주입 -> 생성자 주입을 사용하는게 가장 좋음, 즉 이렇게 생성자 주입을 하는 것과 @RequriedArgsConstructor 사용하는 것이 같음 */
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /* 회원 가입 */
    @Transactional /* 데이터를 변경하는 곳에는 따로 어노테이션을 추가해서 클래스 단위로 설정한 어노테이션이 적용 안 되게 설정한다. */
    public Long join(Member member) {
        /* 중복 회원은 안 되는 로직 추가. */
        validateDuplicateMember(member);

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName()); /* 만약 여러 클라이언트가 동시에 save하게 된다면 둘다 save가 됨 왜 ? 현재는 db에 존재하지 않기 때문 */
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /* 회원 전체 조회 */
//    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 조회 */
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
