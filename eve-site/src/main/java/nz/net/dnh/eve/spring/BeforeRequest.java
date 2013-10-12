package nz.net.dnh.eve.spring;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks that a method should be executed before a request is executed.
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface BeforeRequest {
	/**
	 * The priority of this method. Lower priorities are processed first. Methods with the same priority are processed
	 * in an unspecified order
	 */
	int priority() default 0;
}
