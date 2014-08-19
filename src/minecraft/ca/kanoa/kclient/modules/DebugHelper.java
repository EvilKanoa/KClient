package ca.kanoa.kclient.modules;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import ca.kanoa.kclient.KClient;
import ca.kanoa.kclient.settings.Setting;
import ca.kanoa.kclient.settings.SettingsHandler;
import ca.kanoa.kclient.settings.TextSettings;
import ca.kanoa.kclient.util.KeyboardUtil;

public class DebugHelper extends AbstractModule {
	
	private int taskId;
	
	@Override
	public void tick() {
		sendMessage(MinecraftServer.getServer().getMinecraftVersion());
	}

	@Override
	public void render() {}

	@Override
	public void initialize() {
		setName("debug");
		setDescription("Debug Helper");
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {}

	@Override
	public void command(String[] args) {}

}
