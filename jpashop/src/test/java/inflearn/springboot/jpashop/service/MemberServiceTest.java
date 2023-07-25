package inflearn.springboot.jpashop.service;

import inflearn.springboot.jpashop.domain.Member;
import inflearn.springboot.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Id;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest /* @SpringBootTest에는 @ExtendWith 가 포함되어 있음 */
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false) /* Test에서 Rollback을 안 하기 위한 어노테이션 */
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Kong");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); /* 영속성 컨텍스트에 있는 값을 강제로 Insert 즉 @Rollback(false)를 추가하지 않고 Insert 쿼리문을 볼 수 있고  Rollback 가능하다. */
        assertEquals(member, memberRepository.findOne(savedId)); /* JPA에서 같은 tx 안에서 같은 pk를 가지면 같은 영속성 컨텍스트에서 하나로 관리된다. 즉 equals == true */
    } /* Insert 문이 발생하지 않음. 왜 ? em.persist는 영속성 컨텍스트에 저장하는거지 DB에 Insert X 즉 tx가 커밋될 때 DB에 저장되는 것이다. 또 Test에선 Rollback 되니 Insert를 안 한다.*/

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Kong");

        Member member2 = new Member();
        member2.setName("Kong");

        //when
        memberService.join(member1);

//        try {
//            memberService.join(member2); /* 예외가 발생해야 한다. */
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2)); /* assertThrows() 사용하여 특정 예외가 발생하는지 테스트 할 수 있다. */

//        fail("예외가 발생해야 한다."); /* 해당 코드가 오면 안 된다. 즉 fail() */
    }
}