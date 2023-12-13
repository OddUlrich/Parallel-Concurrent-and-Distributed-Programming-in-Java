package org.oo.demo.pseudo.test;

import junit.framework.TestCase;

public class BoundedBufferTest extends TestCase {

    private final static long LOCKUP_DETECT_TIMEOUT = 10;

    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    public void testFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

    public void testTaskBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread(() -> {
            try {
                int unused = bb.take();
                fail();
            } catch (InterruptedException ignored) {
            }
        });
        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            fail();
        }
    }

}
