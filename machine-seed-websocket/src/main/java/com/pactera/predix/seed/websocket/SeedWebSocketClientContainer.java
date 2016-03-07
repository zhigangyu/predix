package com.pactera.predix.seed.websocket;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component(name = SeedWebSocketClientContainer.SERVICE_PID)
public class SeedWebSocketClientContainer {
	/**
	 * Create logger to report errors, warning massages, and info messages
	 * (runtime Statistics)
	 */
	protected static Logger _logger = LoggerFactory
			.getLogger(SeedWebSocketClientContainer.class);

	/**
	 * SERVICE_PID String used as the component service name
	 */
	protected final static String SERVICE_PID = "com.pactera.predix.seed.websocket"; //$NON-NLS-1$

	private static final String LOCAL_WSSERVER = "ws://127.0.0.1:9191/livestream/m01"; //$NON-NLS-1$

	/**
	 * TEST_STRING A test string to be used in validating correct websocket
	 * transfer
	 */
	static final String TEST_STRING = "A test string"; //$NON-NLS-1$
	/**
	 * TEST_BYTE A test string that will be converted to a byte buffer to be
	 * used in validating correct websocket transfer
	 */
	static final String TEST_BYTE = "A test of binary data sending"; //$NON-NLS-1$
	/**
	 * TEST_BYTE_BUFF1 A test byte buffer to be used in validating correct
	 * websocket transfer
	 */
	static final ByteBuffer TEST_BYTE_BUFF = ByteBuffer.wrap(TEST_BYTE
			.getBytes(Charset.forName("UTF-8"))); //$NON-NLS-1$

	private WebSocketContainer clientContainer;

	/** Local session */
	protected static Session locSession;

	/**
	 * Sets up the locat websocket session and sends some data. Sessions that
	 * are created here are not explicitly closed because web sockets will close
	 * automatically with a timeout. Sessions can be manually closed using a
	 * session.close() call.
	 * 
	 * @param context
	 *            The OSGi Component Context
	 * @throws InterruptedException
	 *             Exception thrown by the sleep function between echo tests
	 */
	@Activate
	protected void activate(ComponentContext context)
			throws InterruptedException {
		_logger.info("\n\nClient: Echo test started"); //$NON-NLS-1$

		// Best practice is to do these in a thread since long running tasks
		// should not take place in the activate.
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				try {
					_logger.info("\n\n\nClient: Echo test with localhost"); //$NON-NLS-1$
					locSession = getWebSocketContainer().connectToServer(
							SeedWebSocketClientHandler.class,
							URI.create(LOCAL_WSSERVER));
					_logger.info("Client: sending... A test string"); //$NON-NLS-1$
					locSession.getBasicRemote().sendText(TEST_STRING);
					_logger.info("Client: sending... " + TEST_BYTE); //$NON-NLS-1$
					//locSession.getBasicRemote().sendBinary(TEST_BYTE_BUFF);
					int i = 0;
					while(true){
						i ++;
						Thread.sleep(3000);
						locSession.getBasicRemote().sendText("Message " + i);
					}

				} catch (DeploymentException | IOException e) {
					_logger.error(
							"Client: Failed to send messages to server", e); //$NON-NLS-1$
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					_logger.error(
							"Client: Failed to send messages to server", e);
			}

			}
		};

		new Thread(runner).start();
	}

	/**
	 * Dependency injection of Websocket container
	 * 
	 * @param container
	 *            Websocket Container used to connect sessions with the server
	 */
	@Reference
	protected void setWebSocketContainer(WebSocketContainer container) {
		this.clientContainer = container;
	}

	/**
	 * Unset of Websocket container
	 * 
	 * @param container
	 *            Websocket Container used to connect sessions with the server
	 */
	protected void unsetWebSocketContainter(WebSocketContainer container) {
		this.clientContainer = null;
	}

	/**
	 * @return the WebSocketContainer
	 */
	public WebSocketContainer getWebSocketContainer() {
		return this.clientContainer;
	}
}
