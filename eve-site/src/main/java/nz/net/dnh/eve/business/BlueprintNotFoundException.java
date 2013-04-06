package nz.net.dnh.eve.business;

public class BlueprintNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final BlueprintReference blueprintReference;

	public BlueprintNotFoundException(BlueprintReference blueprintReference) {
		super("Could not find the blueprint " + blueprintReference);
		this.blueprintReference = blueprintReference;
	}

	public BlueprintReference getBlueprintReference() {
		return this.blueprintReference;
	}
}
