package networking.platform.stats.rest.data;

public class Message {
	public String src_app;
	public String dest_app;
	public String vpc_id;
	public int bytes_tx;
	public int bytes_rx;
	public int hour;
	
	public String getSrc_app() {
		return src_app;
	}
	public void setSrc_app(String src_app) {
		this.src_app = src_app;
	}
	public String getDest_app() {
		return dest_app;
	}
	public void setDest_app(String dest_app) {
		this.dest_app = dest_app;
	}
	public String getVpc_id() {
		return vpc_id;
	}
	public void setVpc_id(String vpc_id) {
		this.vpc_id = vpc_id;
	}
	public int getBytes_tx() {
		return bytes_tx;
	}
	public void setBytes_tx(int bytes_tx) {
		this.bytes_tx = bytes_tx;
	}
	public int getBytes_rx() {
		return bytes_rx;
	}
	public void setBytes_rx(int bytes_rx) {
		this.bytes_rx = bytes_rx;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
}
