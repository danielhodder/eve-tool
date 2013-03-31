package nz.net.dnh.eve.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountRepositoryImpl implements AccountRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Account save(Account account) {
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		this.entityManager.persist(account);
		return account;
	}
}
