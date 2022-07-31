package networking.platform.stats.rest.data;

import java.util.concurrent.BlockingQueue;

import networking.platform.stats.persistence.MessageStore;

public class Worker implements Runnable {

	private MessageStore store;
    private BlockingQueue<Message> queue;
    
	public Worker(BlockingQueue<Message> queue, MessageStore store)
	{
		this.store = store;
		this.queue = queue;
	}
	
	public void run() {
        try {
            while (true) {
            	Message msg = queue.take();
            	this.store.save(msg);
            }
        }
        catch (InterruptedException e) {
        }
   }
}
