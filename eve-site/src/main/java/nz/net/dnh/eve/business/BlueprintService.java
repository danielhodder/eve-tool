package nz.net.dnh.eve.business;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Provides access to Blueprints
 */
@Service
public interface BlueprintService {
	public List<BlueprintSummary> listSummaries();
}
