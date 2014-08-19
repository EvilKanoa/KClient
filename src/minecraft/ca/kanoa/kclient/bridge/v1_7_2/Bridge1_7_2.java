package ca.kanoa.kclient.bridge.v1_7_2;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.PotionEffect;
import ca.kanoa.kclient.bridge.Bridge;

public class Bridge1_7_2 implements Bridge {

	@Override
	public void addPotionEffect(PotionEffect effect) {
		Minecraft.getMinecraft().thePlayer.addPotionEffect(effect);
	}

	@Override
	public void removePotionEffect(int effectId) {
		Minecraft.getMinecraft().thePlayer.removePotionEffect(effectId);
	}

}
