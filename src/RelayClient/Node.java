package RelayClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import RelayServer.SystemManager;

public class Node {
	
	// Unique Id
	private String uid;
	// private graph
	public NGraph graph;

	
	// Constructor - create a new node - signUp node from server
	public Node(SystemManager server){
		this.uid = String.valueOf((server.signUpNode()));
		createGraph(server);
		this.graph.addEdge(this.getUid(), server.cloud.getUid());
		this.graph.addEdge(this.getUid(), server.cloud.getUid());
	}
	
	// Constructor - create a new Server
		public Node(SystemManager server,String name ){
			this.uid = server.createServer("Server");
			createGraph(server);
		}

	// Create Graph- only for the main node
	private void createGraph(SystemManager server) {
		this.graph = new NGraph();
		this.graph.addGraphNode(this.uid);
		GraphNode graphNode = this.graph.graphNodes.get(this.uid);
		graphNode.setRank(server.getDefaultRank());
		graphNode.setTimeStmp(new Date());
		graphNode.setKDegree(server.getDefaultKDegree());
		this.graph.graphNodes.put(this.uid, graphNode);
	}
	
	// Getters and Setter
	
	public List<String> getQueueIn(String nUid){
		return this.graph.graphNodes.get(nUid).getQueueIn();
	}
	
	public void setQueueIn(String nUid,List<String> list){
		this.graph.graphNodes.get(nUid).setQueueIn(list);

	}
	
	public List<String> getQueueOut(String nUid){
		return this.graph.graphNodes.get(nUid).getQueueOut();
	}
	
	public void setQueueOut(String nUid,List<String> list){
		this.graph.graphNodes.get(nUid).setQueueOut(list);

	}
	
	public String getUid() {
		return this.uid;
	}
	
	public NGraph getGraph() {
		return graph;
	}
	
	public int getRank(String nUid) {
		return this.graph.graphNodes.get(nUid).getRank();
	}
	
	public void setRank(String nUid,int rank) {
		this.graph.graphNodes.get(nUid).setRank(rank);;

	}
	
	public Date getTimeStmp(String nUid) {
		return this.graph.graphNodes.get(nUid).getTimeStmp();
	}
	
	public void setTimeStmp(String nUid,Date date) {
		this.graph.graphNodes.get(nUid).setTimeStmp(date);

	}
	
	public int getkDegree(String nUid) {
		return this.graph.graphNodes.get(nUid).getKDegree();
	}
	
	public void setKDegree(int kDegree,String nUid) {
		this.graph.graphNodes.get(nUid).setKDegree(kDegree);

	}
	
	public int getHandShakeCount(String nUid) {
		return this.graph.graphNodes.get(nUid).getHandShakeCount();
	}
	
	public List<Date> getHandShakeCounter(String nUid) {
		return this.graph.graphNodes.get(nUid).getHandShakeCounter();
	}
	
	public void setFlag(String nUid,int flag) {
		this.graph.graphNodes.get(nUid).setFlag(flag);
	}
	
	public int getFlag(String nUid) {
		return this.graph.graphNodes.get(nUid).getFlag();
	}

	// update HandShake Counter
	public void updateHandShake(String nUid){
		this.graph.graphNodes.get(nUid).updateHandShake();
	}
	
	// Show node's Metadata
	public String showMetaData(String nUid){
		return this.graph.graphNodes.get(nUid).showMetaData();
	}
	
	// Send message to node . return the id of the msg
	public String sendMessage(String addressee, int type,String msg){
		Packet tempPacket = new Packet();
		tempPacket.setAddressee(addressee);
		tempPacket.setType(type);
		tempPacket.setMsg(msg);
		// add packet to packets
		this.graph.packets.put(tempPacket.getUid(), tempPacket);
		// add packet to queueOut
		GraphNode graphNode = this.graph.getGraphNode(this.uid);
		graphNode.addPacketUidtoQueuOut(tempPacket.getUid());
		return tempPacket.getUid();
	}
	
	// Get packet by pUid 
	public Packet getPacket(String pUid){
		return this.graph.packets.get(pUid);
	}
		
