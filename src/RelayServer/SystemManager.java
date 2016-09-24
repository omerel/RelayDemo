package RelayServer;

import RelayClient.Node;

//import java.util.UUID;

public class SystemManager {
	
	private static final int KDEGREE = 3;
	private static final int RANK = 0;
	private static char uid = 'a';
	public Node cloud;
		
	public SystemManager(){
		this.cloud = new Node(this,"Server");
	}
	
	public String createServer(String name){
		return name;
	}
	
	public char signUpNode(){
		// Search nodes exists in each node graph
		cloud.searchNode(String.valueOf(uid));
		cloud.graph.addEdge(cloud.getUid(), String.valueOf(uid));
		return uid++;
		//return UUID.randomUUID().toString();
	}
	
	public int getDefaultKDegree(){
		return KDEGREE;
	}
	
	public int getDefaultRank(){
		return RANK;
	}

}
