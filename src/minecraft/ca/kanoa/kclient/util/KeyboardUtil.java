package ca.kanoa.kclient.util;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class KeyboardUtil {

	private static boolean[] keyStates = new boolean[256];
	
	public static boolean keyDown(int key) {
		return Minecraft.getMinecraft().currentScreen == null &&
				(keyStates[key] = Keyboard.isKeyDown(key));
	}
	
	public static boolean keyPressed(int key) {
		return Minecraft.getMinecraft().currentScreen == null &&
				Keyboard.isKeyDown(key) != keyStates[key] &&
				(keyStates[key] = !keyStates[key]);
	}
	
	public static boolean keyDownGui(int key) {
		return (keyStates[key] = Keyboard.isKeyDown(key));
	}
	
	public static boolean keyPressedGui(int key) {
		return Keyboard.isKeyDown(key) != keyStates[key] &&
				(keyStates[key] = !keyStates[key]);
	}
	
	public static String getShortenedKeyName(int key) {
		switch (key) {
		case 0: return "NONE";
		case 1: return "ESC";
		case 2: return "1";
		case 3: return "2";
		case 4: return "3";
		case 5: return "4";
		case 6: return "5";
		case 7: return "6";
		case 8: return "7";
		case 9: return "8";
		case 10: return "9";
		case 11: return "0";
		case 12: return "-";
		case 13: return "=";
		case 14: return "BKSP";
		case 15: return "TAB";
		case 16: return "Q";
		case 17: return "W";
		case 18: return "E";
		case 19: return "R";
		case 20: return "T";
		case 21: return "Y";
		case 22: return "U";
		case 23: return "I";
		case 24: return "O";
		case 25: return "P";
		case 26: return "[";
		case 27: return "]";
		case 28: return "RET";
		case 29: return "LCTRL";
		case 30: return "A";
		case 31: return "S";
		case 32: return "D";
		case 33: return "F";
		case 34: return "G";
		case 35: return "H";
		case 36: return "J";
		case 37: return "K";
		case 38: return "L";
		case 39: return ";";
		case 40: return "'";
		case 41: return "`";
		case 42: return "LSHFT";
		case 43: return "\\";
		case 44: return "Z";
		case 45: return "X";
		case 46: return "C";
		case 47: return "V";
		case 48: return "B";
		case 49: return "N";
		case 50: return "M";
		case 51: return ",";
		case 52: return ".";
		case 53: return "/";
		case 54: return "RSHFT";
		case 55: return "*";
		case 56: return "LMENU";
		case 57: return "SPACE";
		case 58: return "CAPS";
		case 59: return "F1";
		case 60: return "F2";
		case 61: return "F3";
		case 62: return "F4";
		case 63: return "F5";
		case 64: return "F6";
		case 65: return "F7";
		case 66: return "F8";
		case 67: return "F9";
		case 68: return "F10";
		case 69: return "NUMLK";
		case 70: return "SCROL";
		case 71: return "NUM7";
		case 72: return "NUM8";
		case 73: return "NUM9";
		case 74: return "-";
		case 75: return "NUM4";
		case 76: return "NUM5";
		case 77: return "NUM6";
		case 78: return "+";
		case 79: return "NUM1";
		case 80: return "NUM2";
		case 81: return "NUM3";
		case 82: return "NUM0";
		case 83: return ".";
		case 87: return "F11";
		case 88: return "F12";
		case 100: return "F13";
		case 101: return "F14";
		case 102: return "F15";
		case 103: return "F16";
		case 104: return "F17";
		case 105: return "F18";
		case 112: return "KANA";
		case 113: return "F19";
		case 125: return "YEN";
		case 109: return "NUM1";
		case 141: return "=";
		case 144: return "^";
		case 145: return "@";
		case 146: return ":";
		case 147: return "_";
		case 148: return "KANJI";
		case 149: return "STOP";
		case 150: return "AX";
		case 156: return "RET";
		case 157: return "RCTRL";
		case 179: return ",";
		case 181: return "/";
		case 183: return "SYSRQ";
		case 184: return "RMENU";
		case 196: return "Fn";
		case 197: return "PAUSE";
		case 199: return "HOME";
		case 200: return "UP";
		case 201: return "PGUP";
		case 203: return "LEFT";
		case 205: return "RIGHT";
		case 207: return "END";
		case 208: return "DOWN";
		case 209: return "NEXT";
		case 210: return "INSRT";
		case 211: return "DEL";
		case 218: return "CLEAR";
		case 219: return "LMETA";
		case 220: return "RMETA";
		case 221: return "APPS";
		case 222: return "PWR";
		case 223: return "SLEEP";
		default: return "null";
		}
	}
	
}