	// Add packet to packets
	public void addPacketToPackets(Packet packet){
		this.graph.packets.put(packet.getUid(), packet);
	}
	
	// Get packet from graphNode queueIn by index
	public Packet getPacketFromGraphNodeQueueIn(String nUid,int index){
		return this.graph.packets.get(this.graph.graphNodes.get(nUid).getPacketUidFromQueueIn(index));
	}
	
	// Get packet from graphNode queueIn by pUid
	public Packet getPacketFromGraphNodeQueueIn(String nUid, String pUid){
		return this.graph.packets.get(this.graph.graphNodes.get(nUid).getPacketUidFromQueueIn(pUid));
	}
	
	// Get packet from graphNode queueOut by index
	public Packet getPacketFromGraphNodeQueueOut(String nUid, int index){
		return this.graph.packets.get(this.graph.graphNodes.get(nUid).getPacketUidFromQueueOut(index));
	}
	
	// Get packet from graphNode queueOut by pUid
	public Packet getPacketFromGraphNodeQueueOut(String nUid, String pUid){
		return this.graph.packets.get(this.graph.graphNodes.get(nUid).getPacketUidFromQueueOut(pUid));
	}
	
	// Add packetUid to graphNode queueIn
	public void addPacketUidToQueueIn(String nUid, String pUid){
		GraphNode graphNode = this.graph.getGraphNode(nUid);
		if (!graphNode.checkPacketUidExsitInQueueIn(pUid)){
			graphNode.addPacketUidtoQueuIn(pUid);
		}
	}
	
	// Add packetUid to graphNode queueOut
	public void addPacketUidToQueueOut(String nUid, String pUid){
		GraphNode graphNode = this.graph.getGraphNode(nUid);
		if (!graphNode.checkPacketUidExsitInQueueOut(pUid)){
			graphNode.addPacketUidtoQueuOut(pUid);
		}
	}
	
	// To string
	public String toString() {
		return "(" + this.uid + ")";
	}
	
	// update Packet.msg
	public void changePacketMsg(String pUid,String msg) {
		Packet tempPacket = getPacket(pUid);
		tempPacket.setMsg(msg);
	}
	
	// Set visited flag to graphNode 
	public void setVisitedFlagToGraphNode(int flag,String nUid){
		GraphNode graphNode = this.graph.getGraphNode(nUid);
		graphNode.setFlag(flag);	
	}

	/////////////////////////////// Hand shakes methods  /////////////////////////////	
	
	// Search node in graph and add it if not exist
	public boolean searchNode(String nUid){
		if (!this.graph.isExist(nUid)){
			this.graph.addGraphNode(nUid);
			this.addPacketsToNewNodeQueueIn(nUid);
		}
		return true;
	}
	
	private void addPacketsToNewNodeQueueIn(String nUid) {
		Iterator<String> list = this.graph.packets.keySet().iterator();
		Packet packet;
		while ( list.hasNext() ){
			packet = this.getPacket(list.next());
			if (packet.getAddressee().equals(nUid)){
				this.addPacketUidToQueueIn(nUid, packet.getUid());
			}
		}
		
	}

	// Update Rank Request
	public boolean updateRankRequest(Node nodeB,String uidN){
		GraphNode graphNodeA = this.graph.getGraphNode(uidN);
		GraphNode graphNodeB = nodeB.graph.getGraphNode(uidN);
		if (graphNodeA.getTimeStmp().compareTo(graphNodeB.getTimeStmp()) < 0){
			graphNodeB.setTimeStmp(graphNodeA.getTimeStmp());
			graphNodeB.setRank(graphNodeA.getRank());
		}
		else{
			graphNodeA.setTimeStmp(graphNodeB.getTimeStmp());
			graphNodeA.setRank(graphNodeB.getRank());
		}		
		return true;
	}
	
