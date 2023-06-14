package numble.deepdive.performanceticketingservice.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DiscriminatorValue("B")
@Getter
@ToString(callSuper = true)
public class BusinessUser extends AbstractUser {

    private String businessLicense; // 사업자 등록번호

    private String type; // 공연장 관리자 or 공연 관리자

    public BusinessUser(String name, String email, String password, String businessLicense, String type) {
        super(name, email, password);
        this.businessLicense = businessLicense;
        this.type = type;
    }
}
