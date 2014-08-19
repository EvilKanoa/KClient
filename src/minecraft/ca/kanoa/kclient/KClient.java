package ca.kanoa.kclient;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import ca.kanoa.kclient.bridge.Bridge;
import ca.kanoa.kclient.modules.*;
import ca.kanoa.kclient.managers.BasicModuleManager;
import ca.kanoa.kclient.modules.Brightness;
import ca.kanoa.kclient.modules.ClickGui;
import ca.kanoa.kclient.modules.DebugHelper;
import ca.kanoa.kclient.modules.Keybinds;
import ca.kanoa.kclient.modules.Login;
import ca.kanoa.kclient.modules.StripMine;
import ca.kanoa.kclient.modules.TextRader;
import ca.kanoa.kclient.scheduler.BasicScheduler;
import ca.kanoa.kclient.scheduler.Scheduler;

public class KClient {
	
	/** Used to store a static copy of the KClient instace **/
	private static KClient _instance;
	private static final String NAME = "KClient";
	
	private ModuleManager moduleManager;
	private Scheduler scheduler;
	private Bridge bridge;

	public KClient() {
		_instance = this;
		try {
			String bridgeVersion = "ca.kanoa.kclient.bridge.v{version}.Bridge{version}";
			bridgeVersion = bridgeVersion.replace("{version}", Display.getTitle().substring(10).replace('.', '_'));
			System.out.println( Display.getTitle().substring(10).replace('.', '_'));
			bridge = (Bridge) Class.forName(bridgeVersion).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("KClient version bridge invalid. KClient cannot be started!");
			System.out.println("Disable KClient or fix bridge error to start.");
			System.exit(-1);
		}
	}
	
	public void setup() {
		moduleManager = new BasicModuleManager();
		addModules();
		moduleManager.setup();
		scheduler = new BasicScheduler();
		//Minecraft.getMinecraft().fontRenderer = Minecraft.getMinecraft().standardGalacticFontRenderer;
	}
	
	private void addModules() {
		moduleManager.addModule(new Keybinds());
		moduleManager.addModule(new ClickGui());
		moduleManager.addModule(new DebugHelper());
		moduleManager.addModule(new Brightness());
		moduleManager.addModule(new Login());
		moduleManager.addModule(new TextRader());
		moduleManager.addModule(new StripMine());
	}

	public static ModuleManager getModuleManager() {
		return _instance.moduleManager;
	}
	
	public static Scheduler getScheduler() {
		return _instance.scheduler;
	}
	
	public static Bridge getBridge() {
		return _instance.bridge;
	}
	
	public void tick() {
		this.moduleManager.tick();
		this.scheduler.tick();
	}
	
	public static KClient getInstance() {
		return _instance;
	}
	
	public static void sendMessage(Module module, String message) {
		String raw = "&c[&e{module}&c] &r{message}";
		sendMessage(raw.replace("{module}", module.toString())
				.replace("{message}", message));
	}
	
	public static void sendMessage(String message) {
		String raw = "&5[&b{client}&5] &r{message}";
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
				raw.replace("{client}", KClient.NAME)
				.replace("{message}", message).replace('&', '\u00a7')));
	}
	
}
