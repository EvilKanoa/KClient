package ca.kanoa.kclient.hooks;

import ca.kanoa.kclient.KClient;
import net.minecraft.client.gui.GuiChat;

public class GuiChatHook extends GuiChat {
	
	public GuiChatHook() {
		super();
	}
	
	public GuiChatHook(String par1str) {
		super(par1str);
	}
	
	@Override
	public void func_146403_a(String par1str) {
		if (!KClient.getInstance().getModuleManager().getCommandHandler().execute(par1str)) {
			super.func_146403_a(par1str);
		}
	}

}
