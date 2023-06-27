package numble.deepdive.performanceticketingservice.global.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * lock의 이름
     */
    String key();

    /**
     * 락을 대기하는 시간 (default: 10초)
     * <br>
     * 락을 보유하지 못한 쓰레드가 락 획득을 위해 waitTime 만큼 대기한다
     */
    long waitTime() default 10L;

    /**
     * 락 점유 시간 (default: 3초)
     * <br>
     * 락을 획득한 쓰레드가 leaseTime 이 지나면 락을 해제한다
     */
    long leaseTime() default 3L;

    /**
     * lock을 대기하고 점유하는 시간 단위
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
