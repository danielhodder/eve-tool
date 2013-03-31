package nz.net.dnh.eve.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Account save(Account account) {
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		return account;
	}

	@Override
	public Account findByUsername(String username) {
		return null;
	}
}
