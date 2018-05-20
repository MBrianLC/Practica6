package es.ucm.fdi.view;

import java.util.logging.Logger;

public class Stepper {
	private Runnable before;
	private Runnable during;
	private Runnable after;
	private int steps;
	private volatile boolean stopRequested = false;
	private static final Logger logger = Logger.getLogger(Stepper.class.getName());

	public Stepper(Runnable before, Runnable during, Runnable after){
		this.before = before;
		this.during = during;
		this.after = after;
	}

	public Thread start (int steps, int delay){
		this.steps = steps;
		stopRequested = false;
		Thread t = new Thread (() -> {
			try {
				before.run();
				while ( !stopRequested && Stepper.this.steps > 0){
					during.run();
					try {
						Thread.sleep(delay);
					} catch (InterruptedException ie){
						// ignore and continue
					}
					Stepper.this.steps--;
				}
			} catch (Exception e) {
				logger.warning("Exception while stepping, " + this.steps + " remaining: " + e);
			} finally {
				after.run ();
			}
		});
		t.start ();
		return t;
	}
	
	public void stop() {
		stopRequested = true;
	}
}
