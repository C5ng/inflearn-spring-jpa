package inflearn.springboot.jpashop.service;

import inflearn.springboot.jpashop.domain.Delivery;
import inflearn.springboot.jpashop.domain.Member;
import inflearn.springboot.jpashop.domain.Order;
import inflearn.springboot.jpashop.domain.OrderItem;
import inflearn.springboot.jpashop.domain.item.Item;
import inflearn.springboot.jpashop.repository.ItemRepository;
import inflearn.springboot.jpashop.repository.MemberRepository;
import inflearn.springboot.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        /* 엔티티 조회 */
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        /* 배송정보 생성 */
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        /* 주문상품 생성 */
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        /* 주문 생성 */
        Order order = Order.createOrder(member, delivery, orderItem);

        /* 주문 저장 */
        orderRepository.save(order); /* order만 저장해줘도 OrderItem, delivery 가 저장된다. cascade 때문 */

        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        /* 주문 엔티티 조회 */
        Order order = orderRepository.findOne(orderId);

        /* 주문 취소 */
        order.cancel(); /* JPA를 사용하면 Entity 안에서 Data만 바꿔주면 JPA가 알아서 바뀐 변경 포인트를 (더티체킹) 알아서 DB에 update 쿼리를 날려준다. */
    }

    /**
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
