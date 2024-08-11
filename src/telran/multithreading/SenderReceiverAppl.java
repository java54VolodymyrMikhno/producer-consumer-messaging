package telran.multithreading;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.IntStream;

public class SenderReceiverAppl {
	private static int N_MESSAGES = 2000;
	private static int N_RECEIVER = 10;

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> evenMessageBox = new LinkedBlockingDeque<String>();
		BlockingQueue<String> oddMessageBox = new LinkedBlockingDeque<String>();
		ProducerSender sender = startSender(evenMessageBox, oddMessageBox, N_MESSAGES);
		ConsumerReceiver[] receivers = startReceivers(evenMessageBox, oddMessageBox, N_RECEIVER);
		sender.join();
		stopReceivers(receivers);
		displayResult();
	}

	private static void displayResult() {
		System.out.printf("counter of processed messages is %d\n", ConsumerReceiver.getMessageCounter());

	}

	private static void stopReceivers(ConsumerReceiver[] receivers) throws InterruptedException {
		for (ConsumerReceiver receiver : receivers) {
			receiver.interrupt();
			receiver.join();
		}

	}

	private static ConsumerReceiver[] startReceivers(BlockingQueue<String> evenMessageBox,
			BlockingQueue<String> oddMessageBox, int nReceivers) {

		ConsumerReceiver[] receivers = IntStream.range(0, nReceivers).mapToObj(i -> {
			ConsumerReceiver reseiver = new ConsumerReceiver();
			if (reseiver.getId()%2!=0) {
				reseiver.setMessagebox(oddMessageBox);
			} else {
				reseiver.setMessagebox(evenMessageBox);
			}
			return reseiver;
		}).toArray(ConsumerReceiver[]::new);
		Arrays.stream(receivers).forEach(ConsumerReceiver::start);
		return receivers;
	}

	private static ProducerSender startSender(BlockingQueue<String> evenMessageBox, BlockingQueue<String> oddMessageBox,
			int nMessages) {
		ProducerSender sender = new ProducerSender(evenMessageBox, oddMessageBox, nMessages);
		sender.start();
		return sender;
	}

}
