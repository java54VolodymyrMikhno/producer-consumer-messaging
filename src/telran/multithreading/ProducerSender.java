package telran.multithreading;

import java.util.concurrent.*;
import java.util.stream.*;

public class ProducerSender extends Thread {
	private BlockingQueue<String> evenMessageBox;
	private BlockingQueue<String> oddMessageBox;
	private int nMessage;

	

	public ProducerSender(BlockingQueue<String> evenmMessageBox, BlockingQueue<String> oddMessageBox, int nMessage) {
		
		this.evenMessageBox = evenmMessageBox;
		this.oddMessageBox = oddMessageBox;
		this.nMessage = nMessage;
	}



	public void run() {
		IntStream.rangeClosed(1, nMessage).forEach(i -> {
			try {
				String message = "message"+i;
				if(i%2==0) {
					evenMessageBox.put(message);
				}else {
					oddMessageBox.put(message);
				}
				
			} catch (InterruptedException e) {
				// no interrupt logics
			}
		});

	}
}
