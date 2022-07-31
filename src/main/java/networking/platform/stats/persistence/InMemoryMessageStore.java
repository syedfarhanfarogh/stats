package networking.platform.stats.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import networking.platform.stats.rest.data.Message;

public class InMemoryMessageStore implements MessageStore {

	private static Object _lock = new Object();
	private static InMemoryMessageStore _singleton;
	private ConcurrentHashMap<Integer, Bucket> map = new ConcurrentHashMap<Integer, Bucket>();
	
	public static InMemoryMessageStore getDefaultInstance() {
		if(_singleton != null)
			return _singleton;
		
		synchronized (_lock) {
			if(_singleton !=null)
				return _singleton;
			
			_singleton = new InMemoryMessageStore();
			return _singleton;
		}		
	}

	public void update(Message message) {
		Bucket bucket = map.computeIfAbsent(message.hour, k -> new Bucket(message.hour));
		bucket.update(message);
	}

	public List<Message> read(int hour) {
		Bucket bucket = map.get(hour);
		if(bucket == null)
			return new ArrayList<Message>();
		
		return bucket.values();
	}
}
