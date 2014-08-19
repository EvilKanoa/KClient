package ca.kanoa.kclient.scheduler;

public interface Task {
	
	public void run();
	
	public void cancel();
	
	public int getTaskId();
	
	public boolean isRepeating();
	
	public long getPeriod();
	
	public boolean isCancelled();
	
}
