package root.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "role")
public class Role extends BaseEntity<Short> {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role")
    List<User> users = new ArrayList<>();
}
