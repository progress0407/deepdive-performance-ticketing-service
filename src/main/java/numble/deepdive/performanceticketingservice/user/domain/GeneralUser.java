package numble.deepdive.performanceticketingservice.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "general_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("GENERAL")
@ToString(callSuper = true)
public class GeneralUser extends AbstractUser {

    public GeneralUser(String name, String email, String password) {
        super(name, email, password);
    }
}
