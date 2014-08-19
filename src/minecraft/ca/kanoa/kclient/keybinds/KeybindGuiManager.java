package ca.kanoa.kclient.keybinds;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.client.Minecraft;

import org.darkstorm.minecraft.gui.AbstractGuiManager;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicLabel;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.lwjgl.input.Keyboard;

import ca.kanoa.kclient.KClient;
import ca.kanoa.kclient.Module;
import ca.kanoa.kclient.modules.Category;
import ca.kanoa.kclient.modules.Keybinds;
import ca.kanoa.kclient.util.KeyboardUtil;

public class KeybindGuiManager extends AbstractGuiManager {

	private final AtomicBoolean setup;
	private final Keybinds keybinds;
	
	public KeybindGuiManager(Keybinds keybinds) {
		setup = new AtomicBoolean();
		this.keybinds = keybinds;
	}
	
	@Override
	public void setup() {
		// We only want to run this method once
		if (!setup.compareAndSet(false, true)) {
			return;
		}
		
		final Map<Category, BasicFrame> keybindFrames = new HashMap<Category, BasicFrame>();
		for (Module module : KClient.getInstance().getModuleManager().getModules()) {
			BasicFrame frame = keybindFrames.get(module.getCategory());
			if (frame == null) {
				frame = new BasicFrame(module.getCategory().toString());
				frame.setTheme(getTheme());
				frame.setLayoutManager(new GridLayoutManager(2, 0));
				frame.setVisible(true);
				frame.setClosable(false);
				frame.setMinimizable(false);
				frame.setMinimized(false);
				frame.setPinnable(false);
				addFrame(frame);
				keybindFrames.put(module.getCategory(), frame);
			}
			frame.add(new BasicLabel(module.getDescription()));
			final Module updateModule = module;
			Button button = new KeybindButton(module.getName(), 
					KeyboardUtil.getShortenedKeyName(keybinds.getKey(
					module.getName())).toUpperCase()) {
				
				@Override
				public void update() {
					setText(KeyboardUtil.getShortenedKeyName(keybinds.getKey(
							updateModule.getName())));
				}
			};
			button.addButtonListener(keybinds.getListener());
			frame.add(button, HorizontalGridConstraint.RIGHT);
		}
		
		resizeComponents();
		Minecraft mc = Minecraft.getMinecraft();
		Dimension maxSize = recalculateSizes();
		int offsetX = 5, offsetY = 5;
		int scale = mc.gameSettings.guiScale;
		if(scale == 0)
			scale = 1000;
		int scaleFactor = 0;
		while (scaleFactor < scale && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
			scaleFactor++;
		}
		for (Frame frame : getFrames()) {
			frame.setX(offsetX);
			frame.setY(offsetY);
			offsetX += maxSize.width + 5;
			if(offsetX + maxSize.width + 5 > mc.displayWidth / scaleFactor) {
				offsetX = 5;
				offsetY += maxSize.height + 5;
			}
		}
		
	}

	@Override
	protected void resizeComponents() {
		Theme theme = getTheme();
		Button button = new BasicButton("******");
		Dimension size = theme.getUIForComponent(button).getDefaultSize(button);
		for (Frame frame : getFrames()) {
			if (frame instanceof BasicFrame) {
				for (Component comp : frame.getChildren()) {
					if (comp instanceof BasicButton) {
						comp.setWidth(size.width);
						comp.setHeight(size.height);
					}
				}
			}
		}
	}

	private Dimension recalculateSizes() {
		Frame[] frames = getFrames();
		int maxWidth = 0, maxHeight = 0;
		for(Frame frame : frames) {
			Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
			maxWidth = Math.max(maxWidth, defaultDimension.width);
			frame.setHeight(defaultDimension.height);
			maxHeight = Math.max(maxHeight, defaultDimension.height);
		}
		for(Frame frame : frames) {
			frame.setWidth(maxWidth);
			frame.layoutChildren();
		}
		return new Dimension(maxWidth, maxHeight);
	}

}
