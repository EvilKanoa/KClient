package ca.kanoa.kclient;

import ca.kanoa.kclient.command.CommandExecutor;
import ca.kanoa.kclient.modules.Category;

public interface Module extends CommandExecutor {
	
	/**
	 * Gets called once when the game starts after initialization occurs
	 */
	public abstract void setup();
	
	/**
	 * Gets called when the game starts <br>
	 * Use for setting names, descriptions, etc
	 */
	public abstract void initialize();
	
	/**
	 * Gets called once every game loop
	 * <br>
	 * Use this to control the logic
	 * <br>
	 * This is not guaranteed to have the same timing as an in-game "tick"
	 * <br>
	 * If you need something to be timed with a games tick, use a scheduler.
	 */
	public abstract void tick();
	
	/**
	 * Used to render stuff onscreen
	 * <br>
	 * Will overlay normal minecraft things
	 */
	public abstract void render();

	/**
	 * A chat command directed towards this module
	 * <br>
	 * IE; .modulename [arguments]
	 * @param args The arguments the user passed
	 */
	public void command(String[] args);
	
	/**
	 * Similar to {@link #tick()} with the following differences:
	 * <br>
	 * Only gets called when the module is disabled
	 */
	public void tickDisabled();
	
	/**
	 * Similar to {@link #render()} with the following differences:
	 * <br>
	 * Only gets called when the module is disabled
	 */
	public void renderDisabled();
	
	/**
	 * Similar to {@link #tick()} with the following differences:
	 * <br>
	 * Gets called no matter if the module is enabled or disabled
	 */
	public void tickOverride();
	
	/**
	 * Similar to {@link #render()} with the following differences:
	 * <br>
	 * Gets called no matter if the module is enabled or disabled
	 */
	public void renderOverride();
	
	/**
	 * Gets called when this module is enabled
	 */
	public void onEnable();
	
	/**
	 * Gets called when this module is disabled
	 */
	public void onDisable();
	
	/**
	 * Toggles this module
	 */
	public void toggle();
	
	/**
	 * Enables this module
	 */
	public void enable();
	
	/**
	 * Disables this module
	 */
	public void disable();
	
	/**
	 * Run a KClient command on this module
	 * @param cmd The command that was ran
	 * @param args The arguments of the command
	 */
	public void run(String cmd, String[] args);
	
	public boolean isEnabled();
	
	/**
	 * Gets the name of the module.
	 * <br>
	 * The name is used in commands and other info, it should not
	 * be used when displaying things. Use {@link #getDescription()} instead.
	 * <br>
	 * The name is often similar to the class name of the module.
	 * @return This modules name
	 */
	public String getName();
	
	/**
	 * Gets the "friendly name" of this module
	 * <br>
	 * This should be used in GUIs, chat messages, and other locations where 
	 * it's displayed to the user.
	 * @return This modules "friendly name"
	 */
	public String getDescription();
	
	/**
	 * Gets this modules category
	 * @return The category of this module
	 */
	public Category getCategory();
	
	/**
	 * Checks if this module is toggleable with commands or in GUIs.
	 * @return Whether this module is togglable
	 */
	public boolean isToggleable();
	
	/**
	 * Checks if this module is visible in GUIs
	 * @return Whether this module is visible
	 */
	public boolean isVisible();
	
	public void setDescription(String description);
	
	public void setName(String name);
	
	public void setCategory(Category category);
	
	public void setToggleable(boolean toggleable);
	
	public void setVisible(boolean visible);
	
}
