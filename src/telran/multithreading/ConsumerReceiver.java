package telran.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class ConsumerReceiver extends Thread {
	private BlockingQueue<String> messageBox;
	private static AtomicLong messageCounter = new AtomicLong();

	public void setMessagebox(BlockingQueue<String> messageBox) {
		this.messageBox = messageBox;
	}

	public void run() {
		boolean running = true;
		try {
			while (running) {
				String message = messageBox.take();
				processMessage(message);
			}
		} catch (InterruptedException e) {
            running = false;
            if(!messageBox.isEmpty()) {
            	processMessage(messageBox.remove());
            }
		}
	}

	public static long getMessageCounter() {
		return messageCounter.get();
	}

	private void processMessage(String message) {
		System.out.printf("Thread %s id=%d %s\n", getName(),getId(), message);
		messageCounter.getAndIncrement();
	}
}
