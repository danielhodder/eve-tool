/**
 * 
 */
package nz.net.dnh.spring.conversation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.Scope;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * A special scope that implements a 'conversation'. That is beans scoped to a 'conversation' are valid for the duration
 * of a session in the first instance, and then to the currently registered conversation. The conversation is defined as
 * being continued if you are still in the same session and pass the same conversation ID to the application.
 * <p>
 * This scope implements thread safety by making all methods that refer to the internal datastore synchronized. This is
 * generally acceptable since it is unlikely that a large number of sessions will have simultaneous requests through the
 * MVC.
 * <p>
 * This bean is session scoped.
 * 
 * @author Daniel Hodder (danielh)
 * @see ConversationConfiguration#conversationScope()
 */
public class ConversationScope implements Scope {
	/**
	 * The scope name for the registered scope.
	 */
	public static final String SCOPE_CONVERSATION = "conversation";

	private static final Logger logger = LoggerFactory.getLogger(ConversationScope.class);

	/**
	 * This is the internal store of beans, it is a table (read map of maps) from the conversationID ({@code String}) to
	 * beanName ({@code String}) to bean instance
	 */
	private final Table<String, String, Object> conversationToObjectMaps = HashBasedTable.create();

	@Autowired
	private CurrentConversationIDHolder currentConversationIDHolder;

	@Override
	public String getConversationId() {
		logger.trace("getting current conversation ID");

		return this.currentConversationIDHolder.getConversationID();
	}

	@Override
	public synchronized Object get(final String name, final ObjectFactory<?> objectFactory) {
		logger.trace("Getting bean of name {}", name);

		// Check if the bean already exists.
		Object beanInstance = this.conversationToObjectMaps.get(this.currentConversationIDHolder.getConversationID(), name);

		if (beanInstance == null) {
			this.conversationToObjectMaps.put(	this.currentConversationIDHolder.getConversationID(), name,
												beanInstance = objectFactory.getObject());
		}

		return beanInstance;
	}

	@Override
	public synchronized Object remove(final String name) {
		logger.trace("Removing bean of name {}", name);

		final Object removedBean = this.conversationToObjectMaps.remove(this.currentConversationIDHolder.getConversationID(), name);

		return removedBean;
	}

	@Override
	public synchronized void registerDestructionCallback(final String name, final Runnable callback) {
		logger.trace("Registering destruction callback against bean: {}", name);
	}

	@Override
	public synchronized Object resolveContextualObject(final String key) {
		logger.trace("running resolveContextualObject for {}", key);

		return null;
	}
}