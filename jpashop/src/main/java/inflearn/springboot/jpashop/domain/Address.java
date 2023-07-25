package inflearn.springboot.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable /* 내장타입, 테이블에서 하나의 객체로 사용 */
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    } /* JPA는 기본 생성자가 있어야 함, JPA 스펙상 엔티티나 임베디드 타입은 기본 생성자를 public 또는 protected로 설정해야 한다. public으로 두는 것 보다는 protected가 더 안전하다. */

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    } /* @Setter를 제거하고 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만듦 */
}
