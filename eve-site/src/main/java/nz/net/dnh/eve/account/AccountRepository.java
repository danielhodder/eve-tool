package nz.net.dnh.eve.account;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountRepository extends AccountRepositoryCustom {
	public Account findByUsername(String username);
}
