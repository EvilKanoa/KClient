package ca.kanoa.kclient.keybinds;

import java.awt.Color;

import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.listener.ButtonListener;

import ca.kanoa.kclient.modules.Keybinds;

public class KeybindButtonListener implements ButtonListener {

	private Keybinds keybinds;
	
	public KeybindButtonListener(Keybinds keybinds) {
		this.keybinds = keybinds;
	}
	
	@Override
	public void onButtonPress(Button par1) {
		if (!(par1 instanceof KeybindButton) ||
				keybinds.isListening()) {
			return;
		}
		KeybindButton button = (KeybindButton) par1;
		button.setBackgroundColor(Color.red);
		keybinds.listen(button);
	}
	
}
