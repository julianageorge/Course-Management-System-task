package com.course_management_system.publicservice.security;

import com.course_management_system.publicservice.exceptions.BadRequestException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String secret;
    private final long expirationSeconds;

    public JwtService(@Value("${app.jwt.secret:${APP_JWT_SECRET:dev-secret-change-me}}") String secret,
            @Value("${app.jwt.expiration-seconds:86400}") long expirationSeconds) {
        this.secret = secret;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(String email, String role) {
        long expiresAt = Instant.now().getEpochSecond() + expirationSeconds;
        String header = base64Url("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = base64Url("{\"sub\":\"" + escape(email) + "\",\"role\":\"" + escape(role)
                + "\",\"exp\":" + expiresAt + "}");
        return header + "." + payload + "." + sign(header + "." + payload);
    }

    public String extractEmail(String token) {
        return extractStringClaim(token, "sub");
    }

    public String extractRole(String token) {
        return extractStringClaim(token, "role");
    }

    public boolean isValid(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        if (!constantTimeEquals(sign(parts[0] + "." + parts[1]), parts[2])) {
            return false;
        }
        return Instant.now().getEpochSecond() < extractLongClaim(token, "exp");
    }

    private String extractStringClaim(String token, String claim) {
        String payload = decodePayload(token);
        String marker = "\"" + claim + "\":\"";
        int start = payload.indexOf(marker);
        if (start < 0) {
            throw new BadRequestException("Invalid token");
        }
        start += marker.length();
        int end = payload.indexOf("\"", start);
        return payload.substring(start, end);
    }

    private long extractLongClaim(String token, String claim) {
        String payload = decodePayload(token);
        String marker = "\"" + claim + "\":";
        int start = payload.indexOf(marker);
        if (start < 0) {
            throw new BadRequestException("Invalid token");
        }
        start += marker.length();
        int end = payload.indexOf(",", start);
        if (end < 0) {
            end = payload.indexOf("}", start);
        }
        return Long.parseLong(payload.substring(start, end));
    }

    private String decodePayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new BadRequestException("Invalid token");
        }
        return new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new IllegalStateException("Could not sign JWT", exception);
        }
    }

    private String base64Url(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private boolean constantTimeEquals(String left, String right) {
        return java.security.MessageDigest.isEqual(left.getBytes(StandardCharsets.UTF_8),
                right.getBytes(StandardCharsets.UTF_8));
    }
}
