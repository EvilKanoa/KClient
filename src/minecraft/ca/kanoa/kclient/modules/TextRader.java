package ca.kanoa.kclient.modules;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class TextRader extends AbstractModule {

	List<String> players;
	
	@Override
	public void initialize() {
		setName("textrader");
		setDescription("Text Rader");
		setCategory(Category.WORLD);
	}
	
	@Override
	public void setup() {
		players = new LinkedList<String>();
	}

	@Override
	public void tick() {
		//for (Object obj : Minecraft.getMinecraft().theWorld.get)
	}

	@Override
	public void render() {
		
	}

}
