package com.palg.tp;

import com.palg.tp.listener.SessionListener;
import com.palg.tp.persistence.PersistentObjects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TpApplication.class)
public class SessionListenerTest implements SessionListener
{

	private static final Logger logger = LoggerFactory.getLogger(SessionListenerTest.class);

	@Value("${palg.listener.period}")
	private Long loopThread;
	private static final long key1 = 1;
	private static final long timeOut1 = 5000;
	
	private static final long key2 = 2;
	private static final long timeOut2 = 10000;
	
	private int session1Opened=0;
	private int session2Opened=0;	
	private int session1StillOpened=0;
	private int session2StillOpened=0;
	
	@Autowired
	private PersistentObjects po;

	@BeforeEach
	public void init()
	{
		po.addListener(this);
		po.createSession(key1,timeOut1);
		po.createSession(key2,timeOut2);
		esperar(loopThread);
	}
	
	@AfterEach
	public void destroy()
	{
		po.destroySession(key1);
		po.destroySession(key2);
		po.removeListener(this);
	}
	
	@Test
	public void testSessionOpenedClosed() {
		assertEquals(1, session1Opened);
		assertEquals(1, session2Opened);

		esperar(timeOut1/2);
		assertEquals(1,session1Opened);
		assertEquals(1,session2Opened);

		esperar((timeOut1/2)+loopThread*2);
		assertEquals(0,session1Opened);
		assertEquals(1,session2Opened);

		long restaEsperar = Math.abs(timeOut1-timeOut2)+loopThread*2;
		esperar(restaEsperar);
		assertEquals(0,session1Opened);
		assertEquals(0,session2Opened);
	}
	
	@Test
	public void testSessionStillOpenedClosed()
	{
		assertEquals(1, session2Opened);

		// no deberia haberse llamado a sessionSTillOpen
		int i=0;
		assertEquals(i, session2StillOpened);

		long acum = 0;
		while( acum<(timeOut2-loopThread) )
		{
			// espero, aun debe estar abierta
			esperar(loopThread);

			i++;

			logger.info("[SessionListenerTest] while loop -> i=%d, session2StillOpened=%d".formatted(i, session2StillOpened));
			assertEquals(i, session2StillOpened);
			
			acum+=loopThread+100;

			logger.info("[SessionListenerTest] while loop -> checking acum<(timeOut2-loopThread) -> %d<(%d-%d=%d)".formatted(acum, timeOut2, loopThread, timeOut2-loopThread));
		}

		esperar(loopThread);

		// expiro el tiempo, paso a closed
		assertEquals(0, session2Opened);

		// espero un loop, debe seguir en closed
		esperar(loopThread+100);
		assertEquals(i-1, session2StillOpened);
	}
		
	@Override
	public void sessionOpened(long key)
	{
		logger.info("[SessionListenerTest] sessionOpened: %d".formatted(key));
		switch ((int) key) {
			case (int) key1 -> session1Opened++;
			case (int) key2 -> session2Opened++;
		}
		printCounters();
	}

	@Override
	public void sessionStillOpened(long key)
	{
		logger.info("[SessionListenerTest] sessionStillOpened: %d".formatted(key));
		switch ((int) key) {
			case (int) key1 -> session1StillOpened++;
			case (int) key2 -> session2StillOpened++;
		}
		printCounters();
	}
	

	@Override
	public void sessionClosed(long key)
	{
		logger.info("[SessionListenerTest] sessionClosed: %d".formatted(key));
		switch ((int) key) {
			case (int) key1 -> session1Opened--;
			case (int) key2 -> session2Opened--;
		}
		printCounters();
	}

	@Override
	public void sessionStillClosed(long key)
	{
		logger.info("[SessionListenerTest] sessionStillClosed: %d".formatted(key));
		switch ((int) key) {
			case (int) key1 -> session1StillOpened--;
			case (int) key2 -> session2StillOpened--;
		}
		printCounters();
	}
	
	public void esperar(long n)
	{
		try
		{
			logger.info("[SessionListenerTest] sleeping for %dms".formatted(n));
			Thread.sleep(n);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void printCounters() {
		logger.info("[SessionListenerTest] {session1Opened=%d, session2Opened=%d, session1StillOpened=%d, session2StillOpened=%d}".formatted(session1Opened, session2Opened, session1StillOpened, session2StillOpened));
	}
}
