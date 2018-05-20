package es.ucm.fdi.extra.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import es.ucm.fdi.view.Command;

public class PopUpMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String newVehicle = "[new_vehicle]\n" + "time = \n" + "id = \n" +
								"max_speed = \n" + "itinerary = \n";
	private String newRoad = "[new_road]\n" + "time = \n" + "id = \n" + "src = \n" + 
							 "dest = \n" + "max_speed = \n" + "length = \n";
	private String newJunction = "[new_junction]\n" + "time = \n" + "id = \n";
	
	private JMenuItem template(JTextArea textArea, String title, String text) {
		JMenuItem template = new JMenuItem(title);
		template.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append(newJunction + "type = rr\n" + "max_time_slice = \n" + "min_time_slice = \n\n");
			}
		});
		return template;
	}
	
	public void addEditor(JTextArea textArea) {
		// create the events pop-up menu
		JPopupMenu _editorPopupMenu = new JPopupMenu();
		
		JMenu subMenu = new JMenu("Add Template");
		
		subMenu.add(template(textArea, "New RR Junction", newJunction + "type = rr\n" + 
							 "max_time_slice = \n" + "min_time_slice = \n\n"));
		subMenu.add(template(textArea, "New MC Junction", newJunction + "type = mc\n\n"));
		subMenu.add(template(textArea, "New Junction", newJunction + "\n"));
		subMenu.add(template(textArea, "New Dirt Road", newRoad + "type = dirt\n\n"));
		subMenu.add(template(textArea, "New Lanes Road", newRoad + "type = lanes\n" + "lanes = \n\n"));
		subMenu.add(template(textArea, "New Road", newRoad + "\n"));
		subMenu.add(template(textArea, "New Bike", newVehicle + "type = bike\n\n"));
		subMenu.add(template(textArea, "New Car", newVehicle + "type = car\n" + "resistance = \n" +
									   "fault_probability = \n" + "max_fault_duration = \n" + "seed = \n\n"));
		subMenu.add(template(textArea, "New Vehicle", newVehicle + "\n"));
		subMenu.add(template(textArea, "Make Vehicle Faulty", "[make_vehicle_faulty]\n" + "time = \n" + 
									   "vehicles = \n" + "duration = \n\n"));
		
		_editorPopupMenu.add(subMenu);
		_editorPopupMenu.addSeparator();
		_editorPopupMenu.add(textArea.getActionMap().get(Command.Load));
		_editorPopupMenu.add(textArea.getActionMap().get(Command.Save));
		_editorPopupMenu.add(textArea.getActionMap().get(Command.Clear));

		// connect the popup menu to the text area _editor
		textArea.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && _editorPopupMenu.isEnabled()) {
					_editorPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

	}

}