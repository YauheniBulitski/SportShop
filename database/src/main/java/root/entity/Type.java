package root.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Getter
@ToString(exclude = {"products","category"})
@Table(name = "type")
public class Type extends BaseEntity<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "type",fetch=FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
