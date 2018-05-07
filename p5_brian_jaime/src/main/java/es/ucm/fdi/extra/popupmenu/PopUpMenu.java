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
	
	private String newVehicle() {
		StringBuilder sb = new StringBuilder("[new_vehicle]\n");
		sb.append("time = \n");
		sb.append("id = \n");
		sb.append("max_speed = \n");
		sb.append("itinerary = \n");
		return sb.toString();
	}
	
	private String newRoad() {
		StringBuilder sb = new StringBuilder("[new_road]\n");
		sb.append("time = \n");
		sb.append("id = \n");
		sb.append("src = \n");
		sb.append("dest = \n");
		sb.append("max_speed = \n");
		sb.append("length = \n");
		return sb.toString();
	}
	
	private String newJunction() {
		StringBuilder sb = new StringBuilder("[new_junction]\n");
		sb.append("time = \n");
		sb.append("id = \n");
		return sb.toString();
	}
	
	public void addEditor(JTextArea textArea) {
		// create the events pop-up menu
		JPopupMenu _editorPopupMenu = new JPopupMenu();
		
		JMenu subMenu = new JMenu("Add Template");
		
		JMenuItem templateRROption = new JMenuItem("New RR Junction");
		templateRROption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder(newJunction());
				sb.append("type = rr\n");
				sb.append("max_time_slice = \n");
				sb.append("min_time_slice = \n\n");
				textArea.append(sb.toString());
			}
		}); 
		subMenu.add(templateRROption);
		JMenuItem templateMCOption = new JMenuItem("New MC Junction");
		templateMCOption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append(newJunction() + "type = mc\n\n");
			}
		});
		subMenu.add(templateMCOption);
		JMenuItem templateJOption = new JMenuItem("New Junction");
		templateJOption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append(newJunction() + "\n");
			}
		});
		subMenu.add(templateJOption);
		JMenuItem templateDirtOption = new JMenuItem("New Dirt Road");
		templateDirtOption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append(newRoad() + "type = dirt\n\n");
			}
		});
		subMenu.add(templateDirtOption);
		JMenuItem templateLanesOption = new JMenuItem("New Lanes Road");
		templateLanesOption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder(newRoad());
				sb.append("type = lanes\n");
				sb.append("lanes = \n\n");
				textArea.append(sb.toString());
			}
		});
		subMenu.add(templateLanesOption);
		JMenuItem templateROption = new JMenuItem("New Road");
		templateROption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append(newRoad() + "\n");
			}
		});
		subMenu.add(templateROption);
		JMenuItem templateBikeOption = new JMenuItem("New Bike");
		templateBikeOption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append(newVehicle() + "type = bike\n\n");
			}
		});
		subMenu.add(templateBikeOption);
		JMenuItem templateCarOption = new JMenuItem("New Car");
		templateCarOption.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder(newVehicle());
				sb.append("type = car\n");
				sb.append("resistance = \n");
				sb.append("fault_probability = \n");
				sb.append("max_fault_duration = \n");
				sb.append("seed = \n\n");
				textArea.append(sb.toString());
			}
		});
		subMenu.add(templateCarOption);
		JMenuItem templateVOption = new JMenuItem("New Vehicle");
		templateVOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append(newVehicle() + "\n");
			}
		});
		subMenu.add(templateVOption);
		JMenuItem templateFaultyOption = new JMenuItem("Make Vehicle Faulty");
		templateFaultyOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder("[make_vehicle_faulty]\n");
				sb.append("time = \n");
				sb.append("vehicles = \n");
				sb.append("duration = \n\n");
				textArea.append(sb.toString());
			}
		});
		subMenu.add(templateFaultyOption);
		
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