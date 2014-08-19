package ca.kanoa.kclient.keybinds;

import org.darkstorm.minecraft.gui.component.basic.BasicButton;

public class KeybindButton extends BasicButton {

	private String module;
	
	public KeybindButton(String module) {
		super();
		this.module = module;
	}
	
	public KeybindButton(String module, String text) {
		super(text);
		this.module = module;
	}
	
	public void setModule(String moduleName) {
		this.module = moduleName;
	}
	
	public String getModule() {
		return this.module;
	}
	
}
