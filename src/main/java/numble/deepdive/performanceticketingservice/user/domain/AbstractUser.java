package numble.deepdive.performanceticketingservice.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public abstract class AbstractUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    public AbstractUser(String name, String email, String password) {        this.name = name;
        this.email = email;
        this.password = password;
    }
}
