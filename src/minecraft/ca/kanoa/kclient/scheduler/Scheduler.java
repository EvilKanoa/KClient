package ca.kanoa.kclient.scheduler;

public interface Scheduler {

	/**
	 * Schedules a task to be executed in a set amount of time.
	 * @param task The task to be executed
	 * @param delay The amount of time to wait before executing the task
	 * @return The taskId of the task that was scheduled
	 */
	public int scheduleTask(Runnable task, long delay);
	
	/**
	 * Schedules a task to be repeatedly executed a certain amount of time apart.
	 * @param task The task to be executed
	 * @param delay The amount of time to wait before first execution
	 * @param period The amount of time between each execution
	 * @return The taskId of the task that was scheduled
	 */
	public int scheduleRepeatingTask(Runnable task, long delay, long period);
	
	/**
	 * Attempts to get the task for an ID
	 * @param taskId The ID of the task to get
	 * @return The task that has ID taskId
	 */
	public Task getTask(int taskId);
	
	/**
	 * Attempt to cancel a task
	 * @param taskId The id of the task to cancel
	 */
	public void cancelTask(int taskId);
	
	/**
	 * Attempt to cancel a task
	 * @param task The task to cancel
	 */
	public void cancelTask(Task task);
	
	/**
	 * Attempts to cancel all running and scheduled tasks
	 */
	public void cancelAllTasks();
	
	/**
	 * This method should get called as often as possible.
	 * <br>
	 * Updates the scheduler and executes tasks if they're scheduled.
	 */
	public void tick();
	
}
