package ca.kanoa.kclient.modules;

import ca.kanoa.kclient.KClient;
import ca.kanoa.kclient.Module;
import ca.kanoa.kclient.command.CommandExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public abstract class AbstractModule implements CommandExecutor, Module {

	private String name;
	private String description;
	private boolean enabled;
	private Category category;
	private boolean toggleable;
	private boolean visible;
	
	public AbstractModule() {
		this.enabled = false;
		this.category = Category.OTHER;
		this.toggleable = true;
		this.visible = true;
	}
	
	public abstract void initialize();
	
	public abstract void tick();
	
	public abstract void render();

	public void command(String[] args) {
		sendMessage("." + getName() + " toggle");
		sendMessage("." + getName() + " enable");
		sendMessage("." + getName() + " disable");
		sendMessage("." + getName() + " status");
	}
	
	public void tickDisabled() {}
	
	public void setup() {}
	
	public void renderDisabled() {}
	
	public void tickOverride() {}
	
	public void renderOverride() {}
	
	public void onEnable() {}
	
	public void onDisable() {}
	
	public void toggle() {
		if (isEnabled()) {
			disable();
		} else {
			enable();
		}
	}
	
	public void enable() {
		if (!enabled) {
			enabled = true;
			onEnable();
		}
	}
	
	public void disable() {
		if (enabled) {
			enabled = false;
			onDisable();
		}
	}
	
	protected void sendMessage(String message) {
		KClient.sendMessage(this, message);
	}
	
	@Override
	public void run(String cmd, String[] args) {
		if (cmd.equalsIgnoreCase(getName())) {
			if (args.length == 0) {
				toggle();
				sendMessage("Toggled");
			} else {
				switch (args[0]) {
				case "toggle":
					sendMessage("Toggled");
					toggle();
					break;
				case "enable":
					sendMessage("Enabled");
					enable();
					break;
				case "disable":
					sendMessage("Disabled");
					disable();
					break;
				case "status":
					sendMessage(isEnabled() ? "Enabled" : "Disabled");
					break;
				default:
					command(args);
				}
			}
		}
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isToggleable() {
		return toggleable;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public String getName() {
		if (name == null) {
			name = getClass().getSimpleName();
		}
		return name;
	}
	
	public String getDescription() {
		if (description == null) {
			description = getName();
		}
		return description;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Category getCategory() {
		return category;
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
}
