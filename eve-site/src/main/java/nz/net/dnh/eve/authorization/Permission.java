package nz.net.dnh.eve.authorization;

/**
 * A generic interface for permission. A permission is defined to be static. That is given the same inputs it will
 * result in the same output.
 * 
 * @author Daniel Hodder (danielh)
 */
public interface Permission {
	/**
	 * Is this permission true in the current context.
	 * 
	 * @return True if the permission is true for the current context.
	 */
	boolean isTrue();
}