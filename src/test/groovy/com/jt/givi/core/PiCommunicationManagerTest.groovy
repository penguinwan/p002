package groovy.com.jt.givi.core

import com.jt.givi.core.PiCommunicationManager
import org.junit.Before
import org.junit.Test

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

import static org.junit.Assert.*

/**
 * Created by superman on 11/21/2015.
 */
class PiCommunicationManagerTest {
    @Before
    void setup() {
        System.setProperty("skipPi", "true")
    }

    @Test
    void testPollWithRetry_allRetryTimeout() {
        def piComMgr = new PiCommunicationManager(2000, 0, 3)
        def envelope = piComMgr.pollWithRetry()
        assertNull(envelope)
    }

    @Test
    void testPollWithRetry_secondRetrySuccess() {
        def piComMgr = new PiCommunicationManager(5000, 0, 3)
        def received
        def startPolling = {
            println "starting polling..."
            received = piComMgr.pollWithRetry()
            println "received ${received}"
        } as Runnable

        def putData = {
            println "starting..."
            Thread.sleep(3000)
            println "put data..."
            piComMgr.dataQueue.put(new PiCommunicationManager.Envelope())
        } as Runnable

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.schedule(startPolling, 0, TimeUnit.MILLISECONDS)
        scheduler.schedule(putData, 0, TimeUnit.MILLISECONDS)
        scheduler.awaitTermination(10000, TimeUnit.MILLISECONDS)

        assertNotNull(received)
    }

    @Test
    void testPollWithRetry_firstSuccess() {
        def piComMgr = new PiCommunicationManager(5000, 0, 3)
        def received
        def startPolling = {
            println "starting polling..."
            received = piComMgr.pollWithRetry()
            println "received ${received}"
        } as Runnable

        def putData = {
            println "starting..."
            println "put data..."
            piComMgr.dataQueue.put(new PiCommunicationManager.Envelope())
        } as Runnable

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.schedule(startPolling, 0, TimeUnit.MILLISECONDS)
        scheduler.schedule(putData, 0, TimeUnit.MILLISECONDS)
        scheduler.awaitTermination(2000, TimeUnit.MILLISECONDS)

        assertNotNull(received)
    }
}
