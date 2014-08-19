package ca.kanoa.kclient.managers;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import ca.kanoa.kclient.Module;
import ca.kanoa.kclient.ModuleManager;
import ca.kanoa.kclient.command.CommandExecutor;
import ca.kanoa.kclient.command.CommandHandler;
import ca.kanoa.kclient.util.KeyboardUtil;

public class BasicModuleManager implements ModuleManager {

	private Set<Module> modules;
	private CommandHandler commandHandler;
	
	public BasicModuleManager() {
		this.modules = new HashSet<Module>();
		this.commandHandler = new CommandHandler(".");
	}

	@Override
	public Set<Module> getModules() {
		return this.modules;
	}

	@Override
	public void addModule(Module module) {
		modules.add(module);
		commandHandler.register(module);
	}

	@Override
	public void removeModule(Module module) {
		modules.remove(module);
		commandHandler.unregister(module);
	}

	@Override
	public void reset() {
		modules.clear();
	}

	@Override
	public void tick() {
		for (Module module : modules) {
			if (module.isEnabled()) {
				module.tick();
			} else {
				module.tickDisabled();
			}
			module.tickOverride();
		}
	}

	@Override
	public void render() {
		for (Module module : modules) {
			if (module.isEnabled()) {
				module.render();
			} else {
				module.renderDisabled();
			}
			module.renderOverride();
		}
	}

	@Override
	public void setup() {
		for (Module module : modules) {
			module.initialize();
		}
		for (Module module : modules) {
			module.setup();
		}
	}

	@Override
	public CommandHandler getCommandHandler() {
		return this.commandHandler;
	}

	@Override
	public Module getModule(String name) {
		for (Module module : modules) {
			if (module.getName().trim().equalsIgnoreCase(name.trim())) {
				return module;
			}
		}
		return null;
	}

	@Override
	public Module getModule(Class<? extends Module> moduleClass) {
		for (Module module : modules) {
			if (module.getClass() == moduleClass) {
				return module;
			}
		}
		return null;
	}

	@Override
	public Module getModuleFromDescription(String description) {
		for (Module module : modules) {
			if (module.getDescription().trim().equalsIgnoreCase(description.trim())) {
				return module;
			}
		}
		return null;
	}

}
