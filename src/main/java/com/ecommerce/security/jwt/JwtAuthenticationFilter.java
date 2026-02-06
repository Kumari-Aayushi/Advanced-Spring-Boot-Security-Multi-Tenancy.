@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            Claims claims = tokenProvider.parse(header.substring(7));

            List<GrantedAuthority> auth =
                    List.of(new SimpleGrantedAuthority(claims.get("role").toString()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(), null, auth);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            TenantContext.setTenant(claims.get("tenant").toString());
        }

        filterChain.doFilter(request, response);
    }
}
