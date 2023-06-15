package numble.deepdive.performanceticketingservice.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "business_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("BUSINESS")
@Getter
@ToString(callSuper = true)
public class BusinessUser extends AbstractUser {

    private String businessLicense; // 사업자 등록번호

    public BusinessUser(String name, String email, String password, String businessLicense) {
        super(name, email, password);
        this.businessLicense = businessLicense;
    }
}
