package root.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(of = "id")
@Getter
@Builder
@Entity
@Table(name = "role")
public class Role extends BaseEntity<Short> {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role")
    List<User> users;
}
