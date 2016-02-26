/*
 * Copyright (c) 2014 General Electric Company. All rights reserved.
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.pactera.predix.seed.machine.httpclient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.dspmicro.httpclient.api.HttpResponseWrapper;
import com.ge.dspmicro.httpclient.api.IHttpClient;
import com.ge.dspmicro.httpclient.api.IHttpClientFactory;
import com.ge.dspmicro.httpclient.api.IPredixCloudHttpClientFactory;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;

/**
 * This sample shows how to use HTTP Client to make a REST call.
 * 
 * @author Predix Machine Sample
 */
@SuppressWarnings("nls")
// @Component is use to register this component as Service in the container.
@Component(name = HttpClientSample.SERVICE_PID)
public class HttpClientSample {
	/** URL of the sample server to which REST calls are sent. */
	private final static String SAMPLE_RESTSERVER = "http://localhost:8080/httpclient/sample/v1"; //$NON-NLS-1$

	/** Service ID of this component */
	protected final static String SERVICE_PID = "com.ge.dspmicro.sample.httpclient"; //$NON-NLS-1$

	/**
	 * Create logger to report errors, warning massages, and info messages
	 * (runtime Statistics)
	 */
	protected static Logger _logger = LoggerFactory
			.getLogger(HttpClientSample.class);

	/**
	 * This injected factory creates private instances of IHttpClient that are
	 * not shared.
	 */
	private IHttpClientFactory httpClientFactory;

	/** A reserved IHttpClient for sending REST calls. */
	private IHttpClient httpClient;

	/**
	 * This injected factory creates private instances of IHttpClient that
	 * supports Predix Cloud authenticated communication. NOTE: This factory
	 * will only be injected when configuration file
	 * <PredixMachine>/configuration
	 * /machine.com.ge.dspmicro.predixcloud.identity.config is populated with
	 * proper settings through enrollment.
	 */
	private IPredixCloudHttpClientFactory cloudHttpClientFactory;

	/**
	 * A reserved IHttpClient that supports Predix Cloud authenticated
	 * communication.
	 */
	private IHttpClient cloudHttpClient;

	/**
	 * The activate method is called when bundle is started.
	 * 
	 * @param ctx
	 *            Component Context.
	 */
	@Activate
	public void activate(ComponentContext ctx) {
		// Use the logger service for debugging purpose
		if (_logger.isDebugEnabled()) {
			_logger.debug("Starting sample "
					+ ctx.getBundleContext().getBundle().getSymbolicName());
		}

		// The REST server may not have completed start up yet. Best practice is
		// to do this in a thread since you don't know how long it will take for
		// the URL
		// to become active.
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				// callWithHttpClient();
				callWithCloudHttpClient();
			}

			private void callWithHttpClient() {
				int maxTries = 20;
				for (int ii = 0; ii < maxTries; ii++) {
					try {
						// Sending a GET request to the test server.
						URI uri = new URI(SAMPLE_RESTSERVER + "/get");

						HttpResponseWrapper httpResponse = getHttpClientService()
								.get(uri);

						if (httpResponse.getStatusCode() == 200) {
							_logger.info("HttpClientSample: GET success: "
									+ new String(httpResponse.getContent())
											.trim());
							return;
						}

						if (ii < maxTries - 1) {
							_logger.warn("HttpClientSample: Cannot connect to server. Status Code: "
									+ httpResponse.getStatusCode());

							// The REST server may not be available yet since
							// this is a timing of startup.
							TimeUnit.SECONDS.sleep(1);
						} else {
							_logger.error("HttpClientSample failed. Cannot connect to server. Status Code: "
									+ httpResponse.getStatusCode());
						}
					} catch (Exception ee) {
						_logger.error(
								"HttpClientSample failed with exception.", ee);
					}
				}
			}

