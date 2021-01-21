package root.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "maker")
@EqualsAndHashCode(of="id")
@Entity
@Builder
@Table(name = "country")
public class Country extends BaseEntity<Integer> {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country")
   List<Maker> makers = new ArrayList<>();
}
