package org.hino.sbb.billboard;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
@ServerEndpoint("/sbbsep")
public class ETFEndpoint {
    private static final Logger logger = Logger.getLogger(ETFEndpoint.class);
    private String message;

    private static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    public ETFEndpoint() {
    }

    public void send(String message) {
        this.message = message;

        try {
            /* Send updates to all open WebSocket sessions */
            for (Session session : queue) {
                session.getBasicRemote().sendText(message);
                logger.info("Sent to websocket: " + message);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        /* Register this connection in the queue */
        queue.add(session);
        logger.log(Level.INFO, "Connection opened.");
        if (message != null && !message.equals("")){
            send(message);
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        /* Remove this connection from the queue */
        queue.remove(session);
        logger.log(Level.INFO, "Connection closed.");
    }

    @OnError
    public void error(Session session, Throwable t) {
        /* Remove this connection from the queue */
        queue.remove(session);
        logger.error("Connection error.\n" + t.toString());
    }
}
