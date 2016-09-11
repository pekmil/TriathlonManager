/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author pekmil
 */
@ServerEndpoint("/notification")
public class NotificationEndpoint {
    
    private final static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    
    public static void send(String msg){
        try{
            for(Session session : queue) {
                session.getBasicRemote().sendText(msg);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @OnOpen
    public void openConnection(Session session) throws IOException{ 
        queue.add(session);
        session.getBasicRemote().sendText("Szerver kapcsolat létrejött!");
    }
    
    @OnClose
    public void closedConnection(Session session) throws IOException{
        queue.remove(session);
    }
    
    @OnMessage
    public void message(final Session session, String msg) throws IOException{
        session.getBasicRemote().sendText("Szerver kapcsolat aktív!");
    }
    
    @OnError
    public void error(Session session, Throwable t){
        queue.remove(session);
    }
    
}
