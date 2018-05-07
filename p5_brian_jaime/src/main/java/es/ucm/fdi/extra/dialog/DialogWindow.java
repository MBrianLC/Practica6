package es.ucm.fdi.extra.dialog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

class DialogWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private MyListModel<String> _vehiclesListModel;
	private MyListModel<String> _roadsListModel;
	private MyListModel<String> _junctionsListModel;

	private int _status;
	private JList<String> _vehiclesList;
	private JList<String> _roadsList;
	private JList<String> _junctionsList;

	static final private char _clearSelectionKey = 'c';
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	public DialogWindow(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {

		_status = 0;

		setTitle("Some Dialog");
		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		JPanel vehiclesPanel = new JPanel(new BorderLayout());
		JPanel roadsPanel = new JPanel(new BorderLayout());
		JPanel junctionsPanel = new JPanel(new BorderLayout());

		contentPanel.add(vehiclesPanel);
		contentPanel.add(roadsPanel);
		contentPanel.add(junctionsPanel);

		vehiclesPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Vehicles", TitledBorder.LEFT, TitledBorder.TOP));
		roadsPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Roads", TitledBorder.LEFT, TitledBorder.TOP));
		junctionsPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Junctions", TitledBorder.LEFT, TitledBorder.TOP));

		vehiclesPanel.setMinimumSize(new Dimension(100, 100));
		roadsPanel.setMinimumSize(new Dimension(100, 100));
		junctionsPanel.setMinimumSize(new Dimension(100, 100));

		_vehiclesListModel = new MyListModel<>();
		_roadsListModel = new MyListModel<>();
		_junctionsListModel = new MyListModel<>();

		_vehiclesList = new JList<>(_vehiclesListModel);
		_roadsList = new JList<>(_roadsListModel);
		_junctionsList = new JList<>(_junctionsListModel);

		addCleanSelectionListner(_vehiclesList);
		addCleanSelectionListner(_roadsList);
		addCleanSelectionListner(_junctionsList);

		vehiclesPanel.add(new JScrollPane(_vehiclesList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		roadsPanel.add(new JScrollPane(_roadsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		
		junctionsPanel.add(new JScrollPane(_junctionsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);


		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		mainPanel.add(infoPanel, BorderLayout.PAGE_START);

		infoPanel.add(new JLabel("Select items for which you want to generate reports."));
		infoPanel.add(new JLabel("Use '" + _clearSelectionKey + "' to deselect all."));
		infoPanel.add(new JLabel("Use Ctrl+A to select all"));
		infoPanel.add(new JLabel(" "));

		setContentPane(mainPanel);
		setMinimumSize(new Dimension(100, 100));
		setVisible(false);
	}

	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == _clearSelectionKey) {
					list.clearSelection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

	}

	public void setData(List<String> vehicles, List<String> roads, List<String> junctions) {
		_vehiclesListModel.setList(vehicles);
		_roadsListModel.setList(roads);
		_junctionsListModel.setList(junctions);
	}

	public String[] getSelectedSimObjects() {
		int[] vehicles = _vehiclesList.getSelectedIndices();
		int[] roads = _roadsList.getSelectedIndices();
		int[] junctions = _junctionsList.getSelectedIndices();
		String[] items = new String[vehicles.length + roads.length + junctions.length];
		int cont = 0;
		for(int i=0; i<vehicles.length; i++) {
			items[cont] = _vehiclesListModel.getElementAt(vehicles[i]);
			++cont;
		}
		for(int i=0; i<roads.length; i++) {
			items[cont] = _roadsListModel.getElementAt(roads[i]);
			++cont;
		}
		for(int i=0; i<junctions.length; i++) {
			items[cont] = _junctionsListModel.getElementAt(junctions[i]);
			++cont;
		}
		return items;
	}

	public int open() {
		setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
		pack();
		setVisible(true);
		return _status;
	}

}
