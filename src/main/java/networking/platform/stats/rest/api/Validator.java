package networking.platform.stats.rest.api;

import networking.platform.stats.rest.data.Message;

public class Validator {

	public boolean valid(Message message) {
		if(message == null)return false;
		if(message.src_app ==null || message.src_app.isBlank())return false;
		if(message.dest_app ==null || message.dest_app.isBlank())return false;
		if(message.vpc_id ==null || message.vpc_id.isBlank())return false;
		if(message.hour <0 || message.hour > 23)return false;
		return true;
	}
}
