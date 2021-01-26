package root.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
