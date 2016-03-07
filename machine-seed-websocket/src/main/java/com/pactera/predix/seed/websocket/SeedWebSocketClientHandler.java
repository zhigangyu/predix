package com.pactera.predix.seed.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint
public class SeedWebSocketClientHandler {
	private static Logger _logger = LoggerFactory.getLogger(SeedWebSocketClientHandler.class.getName());


    /**
     * Defines the behavior of the handler when a session is opened. Prints
     * the status to the logger.
     * 
     * @param session The web socket session
     */
    @OnOpen
    public void onOpen(Session session)
    {
        _logger.info("Client: opened... " + session.getId()); //$NON-NLS-1$
    }

    /**
     * Defines the behavior of the handler when a string message is received.
     * Checks the message for validity and logs transmission success or failure
     * 
     * @param message The string message that was received
     * @param session The web socket session
     */
    @OnMessage
    public void onStringMessage(String message, Session session)
    {
        _logger.info("Client: received... " + message + "."); //$NON-NLS-1$ //$NON-NLS-2$
        if ( message.equals(SeedWebSocketClientContainer.TEST_STRING) )
        {
            _logger.info("Client: Text message success"); //$NON-NLS-1$
        }
        else
        {
            _logger.info("Client: Text message failure"); //$NON-NLS-1$
        }

    }

    /**
     * Defines the behavior of the handler when a byte message is received.
     * Checks the message for validity and logs transmission success or failure
     * 
     * @param message The byte buffer message that was received
     * @param session The web socket session
     */
    @OnMessage
    public void onByteMessage(ByteBuffer message, Session session)
    {
        String result = new String(message.array(), Charset.forName("UTF-8")); //$NON-NLS-1$
        _logger.info("Client: received... " + result + "."); //$NON-NLS-1$ //$NON-NLS-2$
        if ( result.equals(SeedWebSocketClientContainer.TEST_BYTE) )
        {
            _logger.info("Client: Byte message success"); //$NON-NLS-1$
        }
        else
        {
            _logger.info("Client: Byte message failure"); //$NON-NLS-1$
        }
    }

    /**
     * Defines the behavior of the client message handler when the session
     * is closed.
     * 
     * @param session The web socket session
     * @param closeReason Provides information on the session close including
     *            close reason phrase, code, etc...
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        _logger.info("Client: Session " + session.getId() + " closed because of " + closeReason.toString()); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
