package ca.kanoa.kclient.modules;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.lwjgl.input.Keyboard;

import ca.kanoa.kclient.KClient;
import ca.kanoa.kclient.Module;
import ca.kanoa.kclient.gui.MenuGuiManager;
import ca.kanoa.kclient.keybinds.KeybindButton;
import ca.kanoa.kclient.keybinds.KeybindButtonListener;
import ca.kanoa.kclient.keybinds.KeybindGuiManager;
import ca.kanoa.kclient.keybinds.KeybindSettingsHandler;
import ca.kanoa.kclient.settings.SettingsHandler;
import ca.kanoa.kclient.settings.TextSettings;
import ca.kanoa.kclient.util.KeyboardUtil;

public class Keybinds extends AbstractModule {

	public static final File KEYBIND_FILE = new File(Minecraft.getMinecraft()
			.mcDataDir, "keybinds.txt");
	
	private GuiManager manager;
	private GuiScreen screen;
	private KeybindButtonListener listener;
	private KeybindSettingsHandler settingsHandler;
	private boolean listening;
	private KeybindButton listeningButton;
	private static Keybinds _instance;
	
	/** Used to store the keybind associated with each module */
	private Map<String, Integer> binds;

	@Override
	public void initialize() {
		_instance = this;
		
		setName("bind");
		setDescription("Keybinds");
		setVisible(false);
	}

	@Override
	public void setup() {
		settingsHandler = new KeybindSettingsHandler(this);
		TextSettings.loadSettings(settingsHandler, KEYBIND_FILE);
		listener = new KeybindButtonListener(this);
		//GUI setup
		manager = new KeybindGuiManager(this);
		manager.setTheme(new SimpleTheme());
		manager.setup();
		screen = new GuiManagerDisplayScreen(manager);

		listening = false;
	}
	
	@Override
	public void onEnable() {
		TextSettings.loadSettings(settingsHandler, KEYBIND_FILE);
		Minecraft.getMinecraft().displayGuiScreen(screen);
	}
	
	@Override
	public void onDisable() {
		TextSettings.saveSettings(settingsHandler, KEYBIND_FILE);
		Minecraft.getMinecraft().displayGuiScreen(null);
	}
	
	@Override
	public void tick() {
		if (Minecraft.getMinecraft().currentScreen != screen ||
				KeyboardUtil.keyPressedGui(Keybinds.getInstance().getKey(getName()))) {
			disable();
		}
		
		if (listening) {
			for (int key = 0; key <= 223; key++) {
				if (KeyboardUtil.keyPressedGui(key)) {
					setKey(key);
					listening = false;
					listeningButton = null;
					return;
				}
			}
		}
	}
	
	@Override
	public void tickOverride() {
		for (String module : binds.keySet()) {
			if (KeyboardUtil.keyPressed(binds.get(module))) {
				Module moduleInstance = KClient.getInstance().getModuleManager().getModule(module);
				if (moduleInstance != null) {
					moduleInstance.toggle();
				}
			}
		}
		manager.update();
	}

	@Override
	public void render() {
		manager.render();
	}
	
	@Override
	public void command(String[] args) {
		if (args.length == 1) {
			Module module = KClient.getInstance().getModuleManager().getModule(args[0]);
			if (module == null) {
				sendMessage("No module found by name \"" + args[0] + "\"!");
				return;
			}
			TextSettings.loadSettings(settingsHandler, KEYBIND_FILE);
			sendMessage("Module \"" + module.getDescription() + "\" is bound to: " + 
					KeyboardUtil.getShortenedKeyName(getKey(module.getName())));
		} else if (args.length == 2) {
			Module module = KClient.getInstance().getModuleManager().getModule(args[0]);
			if (module == null) {
				sendMessage("No module found by name \"" + args[0] + "\"!");
				return;
			}
			setKey(module.getName(), Keyboard.getKeyIndex(args[1].toUpperCase()));
			TextSettings.saveSettings(settingsHandler, KEYBIND_FILE);
			sendMessage("Module \"" + module.getDescription() + "\" has been bound to: " + 
					KeyboardUtil.getShortenedKeyName(getKey(module.getName())));
		} else {
			sendMessage("Invalid command!\n.bind <module> [key]");
		}
	}
	
	public Set<Module> getModulesForKey(int key) {
		Set<Module> modules = new HashSet<Module>();
		for (Entry<String, Integer> entry : binds.entrySet()) {
			if (entry.getValue() == key) {
				Module module = KClient.getInstance().getModuleManager()
						.getModule(entry.getKey());
				if (module != null) {
					modules.add(module);
				}
			}
		}
		return modules;
	}
	
	public void listen(KeybindButton button) {
		this.listening = true;
		this.listeningButton = button;
	}
	
	public int getKey(String moduleName) {
		return binds.containsKey(moduleName) ? binds.get(moduleName) : Keyboard.KEY_NONE;
	}
	
	private void setKey(int key) {
		binds.put(listeningButton.getModule(), key);
		listeningButton.setBackgroundColor(Color.gray);
		listeningButton.update();
	}
	
	public void setKey(String moduleName, int key) {
		binds.put(moduleName, key);
	}
	
	public Map<String, Integer> getBinds() {
		return binds;
	}
	
	public void setBinds(Map<String, Integer> binds) {
		this.binds = binds;
	}
	
	public ButtonListener getListener() {
		return listener;
	}
	
	public boolean isListening() {
		return listening;
	}
	
	public static Keybinds getInstance() {
		return _instance;
	}

}