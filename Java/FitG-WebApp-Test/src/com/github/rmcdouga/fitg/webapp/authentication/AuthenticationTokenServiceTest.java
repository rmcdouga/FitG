package com.github.rmcdouga.fitg.webapp.authentication;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.EnumSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AuthenticationTokenServiceTest {

	@Test
	void testIssueToken() throws InterruptedException, InvalidAuthenticationTokenException {
		String username = "username";
		EnumSet<Authority> authoritySet = EnumSet.of(Authority.USER);

		AuthenticationTokenService service = new AuthenticationTokenService();
		String token = service.issueToken(username, authoritySet);
		System.out.println("Token='" + token + "'.");
		AuthenticationTokenDetails parsedToken = service.parseToken(token);
		
		assertEquals(username, parsedToken.getUsername(), "Expected username to be the retained.");
		assertEquals(authoritySet, parsedToken.getAuthorities(), "Expected authorities to be the retained.");
		assertEquals(0, parsedToken.getRefreshCount(), "Expected refreshcount to be the 0.");
		assertEquals(1, parsedToken.getRefreshLimit(), "Expected refreshlimit to be the 1.");

		ZonedDateTime expirationDate = parsedToken.getExpirationDate();
		System.out.println("Expiration Date='" + expirationDate);
		System.out.println("Id='" + parsedToken.getId());
		ZonedDateTime issuedDate = parsedToken.getIssuedDate();
		System.out.println("Issued Date='" + issuedDate);
		
		Thread.sleep(2);
		
		String refreshedToken = service.refreshToken(parsedToken);
		AuthenticationTokenDetails parsedRefreshToken = service.parseToken(refreshedToken);

		assertEquals(username, parsedRefreshToken.getUsername(), "Expected username to be the retained.");
		assertEquals(authoritySet, parsedRefreshToken.getAuthorities(), "Expected authorities to be the retained.");
		assertEquals(1, parsedRefreshToken.getRefreshCount(), "Expected refreshcount to be the 1.");
		assertEquals(1, parsedRefreshToken.getRefreshLimit(), "Expected refreshlimit to be the 1.");

		System.out.println("Expiration Date='" + parsedRefreshToken.getExpirationDate());
		System.out.println("Id='" + parsedRefreshToken.getId());
		System.out.println("Issued Date='" + parsedRefreshToken.getIssuedDate());

	}

	@Disabled
	void testParseToken() {
		fail("Not yet implemented");
	}

	@Disabled
	void testRefreshToken() {
		fail("Not yet implemented");
	}

}
