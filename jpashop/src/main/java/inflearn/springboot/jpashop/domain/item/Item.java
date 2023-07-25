package inflearn.springboot.jpashop.domain.item;

import inflearn.springboot.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) /* 상속테이블 전략은 부모 클래스에서 잡아줘야 한다. */
@DiscriminatorColumn(name = "dtype") /* 하위 클래스 구분 */
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
