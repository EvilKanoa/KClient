package ca.kanoa.kclient.command;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.entity.EntityClientPlayerMP;

public class CommandHandler {
	
	private Set<CommandExecutor> executors;
	private String prefix; //Used in front of a command like "-help" or ".module"
	
	public CommandHandler(String prefix) {
		executors = new HashSet<CommandExecutor>();
		this.prefix = prefix;
	}
	
	public void register(CommandExecutor executor) {
		executors.add(executor);
	}
	
	public void unregister(CommandExecutor executor) {
		executors.remove(executor);
	}
	
	public boolean isRegistered(CommandExecutor executor) {
		return executors.contains(executor);
	}
	
	/**
	 * Runs the chat message through the command executor if it has the command prefix
	 * @param cmd The string from chat
	 * @return True if it's a command, false if it's just a chat message
	 */
	public boolean execute(String cmd) {
		if (!cmd.startsWith(prefix)) {
			return false;
		}
		for (CommandExecutor ce : executors) {
			execute(cmd, ce);
		}
		return true;
	}
	
	private void execute(String str, CommandExecutor executor) {
		str = str.substring(1).trim();
		String cmd;
		String[] args;
		if (str.contains(" ")) {
			cmd = str.split(" ")[0].trim();
			args = str.replaceFirst(cmd, "").trim().split(" ");
		} else {
			if (str.length() >= 1) {
				cmd = str;
			} else {
				cmd = "null";
			}
			args = new String[0];
		}
		executor.run(cmd, args);
	}
	
}
