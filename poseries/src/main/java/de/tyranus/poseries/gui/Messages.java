package de.tyranus.poseries.gui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "de.tyranus.poseries.gui.messages"; //$NON-NLS-1$
	public static String MainWindow_shell_text;
	public static String MainWindow_lblFileExtensions_text;
	public static String MainWindow_btnSrcSelect_text;
	public static String MainWindow_lblSourceDirectory_text;
	public static String MainWindow_lblDestinationDirecory_text;
	public static String MainWindow_btnDstSelect_text;
	public static String MainWindow_btnRefresh_text;
	public static String MainWindow_lblSourceDirectoryPattern_text;
	public static String MainWindow_btnPostProcess_text;
	public static String MainWindow_lblFileExtensions_toolTipText;
	public static String MainWindow_lblSourceDirectory_toolTipText;
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	////////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
