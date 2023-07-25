package inflearn.springboot.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepositoryEx {

    @PersistenceContext /* EM을 빈으로 주입할 때 사용하는 어노테이션이다. */
    private EntityManager em;

    public Long save(MemberEx member) {
        em.persist(member);
        return member.getId();
    } /* 저장 로직, 왜 id 반환 ? 커맨드와 쿼리를 분리해라 */

    public MemberEx find(Long id) {
        return em.find(MemberEx.class, id);
    }
}
