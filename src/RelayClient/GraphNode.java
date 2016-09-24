package RelayClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphNode {
	
	// Variables
	// Unique Id
	private String uid;
	// Rank from server
	private int rank;
	// Last update from server 
	private Date timeStmp;
	// KDegree of sharing graph
	private int kDegree;
	// Counts handshakes between nodes 
	private List<Date> handShakeCounter;
	// private graph
	protected NGraph graph;
	// QueueIn
	private List<String> queueIn;
	// QueueOut
	private List<String> queueOut;
	// visited flag : 0 - not visited , 1 - visited
	private int visitedFlag;
		
		
	// To create a copy of existing node in a graph that not contains uid
	public GraphNode(String uid) {
		this.uid = uid;
		this.rank = -1;
		this.timeStmp = new Date(1900,1,1);
		this.kDegree = -1;
		this.handShakeCounter = new ArrayList<Date>();
		this.queueIn = new ArrayList<>();
		this.queueOut = new ArrayList<>();
		visitedFlag = 0;
	}
	
	// Getters and Setter
	public List<String> getQueueIn(){
		return this.queueIn;
	}
	
	public void setQueueIn(List<String> list){
		this.queueIn = list;
	}
	
	public List<String> getQueueOut(){
		return this.queueOut;
	}
	
	public void setQueueOut(List<String> list){
		this.queueIn = list;
	}
	
	public String getUid() {
		return this.uid;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public Date getTimeStmp() {
		return this.timeStmp;
	}
	
	public void setTimeStmp(Date date) {
		this.timeStmp = date;
	}
	
	public int getKDegree() {
		return this.kDegree;
	}
	
	public void setKDegree(int kDegree) {
		this.kDegree = kDegree;
	}
	
	public int getHandShakeCount() {
		return this.handShakeCounter.size();
	}
	
	public List<Date> getHandShakeCounter() {
		return this.handShakeCounter;
	}
	
	public void setFlag(int flag) {
		this.visitedFlag = flag;
	}
	
	public int getFlag() {
		return this.visitedFlag;
	}
	
	// update HandShake Counter
	public void updateHandShake(){
		this.handShakeCounter.add(new Date());
	}
	
	// Show node's Metadata
	public String showMetaData(){
		StringBuilder sMetaData = new StringBuilder();
		sMetaData.append("node "+this.toString()+" metadata: \n");
		sMetaData.append("rank : "+this.getRank()+"\n");
		sMetaData.append("TimeStmp : " + this.getTimeStmp() +"\n");
		sMetaData.append("KDegree : " +this.getKDegree() +"\n");
		sMetaData.append("handShakes :" + this.getHandShakeCount());
		return sMetaData.toString();
	}
	
	
	// get Packet from queueIn by index
	public String getPacketUidFromQueueIn(int index){
		if (index >= this.queueIn.size() || index == -1)
			return null;
		return this.queueIn.get(index);
	}
	
	// add Packet to queueIn
	public void addPacketUidtoQueuIn(String pUid){
		this.queueIn.add(pUid);
	}
	
	// check if PacketUid exsit in queueIn
	public boolean checkPacketUidExsitInQueueIn(String pUid){
		return this.queueIn.contains(pUid);
	}
	
	// get Packet from queueIn by pUid
	public String getPacketUidFromQueueIn(String pUid){
		return getPacketUidFromQueueIn(this.queueIn.indexOf(pUid));
	}
	
	// get Packet from queueOut by index
	public String getPacketUidFromQueueOut(int index){
		if (index >= this.queueOut.size() || index == -1)
			return null;
		return this.queueOut.get(index);
	}
		
	// add Packet to queueOut
	public void addPacketUidtoQueuOut(String pUid){
		this.queueOut.add(pUid);
	}
		
	// check if PacketUid exsit in queueOut
	public boolean checkPacketUidExsitInQueueOut(String pUid){
		return this.queueOut.contains(pUid);
	}
	
	// get Packet from queueIn by pUid
	public String getPacketUidFromQueueOut(String pUid){
		return getPacketUidFromQueueOut(this.queueOut.indexOf(pUid));
	}
	
	// To string
	public String toString() {
		return "(" + this.uid + ")";
	}

}
