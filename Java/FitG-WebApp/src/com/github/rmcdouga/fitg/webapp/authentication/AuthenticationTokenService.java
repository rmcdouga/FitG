package com.github.rmcdouga.fitg.webapp.authentication;

import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.security.SignatureException;

public class AuthenticationTokenService {
	
	private static AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService();
	
	public static AuthenticationTokenService getInstance() { return authenticationTokenService; }
	
    /**
     * How long the token is valid for (in seconds).
     */
    private Long validFor = 1 /* hours */ * 60 * 60L;

    /**
     * How many times the token can be refreshed.
     */
    private Integer refreshLimit = 1;


	AuthenticationTokenSettings settings = new AuthenticationTokenSettings();

	
    public String issueToken(String username, Set<Authority> authorities) {
    	String id = generateTokenIdentifier();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(id)
                .withUsername(username)
                .withAuthorities(authorities)
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(0)
                .withRefreshLimit(refreshLimit)
                .build();

        return issueToken(authenticationTokenDetails);
    }

	private String issueToken(AuthenticationTokenDetails authenticationTokenDetails) {
		
		return Jwts.builder()
                .setId(authenticationTokenDetails.getId())
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(authenticationTokenDetails.getUsername())
                .setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authenticationTokenDetails.getAuthorities())
                .claim(settings.getRefreshCountClaimName(), authenticationTokenDetails.getRefreshCount())
                .claim(settings.getRefreshLimitClaimName(), authenticationTokenDetails.getRefreshLimit())
                .signWith(settings.getKey(), settings.getSigningAlgorithm())
                .compact();
	}

    /**
     * Parse and validate the token.
     *
     * @param token
     * @return
     * @throws InvalidAuthenticationTokenException 
     */
    public AuthenticationTokenDetails parseToken(String token) throws InvalidAuthenticationTokenException {
        try {

            Claims claims = Jwts.parser()
            		.setSigningKey(settings.getKey())
                    .requireAudience(settings.getAudience())
                    .setAllowedClockSkewSeconds(settings.getClockSkew())
                    .parseClaimsJws(token)
                    .getBody();

            return new AuthenticationTokenDetails.Builder()
                    .withId(extractTokenIdFromClaims(claims))
                    .withUsername(extractUsernameFromClaims(claims))
                    .withAuthorities(extractAuthoritiesFromClaims(claims))
                    .withIssuedDate(extractIssuedDateFromClaims(claims))
                    .withExpirationDate(extractExpirationDateFromClaims(claims))
                    .withRefreshCount(extractRefreshCountFromClaims(claims))
                    .withRefreshLimit(extractRefreshLimitFromClaims(claims))
                    .build();

        } catch (UnsupportedJwtException |  MalformedJwtException | IllegalArgumentException  |  SignatureException  e) {
            throw new InvalidAuthenticationTokenException("Invalid token", e);
        } catch (ExpiredJwtException e) {
            throw new InvalidAuthenticationTokenException("Expired token", e);
        } catch (InvalidClaimException e) {
            throw new InvalidAuthenticationTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new InvalidAuthenticationTokenException("Invalid token", e);
        }
    }

    /**
     * Refresh a token.
     *
     * @param currentTokenDetails
     * @return
     */
    public String refreshToken(AuthenticationTokenDetails currentTokenDetails) {

        if (!currentTokenDetails.isEligibleForRefreshment()) {
            throw new AuthenticationTokenRefreshmentException("This token cannot be refreshed");
        }

        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(currentTokenDetails.getId()) // Reuse the same id
                .withUsername(currentTokenDetails.getUsername())
                .withAuthorities(currentTokenDetails.getAuthorities())
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(currentTokenDetails.getRefreshCount() + 1)
                .withRefreshLimit(refreshLimit)
                .build();

        return issueToken(newTokenDetails);
    }

    public AuthenticationStatus getTokenStatus(AuthenticationTokenDetails currentTokenDetails) {
    	// TODO:  Need to check on token expiry date, etc.
    	// Currently assumes that token is always valid.
    	return AuthenticationStatus.TOKEN_VALID;
    }
    
    /**
     * Calculate the expiration date for a token.
     *
     * @param issuedDate
     * @return
     */
    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(validFor);
    }

    /**
     * Generate a token identifier.
     *
     * @return
     */
    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Extract the token identifier from the token claims.
     *
     * @param claims
     * @return Identifier of the JWT token
     */
    private String extractTokenIdFromClaims(@NotNull Claims claims) {
        return (String) claims.get(Claims.ID);
    }

    /**
     * Extract the username from the token claims.
     *
     * @param claims
     * @return Username from the JWT token
     */
    private String extractUsernameFromClaims(@NotNull Claims claims) {
        return claims.getSubject();
    }

    /**
     * Extract the user authorities from the token claims.
     *
     * @param claims
     * @return User authorities from the JWT token
     */
    private Set<Authority> extractAuthoritiesFromClaims(@NotNull Claims claims) {
        List<String> rolesAsString = (List<String>) claims.getOrDefault(settings.getAuthoritiesClaimName(), new ArrayList<>());
        return rolesAsString.stream().map(Authority::valueOf).collect(Collectors.toSet());
    }

    /**
     * Extract the issued date from the token claims.
     *
     * @param claims
     * @return Issued date of the JWT token
     */
    private ZonedDateTime extractIssuedDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Extract the expiration date from the token claims.
     *
     * @param claims
     * @return Expiration date of the JWT token
     */
    private ZonedDateTime extractExpirationDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Extract the refresh count from the token claims.
     *
     * @param claims
     * @return Refresh count from the JWT token
     */
    private int extractRefreshCountFromClaims(@NotNull Claims claims) {
        return (int) claims.get(settings.getRefreshCountClaimName());
    }

    /**
     * Extract the refresh limit from the token claims.
     *
     * @param claims
     * @return Refresh limit from the JWT token
     */
    private int extractRefreshLimitFromClaims(@NotNull Claims claims) {
        return (int) claims.get(settings.getRefreshLimitClaimName());
    }

    private static class AuthenticationTokenSettings {

        /**
         * Signing Algorithm to be used.
         */
		private static SignatureAlgorithm signingAlgorithm = SignatureAlgorithm.HS512;

        /**
         * Key for signing and verifying the token signature.
         * This will generate a new key each time the application is started so sessions will not
         * survive a restart (which is OK at this time).
         */
        private static Key generatedKey = MacProvider.generateKey(signingAlgorithm);

        /**
         * Allowed clock skew for verifying the token signature (in seconds).
         */
        private static Long clockSkew = 1L;

        /**
         * Identifies the recipients that the JWT token is intended for.
         */
        private static String audience = "com.github.rmcdouga.fitg.webapp";

        /**
         * Identifies the JWT token issuer.
         */
        private static String issuer = "com.github.rmcdouga.fitg.webapp";

        /**
         * JWT claim for the authorities.
         */
        private static String authoritiesClaimName = "authorities";

        /**
         * JWT claim for the token refreshment count.
         */
        private static String refreshCountClaimName = "refreshCount";

        /**
         * JWT claim for the maximum times that a token can be refreshed.
         */
        private static String refreshLimitClaimName = "refreshLimit";

        public Key getKey() {
            return generatedKey;
        }

        public SignatureAlgorithm getSigningAlgorithm() {
			return signingAlgorithm;
		}

		public Long getClockSkew() {
            return clockSkew;
        }

        public String getAudience() {
            return audience;
        }

        public String getIssuer() {
            return issuer;
        }

        public String getAuthoritiesClaimName() {
            return authoritiesClaimName;
        }

        public String getRefreshCountClaimName() {
            return refreshCountClaimName;
        }

        public String getRefreshLimitClaimName() {
            return refreshLimitClaimName;
        }
    }

}
