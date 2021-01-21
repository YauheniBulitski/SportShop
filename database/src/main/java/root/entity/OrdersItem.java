package root.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"product","order"})
@EqualsAndHashCode (of = "id")
@Entity
@Builder
@Data
@Getter
@Table(name = "orders_item")
public class OrdersItem extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "orders_id", nullable=false)
    private Orders order;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

}
