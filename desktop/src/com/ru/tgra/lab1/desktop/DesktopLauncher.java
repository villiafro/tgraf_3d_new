package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.shapes.LabFirst3DGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		config.title = "MAZE OF DOOM"; // or whatever you like
		//config.width = (int)screenSize.getWidth();  //experiment with
		//config.height = (int)screenSize.getHeight();  //the window size
		config.width = 800;
		config.height = 600;

		//config.fullscreen = true;

		new LwjglApplication(new LabFirst3DGame(), config);
	}
}
