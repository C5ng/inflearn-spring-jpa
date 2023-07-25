package inflearn.springboot.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") /* 연관관계 주인이 아닌 읽기만 가능하게 만들어 준다. 매핑을 하는 것이 아닌 매핑 되어진*/
    private List<Order> orders = new ArrayList<>(); /* ArrayList로 초기화 해주는 것이 관례 */
}
