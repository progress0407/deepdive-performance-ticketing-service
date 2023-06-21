package numble.deepdive.performanceticketingservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerformanceSpringBootTest extends AcceptanceTest {

    String 일반_유저_토큰;
    String 사업자_토큰;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        일반_회원가입();
        사업자_회원가입();

        일반_유저_토큰 = 일반_유저_로그인_후_토큰_반환();
        사업자_토큰 = 사업자_로그인_후_토큰_반환();
    }

    @Test
    void 사업자는_공연_예매를_할수없다() {
        // given

        // when

        // then

    }

    @Test
    void 일반인은_공연_예매가_가능하다() {
        // given

        // when

        // then

    }
}