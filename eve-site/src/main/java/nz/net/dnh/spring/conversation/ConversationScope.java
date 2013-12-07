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
 * A special subclass of the session scope that works for conversations. This bean is session scoped.
 * 
 * @author Daniel Hodder (danielh)
 * @see ConversationScopeConfiguration#conversationScope()
 */
public class ConversationScope implements Scope {
	private static final Logger logger = LoggerFactory.getLogger(ConversationScope.class);
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