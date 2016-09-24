package RelayClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


// NGraph Class. V represent the type of uid in the node
public class NGraph {

	// Variables 
	protected Map< String, GraphNode> graphNodes;
	protected Map< String, Set<String>> adjacencyList;
	protected Map< String, Packet> packets; 
	
	// Constructor
	public NGraph() {
	this.graphNodes = new HashMap<>();
	this.adjacencyList = new HashMap<>();
	this.packets = new HashMap<>();
	}
	
	// Getter and Setters
	public Map<String, GraphNode> getGraphNodes() {
		 return this.graphNodes;
	}
	
	public Map<String, Set<String>> getAdjacencyList() {
		 return this.adjacencyList;
	}
	
	public Set<String> getNodeAdjacencyList(String uid){
		return this.adjacencyList.get(uid);
	}
	
	public GraphNode getGraphNode(String uid){
		return this.graphNodes.get(uid);
	}
	
	
	public Map<String, Packet> getPackets() {
		 return this.packets;
	}
	
	
	// check if graph is empty
	public boolean isEmpty() {
		return this.graphNodes.isEmpty();
	}
	
	// check if graph contains node uid
	public boolean isExist(String uid) {
		return this.graphNodes.containsKey(uid);
	}
	
	// Add Edge between two nodes
	public boolean addEdge(String srcUid, String destUid) {
		//if srcUid not exist - bad request
		if (!isExist(srcUid))
			return false;
		// if destUid not exist
		if (!isExist(destUid))
		{
			addGraphNode(destUid);
		}
		// get djacencyList of each nodes
		Set<String> srcNodeList = getNodeAdjacencyList(srcUid);
		Set<String> destNodeList = getNodeAdjacencyList(destUid);
		// update their adjacencyList
		srcNodeList.add(destUid);
		this.adjacencyList.put(srcUid, srcNodeList);
		destNodeList.add(srcUid);
		this.adjacencyList.put(destUid, destNodeList);
		return true;
	}
	
	// Remove edge between two nodes
	public boolean removeEdge(String srcUid, String destUid){
			// if one of the node are not exist return false
			if (isExist(srcUid)&isExist(destUid) == false)
				return false;
			// get djacencyList of each nodes
			Set<String> srcNodeList = getNodeAdjacencyList(srcUid);
			Set<String> destNodeList = getNodeAdjacencyList(destUid);
			// update their adjacencyList
			srcNodeList.remove(destUid);
			this.adjacencyList.put(srcUid, srcNodeList);
			destNodeList.remove(srcUid);
			this.adjacencyList.put(destUid, destNodeList);
			return true;
		}
	
	public boolean addGraphNode(String uid){
			this.graphNodes.put(uid, new GraphNode(uid));
			this.adjacencyList.put(uid, new HashSet<String>());
		return true;
	}
		
	// Remove GraphNode from graph
	public boolean removeGraphNode(String uid){
		//if srcUid not exist - bad request
		if (!isExist(uid))
			return false;
		// get djacencyList of node uid
		Set<String> nodeList = getNodeAdjacencyList(uid);
		Iterator<String> itrNode = nodeList.iterator();
		while (itrNode.hasNext()){
			removeEdge(uid, itrNode.next());
		}
		this.adjacencyList.remove(uid);
		this.graphNodes.remove(uid);
		return true;
	}
	
	
	// Print graph
	public String toString(){
		StringBuilder sGraph = new StringBuilder();
		Set<String> nodeSet = graphNodes.keySet();
		Iterator<String> itrNodeUid = nodeSet.iterator();
		String tempUid;
		while (itrNodeUid.hasNext()){
			tempUid = itrNodeUid.next();
			sGraph.append("Set of Edges from node "+tempUid+" : \n");
			Iterator<String> itrEdge = (this.adjacencyList.get(tempUid)).iterator();
			while (itrEdge.hasNext()){
				sGraph.append(tempUid+" ----> "+(itrEdge.next())+"\n");
			}
		}
		return sGraph.toString();
	}
}
