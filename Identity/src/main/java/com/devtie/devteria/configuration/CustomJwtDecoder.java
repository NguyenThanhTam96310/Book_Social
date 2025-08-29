package com.devtie.devteria.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.SignedJWT;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt_secret}")
    private String signerKey;

    // @Autowired
    // private AuthencationService authenticationService;

    // private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        // kt xem token còn hiệu lực k
        // try {
        // var response = authenticationService.introspect(
        // IntrospectRequest.builder().token(token).build());

        // if (!response.isValid()) throw new JwtException("Token invalid");
        // } catch (JOSEException | ParseException e) {
        // throw new JwtException(e.getMessage());
        // }
        // Xác thực token vào build
        // if (Objects.isNull(nimbusJwtDecoder)) {
        // SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),
        // "HS512");
        // nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
        // .macAlgorithm(MacAlgorithm.HS512)
        // .build();
        // }

        // return nimbusJwtDecoder.decode(token);
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return new Jwt(
                    token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims());
        } catch (Exception e) {
            throw new JwtException("Invalid token: " + e.getMessage());
        }
    }
}
