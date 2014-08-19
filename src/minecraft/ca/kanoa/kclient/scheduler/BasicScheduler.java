package ca.kanoa.kclient.scheduler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BasicScheduler implements Scheduler {

	private Map<Task, Long> queue;
	private int nextId;
	
	public BasicScheduler() {
		this.queue = new HashMap<Task, Long>();
		this.nextId = 0;
	}
	
	@Override
	public int scheduleTask(Runnable task, long delay) {
		queue.put(new BasicTask(task, ++nextId), System.currentTimeMillis() + delay);
		return nextId;
	}

	@Override
	public int scheduleRepeatingTask(Runnable task, long delay, long period) {
		queue.put(new BasicTask(task, period, ++nextId), System.currentTimeMillis() + delay);
		return nextId;
	}

	@Override
	public void cancelTask(int taskId) {
		for (Task task : queue.keySet()) {
			if (task.getTaskId() == taskId) {
				task.cancel();
			}
		}
	}

	@Override
	public void cancelTask(Task task) {
		task.cancel();
	}

	@Override
	public void cancelAllTasks() {
		for (Task task : queue.keySet()) {
			task.cancel();
		}
	}

	@Override
	public void tick() {
		Set<Task> temp = new HashSet<Task>();
		for (Task task : queue.keySet()) {
			if (task.isCancelled()) {
				temp.add(task);
				continue;
			}
			if (System.currentTimeMillis() > queue.get(task)) {
				task.run();
				if (task.isRepeating()) {
					queue.put(task, System.currentTimeMillis() + task.getPeriod());
				} else {
					temp.add(task);
				}
			}
		}
		for (Task task : temp) {
			queue.remove(task);
		}
	}

	@Override
	public Task getTask(int taskId) {
		for (Task task : queue.keySet()) {
			if (task.getTaskId() == taskId) {
				return task;
			}
		}
		return new BasicTask(null, -1);
	}

}
