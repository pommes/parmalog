package de.tyranus.poseries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import de.tyranus.poseries.config.PoseriesConfig;
import de.tyranus.poseries.gui.MainWindow;

/**
 * Startet die App.
 */
public class App {

	@Autowired
	MainWindow window;

	public static void main(String[] args) {
		//Runtime.getRuntime().loadLibrary("swt-win32-3740");
		final ApplicationContext context = new AnnotationConfigApplicationContext(PoseriesConfig.class);
		context.getBean(App.class).start();
	}

	public void start() {
		try {
			window.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
