package de.tyranus.poseries.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tyranus.poseries.App;
import de.tyranus.poseries.gui.MainWindow;
import de.tyranus.poseries.usecase.UseCaseService;
import de.tyranus.poseries.usecase.intern.UseCaseServiceImpl;

@Configuration
public class PoseriesConfig {

	@Bean
	public App app() {
		return new App();
	}
	
	@Bean
	public MainWindow mainWindow() {
		return new MainWindow();
	}

	@Bean
	public UseCaseService useCaseService() {
		return new UseCaseServiceImpl();
	}
}
