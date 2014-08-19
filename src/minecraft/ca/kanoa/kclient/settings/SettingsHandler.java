package ca.kanoa.kclient.settings;

import java.util.Map;

public interface SettingsHandler {

	public Map<String, Object> getSettings();
	
	public void setSettings(Map<String, Object> settings);
	
}
