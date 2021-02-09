package root.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"products","country"})
@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Table(name = "maker")
public class Maker extends BaseEntity<Integer> {

    @Column(name = "name",nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "maker",fetch=FetchType.EAGER)
    private List<Product> products;
}
