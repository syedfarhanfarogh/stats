package networking.platform.stats.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import networking.platform.stats.rest.data.Message;

public class Bucket {
	private final ConcurrentHashMap<StoreKey, StoreValue> map = new ConcurrentHashMap<StoreKey, StoreValue>();
	private int hour ;
	
	public Bucket(int hour) {
		this.hour =hour;
	}
	
	public void update(Message message) {
		StoreKey key = getStoreKey(message);
		StoreValue value = map.computeIfAbsent(key, k -> new StoreValue());
		value.update(message.bytes_tx, message.bytes_rx);
	}

	public List<Message> values() {
		List<Message> results = new ArrayList<Message>();
		if(map.isEmpty())
			return results;
		
		int[] buffer = new int[2];
		for(StoreKey key : map.keySet()) {
			StoreValue v = map.get(key);
			if(!v.get(buffer))
				continue;
			
			Message msg = new Message();
			msg.src_app = key.src_app;
			msg.dest_app = key.dest_app;
			msg.vpc_id = key.vpc_id;
			msg.bytes_tx = buffer[0];
			msg.bytes_rx = buffer[1];
			msg.hour = hour;
			results.add(msg);
		}
		
		return results;
	}

	private StoreKey getStoreKey(Message msg) {
		return new StoreKey(msg.src_app, msg.dest_app, msg.vpc_id);
	}

	private class StoreKey{
		public String src_app;
		public String dest_app;
		public String vpc_id;
		
		public StoreKey(String src_app, String dest_app, String vpc_id) {
			this.src_app = src_app;
			this.dest_app = dest_app;
			this.vpc_id = vpc_id;
		}
		
	    @Override
	    public boolean equals(Object o) {
	        if (o == this)
	            return true;
	        if (!(o instanceof StoreKey))
	            return false;
	        StoreKey other = (StoreKey) o;
	        return src_app.equals(other.src_app) && dest_app.equals(other.dest_app) && vpc_id.equals(other.vpc_id);
	    }
		
		@Override
		public final int hashCode() {
			return src_app.hashCode() ^ dest_app.hashCode() ^ vpc_id.hashCode();
		}
	}
	
	private class StoreValue{
		private boolean init = false;
		private int bytes_tx = 0;
		private int bytes_rx = 0;
		
		public synchronized boolean get(int[] buffer) {
			if(!init)
				return false;
			
			buffer[0] = bytes_tx;
			buffer[1] = bytes_rx;
			return true;
		}
		
		public synchronized void update(int bytes_tx, int bytes_rx) {
			init = true;
			this.bytes_tx += bytes_tx;
			this.bytes_rx += bytes_rx;
		}
	}
}