package ca.kanoa.kclient.modules;

public class Login extends AbstractModule {

	@Override
	public void initialize() {
		setName("login");
		setDescription("Login");
		setVisible(false);
		setToggleable(false);
	}

	@Override
	public void tick() {}

	@Override
	public void render() {}
	
	@Override
	public void command(String[] args) {
		
	}

}
