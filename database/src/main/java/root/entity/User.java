package root.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Builder
@Data
@ToString(exclude = {"orders","products"})
@Table(name="users")
public class User extends BaseEntity<Long> {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @ManyToMany(cascade =CascadeType.REMOVE,fetch=FetchType.EAGER)
    @JoinTable(name = "users_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

}
