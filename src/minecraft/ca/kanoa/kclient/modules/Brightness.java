package ca.kanoa.kclient.modules;

import java.lang.reflect.Field;
import java.util.HashMap;

import ca.kanoa.kclient.KClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class Brightness extends AbstractModule {
	
	@Override
	public void initialize() {
		setName("brightness");
		setDescription("Brightness");
		setCategory(Category.WORLD);
	}

	@Override
	public void tick() {
		KClient.getBridge().addPotionEffect(new PotionEffect(16, Integer.MAX_VALUE));
	}
	
//	@Override
//	public void onEnable() {
//		/*
//		try {
//			Field activePotionsMapField = EntityLivingBase.class.getDeclaredField("activePotionsMap");
//			activePotionsMapField.setAccessible(true);
//			System.out.println(activePotionsMapField.getType().getName());
//			activePotionsMap = (HashMap) activePotionsMapField.get(Minecraft.getMinecraft().thePlayer);
//			activePotionsMapField.setAccessible(false);
//		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//			sendMessage("Error while enabling: " + e.getMessage());
//			disable();
//		}
//		*/
//	}
	
	@Override
	public void onDisable() {
		KClient.getBridge().removePotionEffect(16);
		sendMessage("disabled");
	}
	
	@Override
	public void render() {}

}
