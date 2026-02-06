@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAuditAspect {

    private final AuditService auditService;

    @AfterReturning("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void log(JoinPoint joinPoint) {
        auditService.logEvent(
            "SECURITY_ACTION",
            joinPoint.getSignature().getName(),
            TenantContext.getTenant()
        );
    }
}
