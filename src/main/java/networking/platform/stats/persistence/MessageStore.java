package networking.platform.stats.persistence;

import java.util.List;

import networking.platform.stats.rest.data.Message;

public interface MessageStore {
	public void save(Message message);
	public List<Message> read(int hour);
}
