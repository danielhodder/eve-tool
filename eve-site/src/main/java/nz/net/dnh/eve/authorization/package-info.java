/**
 * Basic authentication components that act as a drop-in module to Spring Security to replace it's woeful role-based permission
 * system. This is an module that integrates with Spring Security. Spring security provides a nice interface for authentication
 * but has a crap interface for permissions. So this is my approach and I think it is much more flexible. It is loosely based
 * on a very successful system that was implemented in a previous project.
 * 
 * This module does not provide authentication but authorization. Authentication is still done by SpringSecurity. The central
 * concept in this module is a {@link nz.net.dnh.eve.authorization.Permission Permission}.
 * 
 * @author Daniel Hodder (danielh)
 *
 */
package nz.net.dnh.eve.authorization;