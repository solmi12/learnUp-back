package pfe.LearnUp.Configurations;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pfe.LearnUp.Entity.Role;
import io.jsonwebtoken.Claims;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenUtil {

    @Autowired
    UserRepository userRepository;

    public static final java.lang.String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public boolean isTokenValid(String token, UserDetails userDetails){

        String username= getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public java.lang.String getUsername(java.lang.String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }


    private boolean isTokenExpired(java.lang.String token) {
        Date expiration=getClaims(token).getExpiration();
        return expiration.before(new Date());


    }

    private Claims getClaims(java.lang.String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            claims=null;
        }
        return claims;
    }



    public java.lang.String generateToken(java.lang.String userName) {


        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private java.lang.String createToken(Map<java.lang.String, Object> claims, java.lang.String userName) {
        User user=userRepository.findByEmail(userName);
        Role role=user.getRole();
        claims.put("role",role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}