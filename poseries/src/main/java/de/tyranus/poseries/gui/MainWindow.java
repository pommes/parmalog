package de.tyranus.poseries.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.file.*;
import java.util.List;
import java.util.Set;

import de.tyranus.poseries.usecase.UseCaseService;
import de.tyranus.poseries.usecase.UseCaseServiceException;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindow {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);

	@Autowired
	private UseCaseService useCaseService;

	protected Shell shell;
	private Text txtSrcDir;
	private Text txtDstDir;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(459, 403);
		shell.setText("PoSeries - Series Post Download");

		final Combo cmbFilePattern = new Combo(shell, SWT.NONE);
		cmbFilePattern.setItems(new String[] { "*avi,*mp4" });
		cmbFilePattern.setBounds(145, 68, 159, 23);

		final Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setBounds(10, 71, 129, 15);
		lblNewLabel.setText("Filename pattern");

		txtSrcDir = new Text(shell, SWT.BORDER);
		txtSrcDir.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				LOGGER.debug("txtSrcDir mouse hover.");
				txtSrcDir.setToolTipText(txtSrcDir.getText());
			}
		});
		txtSrcDir.setBounds(145, 12, 159, 21);

		final Button btnSrcSelect = new Button(shell, SWT.NONE);
		btnSrcSelect.setBounds(310, 10, 120, 25);
		btnSrcSelect.setText("Chose directory...");

		final Label lblSourceDirectory = new Label(shell, SWT.NONE);
		lblSourceDirectory.setAlignment(SWT.RIGHT);
		lblSourceDirectory.setBounds(10, 15, 129, 15);
		lblSourceDirectory.setText("Source directory");

		txtDstDir = new Text(shell, SWT.BORDER);
		txtDstDir.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				LOGGER.debug("txtDstDir mouse hover.");
				txtDstDir.setToolTipText(txtDstDir.getText());
			}
		});
		txtDstDir.setBounds(145, 97, 159, 21);

		final Label lblDestinationDirecory = new Label(shell, SWT.NONE);
		lblDestinationDirecory.setAlignment(SWT.RIGHT);
		lblDestinationDirecory.setBounds(10, 100, 129, 15);
		lblDestinationDirecory.setText("Destination direcory");

		final Button btnDstSelect = new Button(shell, SWT.NONE);
		btnDstSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LOGGER.debug("btnSrcSelect klicked.");
				DirectoryDialog dlg = new DirectoryDialog(shell);

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(txtDstDir.getText());

				// Change the title bar text
				dlg.setText("SWT's DirectoryDialog");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					txtDstDir.setText(dir);
				}
			}
		});
		btnDstSelect.setBounds(310, 95, 120, 25);
		btnDstSelect.setText("Chose directory...");

		final Button btnPostProcessSeries = new Button(shell, SWT.NONE);
		btnPostProcessSeries.setBounds(154, 133, 150, 25);
		btnPostProcessSeries.setText("Post process series");

		final Combo cmbSrcPattern = new Combo(shell, SWT.NONE);
		cmbSrcPattern.setBounds(145, 39, 159, 23);

		final Label lblSourceDirectoryPattern = new Label(shell, SWT.NONE);
		lblSourceDirectoryPattern.setAlignment(SWT.RIGHT);
		lblSourceDirectoryPattern.setBounds(10, 42, 129, 15);
		lblSourceDirectoryPattern.setText("Source directory pattern");
		
		ExpandBar expandBar = new ExpandBar(shell, SWT.NONE);
		expandBar.setBounds(10, 164, 420, 32);
		
		ExpandItem xpndtmSelectedFiles = new ExpandItem(expandBar, SWT.NONE);
		xpndtmSelectedFiles.setText("Selected Files");
		
		final StyledText txtFiles = new StyledText(expandBar, SWT.BORDER);
		txtFiles.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		txtFiles.setEditable(false);
		xpndtmSelectedFiles.setControl(txtFiles);
		xpndtmSelectedFiles.setHeight(150);

		btnSrcSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LOGGER.debug("btnDstSelect klicked.");
				DirectoryDialog dlg = new DirectoryDialog(shell);

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(txtSrcDir.getText());

				// Change the title bar text
				dlg.setText("SWT's DirectoryDialog");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null) {
					// find the source dir pattern
					final String srcDirPattern = useCaseService.findSrcDirPattern(dir);

					// find the final source dir
					final Path finalSrcDir = useCaseService.createFinalSrcDir(dir);

					try {
						// finds the matching files
						final Set<Path> files = useCaseService.findMatchingSrcDirs(finalSrcDir, srcDirPattern);
						
						// preview matching files
						txtFiles.setText(useCaseService.formatFileList(files));

						// get the filename pattern
						final Set<String> extensions = useCaseService.getFoundVideoExtensions(files);
						final String filenamePattern = useCaseService.explodeVideoExtensions(extensions);
						cmbFilePattern.setText(filenamePattern);
					}
					catch (UseCaseServiceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Set the text box to the new selection
					txtSrcDir.setText(finalSrcDir.toString());

					// Set the source dir pattern
					cmbSrcPattern.setText(srcDirPattern);
				}
			}
		});
	}
}
