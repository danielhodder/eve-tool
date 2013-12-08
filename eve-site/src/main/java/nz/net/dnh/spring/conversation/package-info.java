/**
 * Contains classes relating to using conversation scoped beans during a web request. This pattern is similar to that of Spring WebFlow but is done
 * using the Spring Beans scoping API.
 * <p>
 * To use conversation scoped beans first add the {@link nz.net.dnh.spring.conversation.ConversationInterceptor} to your Spring MVC Intercepter list.
 * Then make sure to pass the return value of {@link nz.net.dnh.spring.conversation.CurrentConversationIDHolder#getConversationID()} back as a parameter
 * to the next request. The name of the parameter that is used is configured in {@link nz.net.dnh.spring.conversation.ConversationInterceptor#setConversationParamaterName(String)}.
 * The default parameter name is {@code conversationID}.
 * 
 * @author Daniel Hodder (danielh)
 *
 */
package nz.net.dnh.spring.conversation;