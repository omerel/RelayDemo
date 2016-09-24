package RelayClient;

import java.util.Date;
import java.util.UUID;

public class Packet {
	
	// Variables
	private String uid;
	private String addressee;
	private Date dateCreated;
	// Type: 0 - txt
	private int type;
	private String msg;
	// Status: 0 - sent, 1 - arrivedServer, 2 - delivered
	private int status; 
	
	
	// Constructor cloner
	public Packet() {
		this.uid =  UUID.randomUUID().toString();
		this.status = 0;
		this.dateCreated = new Date();
	}
	
	// Constructor
	public Packet(Packet packet){
		this.uid =  packet.getUid();
		this.status = packet.getStatus();
		this.addressee = packet.getAddressee();
		this.dateCreated = packet.getDateCreated();
		this.type = packet.getType(); 
		this.msg = packet.getMsg();
		}
	
	// Getters and setters
	protected String getUid() {
		return uid;
	}

	protected String getAddressee() {
		return addressee;
	}

	protected Date getDateCreated() {
		return dateCreated;
	}

	protected int getStatus() {
		return status;
	}
	
	protected int getType() {
		return type;
	}

	protected String getMsg() {
		return msg;
	}
	
	protected void setUid(String uid) {
		this.uid = uid;
	}

	protected void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	protected void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	protected void setStatus(int status) {
		this.status = status;
	}
	
	protected void setType(int type) {
		this.type = type;
	}

	protected void setMsg(String msg) {
		this.msg = msg;
	}

	public String toString(){
		StringBuilder sPacket = new StringBuilder();
		sPacket.append("___________Message_________\n");
		sPacket.append(this.dateCreated.toString()+" \n");
		sPacket.append("To: "+this.getAddressee()+" \n");
		sPacket.append("Status: "+this.getStatus()+" \n");
		sPacket.append("msg: "+msg+"\n");
		sPacket.append("____________________________\n");
		return sPacket.toString();
	}
	public String toString2(){
		StringBuilder sPacket = new StringBuilder();
		sPacket.append("to:"+this.addressee+":"+msg);
		if (this.getStatus() == 0)
			sPacket.append("-->Sent");
		else if (this.getStatus() == 1)
			sPacket.append("-->got to Server");
		else 
			sPacket.append("-->Delivered!");
		return sPacket.toString();
	}
	
}
