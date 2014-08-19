package ca.kanoa.kclient.keybinds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import ca.kanoa.kclient.modules.Keybinds;
import ca.kanoa.kclient.settings.SettingsHandler;
import ca.kanoa.kclient.util.KeyboardUtil;

public class KeybindSettingsHandler implements SettingsHandler {

	private Keybinds keybinds;

	public KeybindSettingsHandler(Keybinds keybinds) {
		this.keybinds = keybinds;
	}

	@Override
	public Map<String, Object> getSettings() {
		return new HashMap<String, Object>(keybinds.getBinds());
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Entry<String, Object> entry : settings.entrySet()) {
			if (entry.getValue() instanceof String) {
				try {
					map.put(entry.getKey(), Integer.parseInt((String) entry.getValue()));
				} catch (NumberFormatException ex) {}
			}
		}
		keybinds.setBinds(map);
	}

}
