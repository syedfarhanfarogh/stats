package networking.platform.stats.rest.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import networking.platform.stats.Provider;
import networking.platform.stats.rest.data.Message;
import networking.platform.stats.rest.data.MessageQueue;

@RestController
@RequestMapping("/flows")
public class FlowsController {
    private Provider provider = new Provider();
	private Validator validator = new Validator();

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> write(
			@RequestBody List<Message> messages) throws Exception {
		
		if(messages == null) {
			return ResponseEntity.badRequest().build();
		}
		
		MessageQueue queue = provider.getMessageQueue();
		for(Message m : messages) {
			if(!validator.valid(m))
				continue;
			queue.add(m);
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Message>> read(
			@RequestParam int hour){
		List<Message> list = provider.getMessageStore().read(hour);
		return ResponseEntity.ok()
	      	      .body(list);
	}
}
