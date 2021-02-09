package root.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "types")
@EqualsAndHashCode(of = "id")
@Entity
@Builder
@Data
@Table(name = "category")
public class Category extends BaseEntity<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category",fetch=FetchType.EAGER)
    private List<Type> types;
}
