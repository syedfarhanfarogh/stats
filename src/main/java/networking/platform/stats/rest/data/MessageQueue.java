package networking.platform.stats.rest.data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import networking.platform.stats.Provider;
import networking.platform.stats.persistence.MessageStore;

public class MessageQueue {

	private static Object _lock = new Object();
	private static MessageQueue _singleton;

	private MessageStore messageStore;
	private BlockingQueue<Message>[] queues;
	private Thread[] consumers;
	private final int partitions = 24;
	
	public static MessageQueue getDefaultInstance() {
		if(_singleton != null)
			return _singleton;
		
		synchronized (_lock) {
			if(_singleton !=null)
				return _singleton;
			
			_singleton = new MessageQueue();
			return _singleton;
		}		
	}
	
	private MessageQueue() {
		messageStore = new Provider().getMessageStore();
		queues = new BlockingQueue[partitions];
		consumers = new Thread[partitions];
		
		for(int i =0; i<partitions; i++) {
			queues[i] = new LinkedBlockingDeque<Message>();
			consumers[i] = new Thread(new Worker(queues[i], messageStore));
			consumers[i].start();
		}
	}
	
	
	public void add(Message message) {
		queues[message.hour].offer(message);
	}
}
