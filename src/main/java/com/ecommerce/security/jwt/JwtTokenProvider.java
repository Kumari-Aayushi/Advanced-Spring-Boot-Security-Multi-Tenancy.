@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    void init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String generateToken(String username, String role, String tenant) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("tenant", tenant)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 900000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }
}
