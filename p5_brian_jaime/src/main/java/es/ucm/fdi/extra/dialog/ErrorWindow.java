package es.ucm.fdi.extra.dialog;

import javax.swing.JPanel;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;

@SuppressWarnings("serial")
public class ErrorWindow extends JFrame  {

	private ErrorDialog _dialog;
	
	public ErrorWindow(String error) {
		_dialog = new ErrorDialog(this, error);		
		_dialog.open();
	}
	
	private class ErrorDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		private String error;

		public ErrorDialog(Frame parent, String error) {
			super(parent, true);
			this.error = error;
			initGUI();
		}

		private void initGUI() {

			setTitle("ERROR");
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
			mainPanel.add(new JLabel(error));

			setContentPane(mainPanel);
			setMinimumSize(new Dimension(300, 100));
			setVisible(false);
		}

		public void open() {
			setLocation(getParent().getLocation().x + 400, getParent().getLocation().y + 300);
			pack();
			setVisible(true);
		}

	}
}