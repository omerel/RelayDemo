package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import RelayClient.Node;
import RelayServer.SystemManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.control.RadioButton;

import javafx.scene.layout.Pane;

public class appController implements Initializable {
	@FXML
	private Pane MainPane;
	@FXML
	private RadioButton radioCloud;
	@FXML
	private Label nodeId1;
	@FXML
	private TextField to1;
	@FXML
	private TextField msg1;
	@FXML
	private Button sendMsg1;
	@FXML
	private Button sendMsg2;
	@FXML
	private TextField msg2;
	@FXML
	private TextField to2;
	@FXML
	private Label nodeId2;
	@FXML
	private Button hsButtom;
	@FXML
	private ListView outBox1;
	@FXML
	private ListView inBox1;
	@FXML
	private ListView outBox2;
	@FXML
	private ListView inBox2;
	@FXML
	private RadioButton radioA;
	@FXML
	private RadioButton radioB;
	@FXML
	private RadioButton radioC;
	@FXML
	private RadioButton radioD;
	@FXML
	private RadioButton radioE;
	@FXML
	private RadioButton RadioF;
	@FXML
	private RadioButton radioG;
	@FXML
	private RadioButton radioH;
	private int handShakeRequestCount = 0;
	SystemManager server;
	Node nodeA;
	Node nodeB;
	Node nodeC;
	Node nodeD;
	Node nodeE;
	Node nodeF;
	Node nodeG;
	Node nodeH;

	// Event Listener on Button[#sendMsg1].onAction
	@FXML
	public void sendMsg1(ActionEvent event) {
		// TODO Autogenerated
		Node node = null;
		
		if (nodeId1.getText().equals("a"))
			node = nodeA;

		if (nodeId1.getText().equals("b")){
			node = nodeB;
		}
		
		if (nodeId1.getText().equals("c")){
			node = nodeC;
		}
		if (nodeId1.getText().equals("d")){
			node = nodeD;
		}
		if (nodeId1.getText().equals("e")){
			node = nodeE;
		}
		if (nodeId1.getText().equals("f")){
			node = nodeF;
		}
		if (nodeId1.getText().equals("g")){
			node = nodeG;
		}
		if (nodeId1.getText().equals("h")){
			node = nodeH;
		}
		node.sendMessage(to1.getText(),0,msg1.getText());
		to1.setText("");
		msg1.setText("");
		showOutBox(outBox1, node);
	}
	
	// Event Listener on Button[#sendMsg2].onAction
	@FXML
	public void sendMsg2(ActionEvent event) {
		// TODO Autogenerated
		Node node = null;
		if (nodeId2.getText().equals("a")){
			node = nodeA;
		}
		if (nodeId2.getText().equals("b")){
			node = nodeB;
		}
		if (nodeId2.getText().equals("c")){
			node = nodeC;
		}
		if (nodeId2.getText().equals("d")){
			node = nodeD;
		}
		if (nodeId2.getText().equals("e")){
			node = nodeE;
		}
		if (nodeId2.getText().equals("f")){
			node = nodeF;
		}
		if (nodeId2.getText().equals("g")){
			node = nodeG;
		}
		if (nodeId2.getText().equals("h")){
			node = nodeH;
		}
		node.sendMessage(to2.getText(),0,msg2.getText());
		to2.setText("");
		msg2.setText("");
		showOutBox(outBox2, node);
	}
	// Event Listener on Button[#hsButtom].onAction
	@FXML
	public void startHandShake(ActionEvent event) {
		// TODO Autogenerated
		Node[] nodesHS = new Node[2];
		int counter = 0;
		
		if (handShakeRequestCount < 1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("You need 2 for tango");
			alert.showAndWait();
		}
		else{
			if (radioA.isSelected()){
				nodesHS[counter] = nodeA;
				counter++;
			}
			if (radioB.isSelected()){
				nodesHS[counter] = nodeB;
				counter++;
			}
			if (radioC.isSelected()){
				nodesHS[counter] = nodeC;
				counter++;
			}
			if (radioD.isSelected()){
				nodesHS[counter] = nodeD;
				counter++;
			}
			if (radioE.isSelected()){
				nodesHS[counter] = nodeE;
				counter++;
			}
			if (RadioF.isSelected()){
				nodesHS[counter] = nodeF;
				counter++;
			}
			if (radioG.isSelected()){
				nodesHS[counter] = nodeG;
				counter++;
			}
			if (radioH.isSelected()){
				nodesHS[counter] = nodeH;
				counter++;
			}
			if (radioCloud.isSelected()){
				nodesHS[counter] = server.cloud;
				counter++;
			}
			if  (radioCloud.isSelected()){
				nodesHS[1].handShakeWithServer(nodesHS[0]);
				System.out.println(nodesHS[0].getGraph().toString());
			}
			else{
				nodesHS[0].handShake(nodesHS[1]);
				System.out.println(nodesHS[0].getGraph().toString());
				System.out.println(nodesHS[1].getGraph().toString());
			}
			clearMsgPanels();
			clearRadio();
		}
		
		
			
		
	}
	private void clearRadio() {
		// TODO Auto-generated method stub
		radioA.setSelected(false);
		radioB.setSelected(false);
		radioC.setSelected(false);
		radioD.setSelected(false);
		radioE.setSelected(false);
		RadioF.setSelected(false);
		radioG.setSelected(false);
		radioH.setSelected(false);
		radioCloud.setSelected(false);
		handShakeRequestCount=0;
		
	}

