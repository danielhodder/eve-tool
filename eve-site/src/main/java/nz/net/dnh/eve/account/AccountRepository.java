package nz.net.dnh.eve.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountRepository extends AccountRepositoryCustom,
		CrudRepository<Account, Long> {
	public Account findByUsername(String username);
}
