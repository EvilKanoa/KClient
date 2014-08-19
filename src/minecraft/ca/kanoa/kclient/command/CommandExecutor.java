package ca.kanoa.kclient.command;

import net.minecraft.client.entity.EntityClientPlayerMP;

public interface CommandExecutor {

	public void run(String cmd, String[] args);
	
}