	// Initial all flags in nodes to 'not visited'
	public boolean initialFlags(Node nodeB){
		//initial nodeA
		Set<String> set = this.graph.graphNodes.keySet();
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()){
			this.setVisitedFlagToGraphNode(0,itr.next());
		}
		// inital nodeB
		set = nodeB.graph.graphNodes.keySet();
		itr = set.iterator();
		while (itr.hasNext()){
			nodeB.setVisitedFlagToGraphNode(0,itr.next());
		}
		return true;
	}
		
	// Append handShak counter
	public boolean appendHSCounter(Node nodeB){
		
		GraphNode graphNode = this.graph.getGraphNode(nodeB.getUid());
		graphNode.updateHandShake();
		
		graphNode = nodeB.graph.getGraphNode(this.getUid());
		graphNode.updateHandShake();
		return true;
	}
		
	// Calculate KDegree - temporary
	public boolean calculateK(Node nodeB){	
		// consider my rank
		//int rank = this.getRank(this.getUid());
		// consider handShakes counter with the node
		//List<Date> hS = this.graph.graphNodes.get(nodeB.getUid()).getHandShakeCounter();
		GraphNode graphNode = this.graph.getGraphNode(nodeB.getUid());
		graphNode.setKDegree(3);
		
		//rank = nodeB.getRank(nodeB.getUid());
		// consider handShakes counter with the node
		//hS = nodeB.graph.graphNodes.get(this.getUid()).getHandShakeCounter();
		// nodeB will take from nUid KDegree
		graphNode = nodeB.graph.getGraphNode(this.getUid());
		graphNode.setKDegree(3);
		return true;
	}
	
	
	// QueueIn exchange between nodeA and nodeB themselves
	private boolean QueueInExchange(Node nodeB){
		String tempPacketId;
		Packet tempPacket;
		GraphNode graphNode =  this.graph.getGraphNode(this.getUid());
		Iterator<String> itr = nodeB.getQueueIn(this.getUid()).iterator();
		
		// running on Packets in queueIn in nodeB
		while (itr.hasNext()){
			tempPacketId = itr.next();
			tempPacket =  new Packet(nodeB.getPacket(tempPacketId));
			// if nodeA contains the packet
			if (!this.graph.packets.containsKey(tempPacketId)){
				// set tempPacket to delivered
				tempPacket.setStatus(2);
				// important- in case when the node have a packet for not existing node 
				nodeB.getPacket(tempPacketId).setStatus(2);
				// if nodeB didn't send the packet delete the msg 
				if (!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
					nodeB.changePacketMsg(tempPacketId, null);
				// updates node's A packets with the new packet
				this.graph.packets.put(tempPacketId, tempPacket);
				graphNode.addPacketUidtoQueuIn(tempPacketId);
				// due to we sync queueIn first. if nodeB sent the msg to nodeA,put this packet in nodeB queueOut in graph's nodeA
				if(nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId) )
					this.graph.getGraphNode(nodeB.getUid()).addPacketUidtoQueuOut(tempPacketId);	
			}
		}
		
		// updates nodeA in nodeB
		graphNode =  nodeB.graph.getGraphNode(this.getUid());
		itr = this.getQueueIn(this.getUid()).iterator();
		
		while (itr.hasNext()){
		// running on Packets in queueIn in nodeA
			tempPacketId = itr.next();
			tempPacket =  new Packet(this.getPacket(tempPacketId));
			// if nodeB contains the packet
			if (!nodeB.graph.packets.containsKey(tempPacketId)){
				// set tempPacket to delivered
				tempPacket.setStatus(2);
				tempPacket.setMsg(null);
				nodeB.graph.packets.put(tempPacketId, tempPacket);
				graphNode.addPacketUidtoQueuIn(tempPacketId);
			}
			else{
				// if its a msg that nodeB created - don't delete the msg from nodeB
				if ( !nodeB.graph.graphNodes.get(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
					tempPacket.setMsg(null);
				tempPacket.setStatus(2);
			}
		}
		return true;
	}
	
	// QueueIn exchange between nodeA and nodeB
	private boolean QueueInExchange(Node nodeB,String nUid){
		if (this.graph.getGraphNode(nUid).getFlag() == 1)
			return false;
		Packet tempPacket;
		String tempPacketId;
		GraphNode graphNode;
		Iterator<String> itr;
		// nUid in nodeA update its queueIn from nUid in nodeB
		graphNode = this.graph.getGraphNode(nUid);
		itr = nodeB.getQueueIn(nUid).iterator();
		// running on Packets in queueIn in nodeB
		while (itr.hasNext()){
			tempPacketId = itr.next();
			tempPacket =  new Packet(nodeB.getPacket(tempPacketId));
			// if nodeA contains the packet
			if (!this.graph.packets.containsKey(tempPacket.getUid())){
			//  if the packet status 2 (delivered) and not connected to node nodeA(queueIn or out) change to null
				if ( tempPacket.getStatus() == 2)
					if (!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
									!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
						tempPacket.setMsg(null);
				// update packet in nUid in nodeA
				this.graph.packets.put(tempPacketId, tempPacket);
				graphNode.addPacketUidtoQueuIn(tempPacketId);
				this.graph.graphNodes.put(graphNode.getUid(), graphNode);
			}
			// If packet exist in nodeA
			else{
					// If packet status is updated , update nodeA's packet
					if (tempPacket.getStatus() > this.graph.packets.get(tempPacketId).getStatus()){
						this.graph.packets.get(tempPacketId).setStatus(tempPacket.getStatus());
						// if the packet status 2 (delivered) and not connected to node nodeA(queueIn or out) change to null
						if ( tempPacket.getStatus() == 2)
							if (!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
											!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
								this.graph.packets.get(tempPacketId).setMsg(null);
					}
					// If packet status is not updated , update nodeB's packet
					else{
						nodeB.graph.packets.get(tempPacketId).setStatus(this.getPacket(tempPacketId).getStatus());
						// if the packet status 2 (delivered) and not connected to node nodeB(queueIn or out) change to null
						if ( tempPacket.getStatus() == 2)
							if (!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
											!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
								nodeB.graph.packets.get(tempPacketId).setMsg(null);
					}
				}	
			}
		
		return true;
	}
	
	// QueueIn exchange 
	private boolean QueueInExchangeServer(Node nodeB,String nUid){
		if (this.graph.getGraphNode(nUid).getFlag() == 1)
			return false;
		Packet tempPacket;
		String tempPacketId;
		GraphNode graphNode;
		Iterator<String> itr;
		// nUid in nodeA update its queueIn from nUid in nodeB
		graphNode = this.graph.getGraphNode(nUid);
		itr = nodeB.getQueueIn(nUid).iterator();
		// running on Packets in queueIn in nodeB
		while (itr.hasNext()){
			tempPacketId = itr.next();
			tempPacket =  new Packet(nodeB.getPacket(tempPacketId));
			// if nodeA contains the packet
			if (!this.graph.packets.containsKey(tempPacket.getUid())){
				
				if(tempPacket.getStatus()< 2)
					tempPacket.setStatus(1);
				if(this.graph.packets.get(tempPacketId).getStatus() <2)
					this.graph.packets.get(tempPacketId).setStatus(1); 
				
			//  if the packet status 2 (delivered) and not connected to node nodeA(queueIn or out) change to null
				if ( tempPacket.getStatus() == 2)
					if (!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
									!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
						tempPacket.setMsg(null);
				// update packet in nUid in nodeA
				this.graph.packets.put(tempPacketId, tempPacket);
				graphNode.addPacketUidtoQueuIn(tempPacketId);
				this.graph.graphNodes.put(graphNode.getUid(), graphNode);
			}
			// If packet exist in nodeA
			else{
					if(tempPacket.getStatus()< 2)
						tempPacket.setStatus(1);
					if(this.graph.packets.get(tempPacketId).getStatus() <2)
						this.graph.packets.get(tempPacketId).setStatus(1); 
					
					// If packet status is updated , update nodeA's packet
					if (tempPacket.getStatus() > this.graph.packets.get(tempPacketId).getStatus()){
						this.graph.packets.get(tempPacketId).setStatus(tempPacket.getStatus());
						// if the packet status 2 (delivered) and not connected to node nodeA(queueIn or out) change to null
						if ( tempPacket.getStatus() == 2)
							if (!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
											!this.graph.getGraphNode(this.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
								this.graph.packets.get(tempPacketId).setMsg(null);
					}
					// If packet status is not updated , update nodeB's packet
					else{
						nodeB.graph.packets.get(tempPacketId).setStatus(this.getPacket(tempPacketId).getStatus());
						// if the packet status 2 (delivered) and not connected to node nodeB(queueIn or out) change to null
						if ( tempPacket.getStatus() == 2)
							if (!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
											!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
								nodeB.graph.packets.get(tempPacketId).setMsg(null);
					}
				}	
			}
		
		return true;
	}
	
	
	// QueueOut exchange between nodeA and nodeB
		private boolean QueueOutExchange(Node nodeB){
			
			String addressee;
			String tempPacketId;
			Packet tempPacket;
			GraphNode graphNode;
			Iterator<String> itr;
			
			// nodeA sends its queueOut to nodeA' in nodeB
			graphNode = nodeB.graph.getGraphNode(this.getUid());
			itr = this.getQueueOut(this.getUid()).iterator();
			while (itr.hasNext()){
				tempPacketId = itr.next();
				tempPacket = new Packet(this.getPacket(tempPacketId));
				addressee = tempPacket.getAddressee();
				// if nodeB not contains the packet
				if (!nodeB.graph.packets.containsKey(tempPacket.getUid())){
	
					// if addressee is nodeB
					if (addressee.equals(nodeB.getUid())){
						tempPacket.setStatus(2);
						this.getPacket(tempPacketId).setStatus(2);
					}
					// if addressee exist in graph
					if (! (nodeB.graph.getGraphNode(addressee)  == null))
						// add Packet Uid to QueuIn in the exist node
						nodeB.graph.getGraphNode(addressee).addPacketUidtoQueuIn(tempPacketId);	
					graphNode.addPacketUidtoQueuOut(tempPacketId);
					// update packet in nUid in nodeB
					nodeB.graph.packets.put(tempPacketId, tempPacket);
					if (tempPacket.getStatus() == 2){
						nodeB.graph.packets.get(tempPacketId).setMsg(null);
					}
				}
				else{
					
					// if the packet in nodeA is updated 
					if (tempPacket.getStatus() > nodeB.graph.packets.get(tempPacketId).getStatus() ){
						nodeB.graph.packets.get(tempPacketId).setStatus(tempPacket.getStatus());
						//if ( !nodeB.graph.graphNodes.get(nodeB.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) )
						if (tempPacket.getStatus() == 2)
							nodeB.graph.packets.get(tempPacketId).setMsg(null);
					}
					else if (tempPacket.getStatus() < nodeB.graph.packets.get(tempPacketId).getStatus() ){
						this.getPacket(tempPacketId).setStatus(nodeB.graph.packets.get(tempPacketId).getStatus());
					}
				}
			}
			return true;
		}
	
		// QueueOut exchange between nodeA and nodeB
			private boolean QueueOutExchangeServer(Node nodeB){
				
				String addressee;
				String tempPacketId;
				Packet tempPacket;
				GraphNode graphNode;
				Iterator<String> itr;
				
				// nodeA sends its queueOut to nodeA' in nodeB
				graphNode = nodeB.graph.getGraphNode(this.getUid());
				itr = this.getQueueOut(this.getUid()).iterator();
				while (itr.hasNext()){
					tempPacketId = itr.next();
					tempPacket = new Packet(this.getPacket(tempPacketId));
					addressee = tempPacket.getAddressee();
					// if nodeB not contains the packet
					if (!nodeB.graph.packets.containsKey(tempPacket.getUid())){
		
						if(tempPacket.getStatus()< 2)
							tempPacket.setStatus(1);
						if(this.graph.packets.get(tempPacketId).getStatus() <2)
							this.graph.packets.get(tempPacketId).setStatus(1);
						
						// if addressee is nodeB
						if (addressee.equals(nodeB.getUid())){
							tempPacket.setStatus(2);
							this.getPacket(tempPacketId).setStatus(2);
						}
						// if addressee exist in graph
						if (! (nodeB.graph.getGraphNode(addressee)  == null))
							// add Packet Uid to QueuIn in the exist node
							nodeB.graph.getGraphNode(addressee).addPacketUidtoQueuIn(tempPacketId);	
						graphNode.addPacketUidtoQueuOut(tempPacketId);
						// update packet in nUid in nodeB
						nodeB.graph.packets.put(tempPacketId, tempPacket);
						if (tempPacket.getStatus() == 2){
							nodeB.graph.packets.get(tempPacketId).setMsg(null);
						}
					}
					else{
						
						// if the packet in nodeA is updated 
						if (tempPacket.getStatus() > nodeB.graph.packets.get(tempPacketId).getStatus() ){
							nodeB.graph.packets.get(tempPacketId).setStatus(tempPacket.getStatus());
							//if ( !nodeB.graph.graphNodes.get(nodeB.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) )
							if (tempPacket.getStatus() == 2)
								nodeB.graph.packets.get(tempPacketId).setMsg(null);
						}
						else if (tempPacket.getStatus() < nodeB.graph.packets.get(tempPacketId).getStatus() ){
							this.getPacket(tempPacketId).setStatus(nodeB.graph.packets.get(tempPacketId).getStatus());
						}
					}
					if(tempPacket.getStatus()< 2)
						tempPacket.setStatus(1);
					if(this.graph.packets.get(tempPacketId).getStatus() <2)
						this.graph.packets.get(tempPacketId).setStatus(1); 
				}
				return true;
			}
		
		
	// QueueOut exchange between nodeA and nodeB
	private boolean QueueOutExchange(Node nodeB,String nUid){
		if (this.graph.getGraphNode(nUid).getFlag() == 1)
			return false;
		String addressee;
		String tempPacketId;
		Packet tempPacket;
		
		Iterator<String> itr;
		
		// nUid in nodeA sends its queueOut to nUid in nodeB
		
		itr = this.getQueueOut(nUid).iterator();
		while (itr.hasNext()){
			tempPacketId = itr.next();
			tempPacket = new Packet(this.getPacket(tempPacketId));
			addressee = tempPacket.getAddressee();
			// if nodeB not contains the packet
			if (!nodeB.graph.packets.containsKey(tempPacketId)){
				
				//  if the packet status 2 (delivered) and not connected to node nodeB(queueIn or out) turn to null
				if ( tempPacket.getStatus() == 2)
					if (!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
						!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
						tempPacket.setMsg(null);
				
				// update addressee queuIn if exist
				if (nodeB.graph.graphNodes.containsKey(addressee)){
					nodeB.addPacketUidToQueueIn(addressee, tempPacketId);
				}
				// update packet in queueOut in nUid in nodeB
				nodeB.addPacketUidToQueueOut(nUid, tempPacketId);
				// Add packet to nodeB packets
				nodeB.graph.packets.put(tempPacketId, tempPacket);
				
			}
			// if packet exist in nodeB
			else{
				// in case the packet is only in nodeB queueIn and not nodeN queueOut (delivered to B when B didn't know N) 
				 if (!nodeB.graph.graphNodes.get(nUid).checkPacketUidExsitInQueueOut(tempPacketId))
					 nodeB.addPacketUidToQueueOut(nUid, tempPacketId);
				
				// if the packet in nodeA is updated 
				if (tempPacket.getStatus() > nodeB.graph.packets.get(tempPacketId).getStatus() ){
					nodeB.graph.packets.get(tempPacketId).setStatus(tempPacket.getStatus());
					if ( !nodeB.graph.graphNodes.get(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacket.getUid()) )
							nodeB.graph.packets.get(tempPacketId).setMsg(tempPacket.getMsg());
				}
				else if (tempPacket.getStatus() < nodeB.graph.packets.get(tempPacketId).getStatus() ){
						this.graph.packets.get(tempPacketId).setStatus(nodeB.graph.packets.get(tempPacketId).getStatus());
						if ( !this.graph.graphNodes.get(this.getUid()).checkPacketUidExsitInQueueOut(tempPacketId) )
								this.graph.packets.get(tempPacketId).setMsg(nodeB.graph.packets.get(tempPacketId).getMsg());
				}
			}
		}
		return true;
	}

	// QueueOut 
		private boolean QueueOutExchangeServer(Node nodeB,String nUid){
			if (this.graph.getGraphNode(nUid).getFlag() == 1)
				return false;
			String addressee;
			String tempPacketId;
			Packet tempPacket;
			
			Iterator<String> itr;
			
			// nUid in nodeA sends its queueOut to nUid in nodeB
			
			itr = this.getQueueOut(nUid).iterator();
			while (itr.hasNext()){
				tempPacketId = itr.next();
				tempPacket = new Packet(this.getPacket(tempPacketId));
				addressee = tempPacket.getAddressee();
				// if nodeB not contains the packet
				if (!nodeB.graph.packets.containsKey(tempPacketId)){
					
					if(tempPacket.getStatus()< 2)
						tempPacket.setStatus(1);
					if(this.graph.packets.get(tempPacketId).getStatus() <2)
						this.graph.packets.get(tempPacketId).setStatus(1); 
					
					//  if the packet status 2 (delivered) and not connected to node nodeB(queueIn or out) turn to null
					if ( tempPacket.getStatus() == 2)
						if (!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueIn(tempPacketId) &
							!nodeB.graph.getGraphNode(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacketId))
							tempPacket.setMsg(null);
					
					// update addressee queuIn if exist
					if (nodeB.graph.graphNodes.containsKey(addressee)){
						nodeB.addPacketUidToQueueIn(addressee, tempPacketId);
					}
					// update packet in queueOut in nUid in nodeB
					nodeB.addPacketUidToQueueOut(nUid, tempPacketId);
					// Add packet to nodeB packets
					nodeB.graph.packets.put(tempPacketId, tempPacket);
					
				}
				// if packet exist in nodeB
				else{
					
					if(tempPacket.getStatus()< 2)
						tempPacket.setStatus(1);
					if(this.graph.packets.get(tempPacketId).getStatus() <2)
						this.graph.packets.get(tempPacketId).setStatus(1); 
					
					// in case the packet is only in nodeB queueIn and not nodeN queueOut (delivered to B when B didn't know N) 
					 if (!nodeB.graph.graphNodes.get(nUid).checkPacketUidExsitInQueueOut(tempPacketId))
						 nodeB.addPacketUidToQueueOut(nUid, tempPacketId);
					
					// if the packet in nodeA is updated 
					if (tempPacket.getStatus() > nodeB.graph.packets.get(tempPacketId).getStatus() ){
						nodeB.graph.packets.get(tempPacketId).setStatus(tempPacket.getStatus());
						if ( !nodeB.graph.graphNodes.get(nodeB.getUid()).checkPacketUidExsitInQueueOut(tempPacket.getUid()) )
								nodeB.graph.packets.get(tempPacketId).setMsg(tempPacket.getMsg());
					}
					else if (tempPacket.getStatus() < nodeB.graph.packets.get(tempPacketId).getStatus() ){
							this.graph.packets.get(tempPacketId).setStatus(nodeB.graph.packets.get(tempPacketId).getStatus());
							if ( !this.graph.graphNodes.get(this.getUid()).checkPacketUidExsitInQueueOut(tempPacketId) )
									this.graph.packets.get(tempPacketId).setMsg(nodeB.graph.packets.get(tempPacketId).getMsg());
					}
				}
			}
			return true;
		}
		
	public Node handShake(Node nodeB){
		
		// the k degree of each node;
		int kDegreeA;
		int kDegreeB;
		// stage 1- the nodes exchanging between themselves metadata and msgs
		// Search nodes exists in each node graph
		this.searchNode(nodeB.getUid());
		nodeB.searchNode(this.getUid());
		
		// add Edge
		this.graph.addEdge(this.getUid(), nodeB.getUid());
		nodeB.graph.addEdge(nodeB.getUid(),this.getUid());
		
		//  Update Rank Request for NodeA
		updateRankRequest(nodeB,this.getUid());
		updateRankRequest(nodeB,nodeB.getUid());
		
		// Initial all flags in nodes to 'not visited'
		initialFlags(nodeB);
		
		// Append handShak counter
		appendHSCounter(nodeB);
		
		// Calculate KDegree
		calculateK(nodeB);
		
		// QueueIn exchange between nodeA and nodeB
		this.QueueInExchange(nodeB);
		nodeB.QueueInExchange(this);
		
		// QueueOut exchange between nodeA and nodeB
		this.QueueOutExchange(nodeB);
		nodeB.QueueOutExchange(this);
		
		// mark flag
		this.setFlag(this.getUid(), 1);
		this.setFlag(nodeB.getUid(), 1);
		nodeB.setFlag(this.getUid(), 1);
		nodeB.setFlag(nodeB.getUid(), 1);

		 
		// stage 2 - the nodes exchanging Kdegree GraphNodes
		// define the level of degree to share between nodes
		kDegreeA = nodeB.getkDegree(this.getUid());
		kDegreeB = this.getkDegree(nodeB.getUid());
		int min = Math.min(kDegreeA, kDegreeB);
		
		// go over all the mutual degrees to share
		for ( int degree = 1; degree <= min; degree++){
			this.handShakeDegree(nodeB,this.graph.getGraphNode(this.getUid()),degree);
			nodeB.handShakeDegree(this,nodeB.graph.getGraphNode(nodeB.getUid()),degree);
		}
		return nodeB;
	} 
	
	private void handShakeDegree(Node nodeB,GraphNode graphNode, int degree){
		
		String nUid;
		Set <String> list = this.graph.getNodeAdjacencyList(graphNode.getUid());
		Iterator<String> itrGraphNode = list.iterator();
		while (itrGraphNode.hasNext()){
			nUid = itrGraphNode.next();
			if (degree == 1){
				/// handShake
					this.searchNode(nUid);
					nodeB.searchNode(nUid);
					nodeB.graph.addEdge(graphNode.getUid(), nUid);
					updateRankRequest(nodeB,nUid);
					this.QueueInExchange(nodeB, nUid);
					nodeB.QueueInExchange(this, nUid);
					this.QueueOutExchange(nodeB, nUid);
					nodeB.QueueOutExchange(this, nUid);
					this.setFlag(nUid, 1);
					nodeB.setFlag(nUid, 1);
			}
			else{
				handShakeDegree(nodeB,this.graph.getGraphNode(nUid), degree-1);
			}	
		}
		
		
	}
	
private void handShakeDegreeServer(Node nodeB,GraphNode graphNode, int degree){
		
		String nUid;
		Set <String> list = this.graph.getNodeAdjacencyList(graphNode.getUid());
		Iterator<String> itrGraphNode = list.iterator();
		while (itrGraphNode.hasNext()){
			nUid = itrGraphNode.next();
			if (degree == 1){
				/// handShake
					this.searchNode(nUid);
					nodeB.searchNode(nUid);
					nodeB.graph.addEdge(graphNode.getUid(), nUid);
					updateRankRequest(nodeB,nUid);
					this.QueueInExchangeServer(nodeB, nUid);
					nodeB.QueueInExchangeServer(this, nUid);
					this.QueueOutExchangeServer(nodeB, nUid);
					nodeB.QueueOutExchangeServer(this, nUid);
					this.setFlag(nUid, 1);
					nodeB.setFlag(nUid, 1);
			}
			else{
				handShakeDegree(nodeB,this.graph.getGraphNode(nUid), degree-1);
			}	
		}
		
		
	}
	
	 public void handShakeWithServer(Node nodeN){
		 
		 updateRankRequest(nodeN,this.getUid());
		 updateRankRequest(this,nodeN.getUid());
		 appendHSCounter(nodeN);
		 initialFlags(nodeN);
		 // QueueIn exchange between nodeA and nodeB
		 this.QueueInExchange(nodeN);
		 nodeN.QueueInExchange(this);	
		 // QueueOut exchange between nodeA and nodeB
		 this.QueueOutExchangeServer(nodeN);
		 nodeN.QueueOutExchangeServer(this);
		 this.setFlag(this.getUid(), 1);
		 this.setFlag(nodeN.getUid(), 1);
		 nodeN.setFlag(this.getUid(), 1);
		 nodeN.setFlag(nodeN.getUid(), 1);
		 nodeN.handShakeDegreeServer(this,nodeN.graph.getGraphNode(nodeN.getUid()),6);
		 
	 }
}
