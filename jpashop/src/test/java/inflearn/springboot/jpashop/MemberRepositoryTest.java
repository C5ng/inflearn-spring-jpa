package inflearn.springboot.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryEx memberRepository;

    @Test
    @Transactional /* @Transactional이 테스트에 있으면 테스트가 끝난 후 롤백한다. */
    @Rollback(false) /* @Rollback(false)를 추가하면 롤백되지 않는다. */
    public void testMember() throws Exception {
        //given
        MemberEx member = new MemberEx();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        MemberEx findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); /* findMember, member는 같은 Transaction 내에서 같은 영속성 컨텍스트에 존재한다. 즉 같은 영속성 컨텍스트에서 id 값이 같으니 같은 엔티티이다. 또한 1차캐시에서 가져온다. */
    }
}