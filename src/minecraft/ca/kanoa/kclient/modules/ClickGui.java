package ca.kanoa.kclient.modules;

import java.awt.Color;

import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import ca.kanoa.kclient.KClient;
import ca.kanoa.kclient.gui.MenuGuiManager;
import ca.kanoa.kclient.util.KeyboardUtil;

public class ClickGui extends AbstractModule {

	private GuiManager manager;
	private GuiScreen screen;

	@Override
	public void initialize() {
		setName("gui");
		setDescription("ClickGui");
		setVisible(false);
	}

	@Override
	public void setup() {
		manager = new MenuGuiManager();
		manager.setTheme(new SimpleTheme());
		manager.setup();
		screen = new GuiManagerDisplayScreen(manager);
	}

	@Override
	public void onEnable() {
		Minecraft.getMinecraft().displayGuiScreen(screen);
	}

	@Override
	public void onDisable() {
		Minecraft.getMinecraft().displayGuiScreen(null);
	}

	@Override
	public void tick() {
		if (Minecraft.getMinecraft().currentScreen != screen ||
				KeyboardUtil.keyPressedGui(Keybinds.getInstance().getKey(getName()))) {
			disable();
		}
	}

	@Override
	public void tickOverride() {
		manager.update();
	}

	@Override
	public void renderDisabled() {
		if (Minecraft.getMinecraft().currentScreen == null) {
			manager.renderPinned();
		}
	}

	@Override
	public void render() {
		manager.render();
	}

}
