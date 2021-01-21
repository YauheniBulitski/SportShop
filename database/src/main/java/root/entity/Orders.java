package root.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = "orders_item")
@Entity
@Data
@Getter
@Table(name = "orders")
public class Orders extends BaseEntity<Long> {


    @Column(name = "time_of_orders", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "type_of_payment", nullable = false)
    private String type_of_payment;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrdersItem> orders_item = new ArrayList<>();

}
