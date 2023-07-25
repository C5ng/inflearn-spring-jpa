package inflearn.springboot.jpashop.service;

import inflearn.springboot.jpashop.domain.Address;
import inflearn.springboot.jpashop.domain.Member;
import inflearn.springboot.jpashop.domain.Order;
import inflearn.springboot.jpashop.domain.OrderStatus;
import inflearn.springboot.jpashop.domain.item.Book;
import inflearn.springboot.jpashop.domain.item.Item;
import inflearn.springboot.jpashop.exception.NotEnoughStockException;
import inflearn.springboot.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book item = createBook("JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }



    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCLE");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그 만큼 재고가 증가해야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("JPA", 10000, 10);

        int orderCount = 11;

        //when
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));

        //then

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book item = new Book();
        item.setName(name); /* ctrl + alt + p -> 메서드 내에 파라미터 추출 */
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Kong");
        member.setAddress(new Address("산본", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}