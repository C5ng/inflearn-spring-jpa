package inflearn.springboot.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order") /* mappedBy는 OrderItem.class의 order 필드에 의해 매핑이 되었으니 order이다. */
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; /* 주문시간 */

    @Enumerated(EnumType.STRING)
    private OrderStatus status; /* 주문상태 [ORDER, CANCEL] */
}