			private void callWithCloudHttpClient() {
				// The following code is similar to callWithHttpClient() and
				// demonstrates how to make a call to Predix Cloud.
				// We bypass it with (byPassFlag == true) because the URL does
				// not point to an actual Predix Cloud.
				boolean byPassFlag = true;
				if (byPassFlag)
					return;

				// Return if IPredixCloudHttpClientFactory is not injected.
				if (getCloudHttpClient() == null)
					return;

				int maxTries = 20;
				for (int ii = 0; ii < maxTries; ii++) {
					try {
						// Sending a GET request to the cloud.
						URI uri = new URI(
								"http://http://freemarker-page-demo.run.aws-usw02-pr.ice.predix.io");

						HttpResponseWrapper httpResponse = getHttpClientService()
								.get(uri);

						if (httpResponse.getStatusCode() == 200) {
							_logger.info("HttpClientSample: GET success: "
									+ new String(httpResponse.getContent())
											.trim());
							return;
						}

						if (ii < maxTries - 1) {
							_logger.warn("HttpClientSample: Cannot connect to Predix Cloud. Status Code: "
									+ httpResponse.getStatusCode());

							// The REST server may not be available yet since
							// this is a timing of startup.
							TimeUnit.SECONDS.sleep(1);
						} else {
							_logger.error("HttpClientSample failed. Cannot connect to Predix Cloud. Status Code: "
									+ httpResponse.getStatusCode());
						}
					} catch (Exception ee) {
						_logger.error(
								"HttpClientSample failed with exception.", ee);
					}
				}
			}
		};

		new Thread(runner).start();
	}

	/**
	 * This method is called when the bundle is stopped.
	 * 
	 * @param ctx
	 *            Component Context
	 */
	@Deactivate
	public void deactivate(ComponentContext ctx) {
		// Put your clean up code here when container is shutting down

		if (_logger.isDebugEnabled()) {
			_logger.debug("Stopped sample for " + ctx.getBundleContext().getBundle().getSymbolicName()); //$NON-NLS-1$
		}
	}

	/**
	 * @return the httpClientService
	 */
	public IHttpClient getHttpClientService() {
		return this.httpClient;
	}

	/**
	 * Dependency injection of IHttpClientFactory.
	 * 
	 * @param clientFactory
	 *            Instance of HttpClientFactory. Use this to create a new
	 *            private instance of IHttpClient service.
	 */
	@SuppressWarnings("deprecation")
	@Reference
	public void setHttpClientService(IHttpClientFactory clientFactory) {
		/*
		 * When modifying the IHttpClient, it will affect all other instances
		 * since the client uses a shared thread pool Therefore when modifying
		 * (configuring HTTPS as an example), the best practice is to use the
		 * factory and create your own private instance of IHttpClient instead
		 * of injecting the standard IHttpClient .
		 */

		this.httpClientFactory = clientFactory;
		this.httpClient = this.httpClientFactory.createHttpClient();

		/*
		 * WARNING: In production, do not use this configuration.
		 * ALLOW_ALL_HOSTNAME_VERIFIER is not secure because it skips hostname
		 * verification. Instead, use:
		 * this.httpClientService.setAllowSSL(IHttpClient
		 * .sslType.ALLOW_DEFAULT_CERTS,
		 * SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		 */
		this.httpClient
				.setAllowSSL(
						IHttpClient.sslType.ALLOW_DEFAULT_CERTS,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	}

	/**
	 * Clear the dependency injection of IHttpClientFactory.
	 * 
	 * @param clientFactory
	 *            The IHttpClientFactory to clear.
	 */
	public void unsetHttpclient(IHttpClientFactory clientFactory) {
		if (this.httpClientFactory == clientFactory) {
			this.httpClientFactory.deleteHttpClient(this.httpClient);
			this.httpClient = null;
			this.httpClientFactory = null;
		}
	}

	/**
	 * @return the IHttpClient supports Predix Cloud authenticated
	 *         communication.
	 */
	public IHttpClient getCloudHttpClient() {
		return this.cloudHttpClient;
	}

	/**
	 * Dependency injection for IPredixCloudHttpClientFactory
	 * 
	 * @param clientFactory
	 *            The IPredixCloudHttpClientFactory to inject
	 */
	@Reference(type = '?')
	public void setPredixCloudHttpClientFactory(
			IPredixCloudHttpClientFactory clientFactory) {
		this.cloudHttpClientFactory = clientFactory;
		try {
			this.cloudHttpClient = this.cloudHttpClientFactory
					.createPredixCloudHttpClient();
		} catch (ConfigurationException e) {
			_logger.error(
					"Error occured in creating authenticated HTTP client", e); //$NON-NLS-1$
		}
	}

	/**
	 * Clear the injected IPredixCloudHttpClientFactory
	 * 
	 * @param clientFactory
	 *            The factory to clear.
	 */
	public void unsetPredixCloudHttpClientFactory(
			IPredixCloudHttpClientFactory clientFactory) {
		if (this.cloudHttpClientFactory == clientFactory) {
			this.cloudHttpClientFactory.deleteHttpClient(this.cloudHttpClient);
			this.cloudHttpClient = null;
			this.cloudHttpClientFactory = null;
		}
	}

	/**
	 * Dependency injection of IHttpClientSampleRestServer.
	 * 
	 * @param restServer
	 *            Not used.
	 */
	@Reference
	public void setHttpClientSampleRestServer(
			IHttpClientSampleRestServer restServer) {
		// Empty method. This is to wait for RestServer to start first.
	}
}
