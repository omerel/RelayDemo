package RelayClient;

import java.util.Iterator;

import RelayServer.SystemManager;

public class Main {

	public static void printAllMsgsInQueueIn(String nuid,Node node){
		GraphNode graphNode = node.graph.getGraphNode(nuid);
		Iterator<String> itr = graphNode.getQueueIn().iterator();
		while (itr.hasNext()){
			System.out.println(node.getPacket(itr.next()).toString());
		}
	}
	
	public static void printAllMsgsInQueueOut(String nuid,Node node){
		GraphNode graphNode = node.graph.getGraphNode(nuid);
		Iterator<String> itr = graphNode.getQueueOut().iterator();
		while (itr.hasNext()){
			System.out.println(node.getPacket(itr.next()).toString());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SystemManager server = new SystemManager();
		Node nodeA = new Node(server); 
		Node nodeB = new Node(server);
		Node nodeC = new Node(server);
		
		nodeA.sendMessage("c", 0, "from a , test0");
		nodeA.sendMessage("b", 0, "from a , test1");
		nodeB.sendMessage("a", 0, "from b, test2");
		nodeB.sendMessage("c", 0, "from b, test3");
		nodeC.sendMessage("a", 0, "from c, test4");
		
		System.out.println("___________________________________________________");
		System.out.println("Outbox Msgs in nodes");
		System.out.println(" msg in the queueOut of the node A:");
		printAllMsgsInQueueOut("a",nodeA);
		System.out.println(" msg in the queueOut of the node B:");
		printAllMsgsInQueueOut("b",nodeB);
		System.out.println(" msg in the queueOut of the node C:");
		printAllMsgsInQueueOut("c",nodeC);
		
			
		
		System.out.println("___________________________________________________");
		System.out.println("Before HandShake Between A And B, B And C: \n");
		System.out.println("In nodeA:\n"+nodeA.showMetaData(nodeA.getUid())+"\n");
		System.out.println("In nodeB:\n"+nodeB.showMetaData(nodeB.getUid())+"\n");
		System.out.println("In nodeC:\n"+nodeC.showMetaData(nodeC.getUid())+"\n");
		
		
		nodeA.handShake(nodeB);
		nodeB.handShake(nodeC);
		nodeA.handShake(nodeB);
		nodeA.handShake(server.cloud);
		
		System.out.println("___________________________________________________");
		System.out.println("After HandShake Between A And B, B And C: \n");
		System.out.println("In nodeA:\n"+nodeA.showMetaData(nodeA.getUid())+"\n");
		System.out.println("In nodeB:\n"+nodeB.showMetaData(nodeB.getUid())+"\n");
		System.out.println("In nodeC:\n"+nodeC.showMetaData(nodeC.getUid())+"\n");
		
		System.out.println("In nodeA:\n"+nodeA.showMetaData(nodeB.getUid())+"\n");
		System.out.println("In nodeB:\n"+nodeB.showMetaData(nodeA.getUid())+"\n");
		System.out.println("In nodeC:\n"+nodeC.showMetaData(nodeB.getUid())+"\n");
		System.out.println("___________________________________________________");
		
		System.out.println("The graph of each node:");
		System.out.println("graph nodeA:\n"+nodeA.getGraph().toString());
		System.out.println("graph nodeB:\n"+nodeB.getGraph().toString());
		System.out.println("graph nodeC:\n"+nodeC.getGraph().toString());
		
		System.out.println("___________________________________________________");
		System.out.println("after handshake:");
		System.out.println("========== NODE A ============:");
		System.out.println("node A contains nodeA's queuIn");
		printAllMsgsInQueueIn("a",nodeA);
		System.out.println("node A contains nodeA's queuOut");
		printAllMsgsInQueueOut("a",nodeA);
		System.out.println("node A contains nodeB's queuIn");
		printAllMsgsInQueueIn("b",nodeA);
		System.out.println("node A contains nodeB's queuOut");
		printAllMsgsInQueueOut("b",nodeA);
		System.out.println("node A contains nodeC's queuIn");
		printAllMsgsInQueueIn("c",nodeA);
		System.out.println("node A contains nodeC's queuOut");
		printAllMsgsInQueueOut("c",nodeA);
		System.out.println("========== NODE B ============:");
		System.out.println("node B contains nodeB's queuIn");
		printAllMsgsInQueueIn("b",nodeB);
		System.out.println("node B contains nodeB's queuOut");
		printAllMsgsInQueueOut("b",nodeB);
		System.out.println("node B contains nodeA's queuIn");
		printAllMsgsInQueueIn("a",nodeB);
		System.out.println("node B contains nodeA's queuOut");
		printAllMsgsInQueueOut("a",nodeB);
		System.out.println("node B contains nodeC's queuIn");
		printAllMsgsInQueueIn("c",nodeB);
		System.out.println("node B contains nodeC's queuOut");
		printAllMsgsInQueueOut("c",nodeB);
		System.out.println("========== NODE C ============:");
		System.out.println("node C contains nodeA's queuIn");
		printAllMsgsInQueueIn("a",nodeC);
		System.out.println("node C contains nodeA's queuOut");
		printAllMsgsInQueueOut("a",nodeC);
		System.out.println("node C contains nodeB's queuIn");
		printAllMsgsInQueueIn("b",nodeC);
		System.out.println("node C contains nodeB's queuOut");
		printAllMsgsInQueueOut("b",nodeC);
		System.out.println("node C contains nodeC's queuIn");
		printAllMsgsInQueueIn("c",nodeC);
		System.out.println("node C contains nodeC's queuOut");
		printAllMsgsInQueueOut("c",nodeC);
		
		
		
		System.out.println("========== RELAY SERVER ==========:");
		System.out.println("SERVER contains nodeA's queuIn");
		printAllMsgsInQueueIn("a",server.cloud);
		System.out.println("SERVER contains nodeA's queuOut");
		printAllMsgsInQueueOut("a",server.cloud);
		System.out.println("SERVER contains nodeB's queuIn");
		printAllMsgsInQueueIn("b",server.cloud);
		System.out.println("SERVER contains nodeB's queuOut");
		printAllMsgsInQueueOut("b",server.cloud);
		System.out.println("SERVER contains nodeC's queuIn");
		printAllMsgsInQueueIn("c",server.cloud);
		System.out.println("SERVER contains nodeC's queuOut");
		printAllMsgsInQueueOut("c",server.cloud);
		

	}

}
