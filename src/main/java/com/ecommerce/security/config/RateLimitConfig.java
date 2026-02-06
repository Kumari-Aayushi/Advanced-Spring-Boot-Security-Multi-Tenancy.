@Configuration
public class RateLimitConfig {

    @Bean
    public RateLimiter loginRateLimiter() {
        return RateLimiter.of("login",
            RateLimiterConfig.custom()
                .limitForPeriod(5)
                .limitRefreshPeriod(Duration.ofMinutes(5))
                .timeoutDuration(Duration.ZERO)
                .build());
    }
}
