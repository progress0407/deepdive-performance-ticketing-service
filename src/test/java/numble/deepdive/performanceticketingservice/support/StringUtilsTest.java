package numble.deepdive.performanceticketingservice.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void CamelCase를_snake_case로_바꾸어주는지_확인() {
        // given
        String Camel_Case_문자열 = "HelloWorld";

        // when
        String 바뀐_문자열 = StringUtils.camelToSnake(Camel_Case_문자열);

        // then
        assertThat(바뀐_문자열).isEqualTo("hello_world");
    }

}