	// Event Listener on RadioButton[#radioA].onAction
	@FXML
	public void handShakeRequest(ActionEvent event) {
		// TODO Autogenerated
		Node node;
		RadioButton radioTemp = (RadioButton) event.getSource();
		node = findSelected(radioTemp);
		if (radioTemp.isSelected()){
			if (handShakeRequestCount<=1){
				handShakeRequestCount++;
				if (handShakeRequestCount == 1)
					if (!node.getUid().equals(this.server.cloud.getUid()))
						showMsgPanelLeft(node);
				if (handShakeRequestCount == 2)
					if (!node.getUid().equals(this.server.cloud.getUid()))
						showMsgPanelRight(node);
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("You need 2 for tango");
				alert.setContentText("please choose 2 node to create handShake");
				alert.showAndWait();
				radioTemp.setSelected(false);
			}
		}
		else{
			handShakeRequestCount--;
			clearOneMsgPanel(node);
		}
	}
	private void clearOneMsgPanel(Node node) {
		// TODO Auto-generated method stub
		if (node.getUid().equals(nodeId1.getText()))
		{
			to1.setVisible(false);
			msg1.setVisible(false);
			sendMsg1.setVisible(false);
			nodeId1.setVisible(false);
			outBox1.setVisible(false);
			inBox1.setVisible(false);
		}
		else{
			to2.setVisible(false);
			msg2.setVisible(false);
			sendMsg2.setVisible(false);
			nodeId2.setVisible(false);
			outBox2.setVisible(false);
			inBox2.setVisible(false);
		}
		
	}
	private void showMsgPanelRight(Node node) {
		// TODO Auto-generated method stub
		nodeId1.setVisible(true);
		nodeId1.setText(node.getUid());
		to1.setVisible(true);
		msg1.setVisible(true);
		sendMsg1.setVisible(true);
		showInBox(inBox1,node);
		showOutBox(outBox1,node);
		outBox1.setVisible(true);
		inBox1.setVisible(true);
	}
	private void showOutBox(ListView outBox,Node node) {
		// TODO Auto-generated method stub
		String msgId;
		ObservableList<String> listItems = FXCollections.observableArrayList();
		List<String> queueOut = node.getQueueOut(node.getUid());
		Iterator<String> itr = queueOut.iterator();
		while(itr.hasNext()){
			msgId = itr.next();
			listItems.add(node.getPacket(msgId).toString2());
		}
		outBox.setItems(listItems);
	}
	private void showInBox(ListView inBox,Node node) {
		// TODO Auto-generated method stub
		String msgId;
		ObservableList<String> listItems = FXCollections.observableArrayList();
		List<String> queueIn = node.getQueueIn(node.getUid());
		Iterator<String> itr = queueIn.iterator();
		while(itr.hasNext()){
			msgId = itr.next();
			listItems.add(node.getPacket(msgId).toString2());

		}
		inBox.setItems(listItems);
	}
	private void showMsgPanelLeft(Node node) {
		// TODO Auto-generated method stub
		nodeId2.setVisible(true);
		nodeId2.setText(node.getUid());
		to2.setVisible(true);
		msg2.setVisible(true);
		sendMsg2.setVisible(true);
		showInBox(inBox2,node);
		showOutBox(outBox2,node);
		outBox2.setVisible(true);
		inBox2.setVisible(true);
		
	}
	private Node findSelected(RadioButton radioTemp) {
		if(radioTemp.getId().equals("radioA"))
			return nodeA;
		if(radioTemp.getId().equals("radioB"))
			return nodeB;
		if(radioTemp.getId().equals("radioC"))
			return nodeC;
		if(radioTemp.getId().equals("radioD"))
			return nodeD;
		if(radioTemp.getId().equals("radioE"))
			return nodeE;
		if(radioTemp.getId().equals("RadioF"))
			return nodeF;
		if(radioTemp.getId().equals("radioG"))
			return nodeG;
		if(radioTemp.getId().equals("radioH"))
			return nodeH;
		if(radioTemp.getId().equals("radioCloud"))
			return server.cloud;
		return null;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		clearMsgPanels();
		this.server = new SystemManager();
		this.nodeA = new Node(server); 
		this.nodeB = new Node(server);
		this.nodeC = new Node(server);
		this.nodeD = new Node(server); 
		this.nodeE = new Node(server);
		this.nodeF = new Node(server);
		this.nodeG = new Node(server); 
		this.nodeH = new Node(server);
	}
	public void clearMsgPanels(){
		to1.setVisible(false);
		to2.setVisible(false);
		msg1.setVisible(false);
		msg2.setVisible(false);
		sendMsg1.setVisible(false);
		sendMsg2.setVisible(false);
		nodeId1.setVisible(false);
		nodeId2.setVisible(false);
		outBox1.setVisible(false);
		outBox2.setVisible(false);
		inBox1.setVisible(false);
		inBox2.setVisible(false);
	}
}
