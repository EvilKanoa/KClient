package ca.kanoa.kclient.settings;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import ca.kanoa.kclient.util.Files;

public class TextSettings {

	public static void saveSettings(Object settings, File file) {
		Map<String, String> map = new HashMap<String, String>();
		for (Field f : settings.getClass().getDeclaredFields()) {
			Setting s = f.getAnnotation(Setting.class);
			if (s != null) {
				String key;
				if (s.key().equals("field_name")) {
					key = f.getName();
				} else {
					key = s.key();
				}
				if (!f.isAccessible()) {
					f.setAccessible(true);
				}
				try {
					if (f.getType() == List.class) {
						map.put(key, listToString((List) f.get(settings)));
					} else {
						map.put(key, f.get(settings).toString());
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (settings instanceof SettingsHandler) {
			try {
				for (Entry<String, Object> entry : ((Map<String, Object>) 
						settings.getClass().getMethod("getSettings").invoke(settings)).entrySet()) {
					if (entry.getValue() instanceof List) {
						map.put(entry.getKey(), listToString((List) entry.getValue()));
					} else {
						map.put(entry.getKey(), entry.getValue().toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			builder.append(entry.getKey()).append(": ").append(entry.getValue());
			builder.append('\n');
		}
		Files.saveTextFile(builder.toString(), file);
	}

	public static void loadSettings(Object settings, File file) {
		Map<String, String> map = new HashMap<String, String>();
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		for (String line : Files.readTextFile(file).split("\n")) {
			if (line.contains(":")) {
				String[] raw = line.split(":");
				if (raw.length < 2) {
					continue;
				}
				String key = raw[0];
				StringBuilder value = new StringBuilder();
				for (int i = 1; i < raw.length; i++) {
					value.append(raw[i]);
					if (!(i == raw.length - 1)) {
						value.append(':');
					}
				}
				map.put(key, value.toString().trim());
			}
		}
		Map<String, Object> cleanMap = new HashMap<String, Object>();
		for (Field f : settings.getClass().getDeclaredFields()) {
			Setting s = f.getAnnotation(Setting.class);
			if (s != null) {
				String key;
				if (s.key().equals("field_name")) {
					key = f.getName();
				} else {
					key = s.key();
				}
				if (map.containsKey(key)) {
					if (!f.isAccessible()) {
						f.setAccessible(true);
					}
					Object value;
					try {
						if (f.getType() == int.class) {
							value = Integer.parseInt(map.get(key));
						} else if (f.getType() == boolean.class) { 
							value = Boolean.parseBoolean(map.get(key));
						} else if (f.getType() == double.class) {
							value = Double.parseDouble(map.get(key));
						} else if (f.getType() == List.class) {
							value = Arrays.asList(StringUtils.substringBetween(
									map.get(key), "[", "]").split("(?<!\\\\), "));
						} else if (f.getType() == String.class) {
							value = map.get(key);
						} else {
							value = f.getType().cast(map.get(key));
						}
					} catch (Exception e) {
						value = null;
						//TODO: remove debuggyness
						e.printStackTrace();
					}
					cleanMap.put(key, value);
					try {
						f.set(settings, value);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		if (settings instanceof SettingsHandler) {
			try {
				settings.getClass().getMethod("setSettings", Map.class)
					.invoke(settings, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String listToString(List list) {
		Iterator it = list.iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
        	Object o = it.next();
            sb.append(o instanceof String ? ((String) o).replace(",", "\\,") : o);
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
	}

}
