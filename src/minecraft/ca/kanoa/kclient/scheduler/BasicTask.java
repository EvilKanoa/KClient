package ca.kanoa.kclient.scheduler;

public class BasicTask implements Task {

	private Runnable task;
	private long period;
	private boolean cancelled;
	private int taskId;
	
	public BasicTask(Runnable task, int taskId) {
		this(task, -1, taskId);
	}
	
	public BasicTask(Runnable task, long period, int taskId) {
		this.task = task;
		this.period = period;
		this.taskId = taskId;
		this.cancelled = false;
	}
	
	@Override
	public void cancel() {
		this.cancelled = true;
	}

	@Override
	public int getTaskId() {
		return this.taskId;
	}

	@Override
	public boolean isRepeating() {
		return this.period > -1;
	}

	@Override
	public long getPeriod() {
		return this.period;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void run() {
		task.run();
	}

}
