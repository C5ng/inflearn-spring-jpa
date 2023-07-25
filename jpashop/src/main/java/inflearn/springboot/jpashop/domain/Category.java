package inflearn.springboot.jpashop.domain;

import inflearn.springboot.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    /* 객체는 컬렉션이 있어 다대다가 가능하지만, 관계형 DB는 컬렉션이 없으니 일대다 다대일로 풀어내는 중간 테이블이 존재해야 한다. 즉 중간 테이블과 Join */
    /* 실전에선 사용 X 테이블 컬럼 추가가 안 된다. */
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
