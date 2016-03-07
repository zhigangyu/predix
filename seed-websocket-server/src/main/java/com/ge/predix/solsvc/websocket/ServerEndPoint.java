package com.ge.predix.solsvc.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
@ServerEndpoint(value="/livestream/{nodeId}")
public class ServerEndPoint{
	
	private String nodeId;
	
	private static Logger _logger = LoggerFactory.getLogger(ServerEndPoint.class);
	
    @OnOpen
    public void onOpen(@PathParam(value="nodeId") String nodeId, final Session session, EndpointConfig ec) {
    	this.nodeId = nodeId;
    	_logger.info("Server: opened... for Node Id : "+nodeId+" : " + session.getId()); //$NON-NLS-1$
    	/*try {
			session.getBasicRemote().sendText("Nunmber of open connections : "+session.getOpenSessions().size());
		} catch (Exception e) {
			_logger.error("Exeception", e);
		}*/
    }
    
    @OnMessage
    public void OnMessage(String message,Session session) throws IOException {
    	_logger.info("Message : "+message);
    	
    	for(Session s : session.getOpenSessions()){
    		s.getBasicRemote().sendText(message);
		}
    	
    	try {
    	if ("messages".equalsIgnoreCase(nodeId)) {
    		JsonParser parser = new JsonParser();
    		JsonObject o = (JsonObject)parser.parse(message);
    		JsonArray nodes = o.getAsJsonArray("body");
    		for(Session s : session.getOpenSessions()){
    			if (!"messages".equals(s.getPathParameters().get("nodeId"))) {
    				String pNodeName = s.getPathParameters().get("nodeId");
    				JsonObject node = findJsonObjectByName(nodes, pNodeName);
    				if (node != null) {
    					s.getBasicRemote().sendText(node.toString());
    				}
    			}
    		}
    		String response = "{\"messageId\": "+o.get("messageId").getAsLong()+",\"statusCode\": 202}";
    		//_logger.info("Response : "+response);
			session.getBasicRemote().sendText(response);
    	}else {
    		session.getBasicRemote().sendText("SUCCESS");
    	}}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    private JsonObject findJsonObjectByName(JsonArray nodes,String pNodeName){
    	for (int i=0;i<nodes.size();i++) {
    		JsonObject node = (JsonObject)nodes.get(i);
    		String nodeName = node.get("name").getAsString();
    		if (pNodeName.equalsIgnoreCase(nodeName.trim())) {
    			return node;
    		}
    	}
    	return null;
    }
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    	_logger.info("Server: Session " + session.getId() + " closed because of " + closeReason.toString()); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @OnError
    public void onError(Session session, Throwable t) {
    	_logger.info("Server: Session " + session.getId() + " closed because of " + t.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
