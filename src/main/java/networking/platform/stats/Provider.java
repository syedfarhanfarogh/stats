package networking.platform.stats;

import networking.platform.stats.persistence.InMemoryMessageStore;
import networking.platform.stats.persistence.MessageStore;
import networking.platform.stats.rest.data.MessageQueue;

public class Provider {

	public MessageQueue getMessageQueue() {
		return MessageQueue.getDefaultInstance();
	}
	
	public MessageStore getMessageStore() {
		return InMemoryMessageStore.getDefaultInstance();
	}
}
