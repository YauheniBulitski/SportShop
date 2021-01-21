package root.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of="id")
@ToString(exclude = {"users","type","maker","orders_item"})
@Builder
@Getter
@Setter
@Access(AccessType.FIELD)
@Table(name = "product")
public class Product extends BaseEntity<Long> {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "count",nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "maker_id")
    private Maker maker;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrdersItem> orders_item = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "users_product",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users=new ArrayList<>();
}
