package ca.kanoa.kclient.modules;

public enum Category {
	
	PLAYER,
	WORLD,
	COMBAT,
	BOTS,
	OTHER;
	
	@Override
	public String toString() {
		if (name().length() > 1) {
			return name().charAt(0) + name().substring(1).toLowerCase();
		} else {
			return name();
		}
	}
	
}
