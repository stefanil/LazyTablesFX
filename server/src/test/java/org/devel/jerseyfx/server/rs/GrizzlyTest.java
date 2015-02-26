package org.devel.jerseyfx.server.rs;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

public abstract class GrizzlyTest {

	private HttpServer server;
	protected WebTarget target;

	public GrizzlyTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		startGrizzly();
		target = createWebTarget();
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	private void startGrizzly() throws InterruptedException {
		server = createServer();
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(
					"There was an error while starting Grizzly HTTP server.",
					false);
		} finally {
			if (!server.isStarted())
				assertTrue(false);
		}
	}

	protected abstract HttpServer createServer();
	
	protected abstract WebTarget createWebTarget();

}