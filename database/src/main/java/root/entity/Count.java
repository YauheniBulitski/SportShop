package root.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Data
@Table(name = "count")
@ToString(exclude = "product")
public class Count extends BaseEntity<Long> {

    @Column(name = "count")
    private Integer count;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
