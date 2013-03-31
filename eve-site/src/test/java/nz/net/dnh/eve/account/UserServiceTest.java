package nz.net.dnh.eve.account;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService = new UserService();

	@Mock
	private AccountRepository accountRepositoryMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldInitializeWithTwoDemoUsers() {
		// act
		this.userService.initialize();
		// assert
		verify(this.accountRepositoryMock, times(2)).save(any(Account.class));
	}

	@Test
	public void shouldThrowExceptionWhenUserNotFound() {
		// arrange
		this.thrown.expect(UsernameNotFoundException.class);
		this.thrown.expectMessage("user not found");

		when(this.accountRepositoryMock.findByUsername("user")).thenReturn(null);
		// act
		this.userService.loadUserByUsername("user");
	}

	@Test
	public void shouldReturnUserDetails() {
		// arrange
		Account demoUser = new Account("user", "demo", "ROLE_USER");
		when(this.accountRepositoryMock.findByUsername("user")).thenReturn(demoUser);

		// act
		UserDetails userDetails = this.userService.loadUserByUsername("user");

		// assert
		assertEquals(demoUser.getUsername(), userDetails.getUsername());
		assertEquals(demoUser.getPassword(), userDetails.getPassword());
		assertTrue(hasAuthority(userDetails, demoUser.getRole()));
	}

	private boolean hasAuthority(UserDetails userDetails, String role) {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}
