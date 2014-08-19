package ca.kanoa.kclient;

import java.util.List;
import java.util.Set;

import ca.kanoa.kclient.command.CommandHandler;

public interface ModuleManager {
	
	public void reset();
	
	public void tick();
	
	public void render();
	
	public void setup();
	
	public void addModule(Module module);
	
	public void removeModule(Module module);
	
	public Set<Module> getModules();
	
	public Module getModule(String name);
	
	public Module getModuleFromDescription(String description);
	
	public Module getModule(Class<? extends Module> module);
	
	public CommandHandler getCommandHandler();
	
}
