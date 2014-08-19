package ca.kanoa.kclient.bridge;

import net.minecraft.potion.PotionEffect;

/**
 * <p>This is all the methods that anything from KClient will use from Vanilla Minecraft. 
 * All methods should be bridged through a class for each version of Minecraft.</p>
 * <p>This will allow KClient to run on multiple versions of Minecraft with a little reflection magic. 
 * This will also allow KClient to be updated to the newest version of Minecraft very easily</p>
 * @author Kanoa
 *
 */
public interface Bridge {

	public void addPotionEffect(PotionEffect effect);
	
	public void removePotionEffect(int effectId);
	
}
