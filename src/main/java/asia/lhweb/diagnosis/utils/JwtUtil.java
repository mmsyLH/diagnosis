package asia.lhweb.diagnosis.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    // 使用固定的密钥
    public static final Key JWT_SECRET_KEY = Keys.hmacShaKeyFor("ECardECardECardECardECardECardECardECardECardECardECardECardECard".getBytes());

    public static String createJWT(long ttlMillis, Map<String, Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long expMillis = System.currentTimeMillis() + ttlMillis;

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(JWT_SECRET_KEY, signatureAlgorithm)
                .setExpiration(new Date(expMillis));

        return builder.compact();
    }

    public static Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        // 测试创建和解析JWT
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("id", 123);
        String jwt = JwtUtil.createJWT(100000, stringObjectHashMap);
        System.out.println(jwt);
        // 解密
        System.out.println(JwtUtil.parseJWT(jwt));
    }
}
