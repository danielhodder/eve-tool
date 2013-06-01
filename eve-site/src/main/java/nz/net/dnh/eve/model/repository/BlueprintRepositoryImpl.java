package nz.net.dnh.eve.model.repository;

import javax.persistence.PersistenceContext;

import nz.net.dnh.eve.model.domain.Blueprint;

import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.stereotype.Service;

@Service
public class BlueprintRepositoryImpl implements BlueprintRepositoryCustom {

	@PersistenceContext
	private HibernateEntityManager entityManager;

	@Override
	public Blueprint refresh(final Blueprint blueprint) {
		final Session session = this.entityManager.getSession();
		if (!session.contains(blueprint))
			session.refresh(blueprint);
		return blueprint;
	}

}
