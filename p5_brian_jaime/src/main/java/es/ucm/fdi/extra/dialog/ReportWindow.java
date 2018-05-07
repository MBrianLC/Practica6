package es.ucm.fdi.extra.dialog;

import javax.swing.*;

import es.ucm.fdi.model.simulator.RoadMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ReportWindow extends JFrame  {

	private DialogWindow _dialog;
	private String report;
	
	List<String> _vehicles;
	List<String> _roads;
	List<String> _junctions;
	
	public ReportWindow(RoadMap roadMap, int time) {
		initGUI(roadMap, time);
	}
	
	public String getReport() {
		return report;
	}

	private void initGUI(RoadMap roadMap, int time) {

		_vehicles  = new ArrayList<>();
		_roads  = new ArrayList<>();
		_junctions  = new ArrayList<>();
		for(int i=0;i<roadMap.getVehicles().size(); i++) {
			_vehicles.add(roadMap.getVehicles().get(i).getID());
		}
		for(int i=0;i<roadMap.getRoads().size(); i++) {
			_roads.add(roadMap.getRoads().get(i).getID());
		}
		for(int i=0;i<roadMap.getJunctions().size(); i++) {
			_junctions.add(roadMap.getJunctions().get(i).getID());
		}
		
		_dialog = new DialogWindow(this);
		_dialog.setData(_vehicles, _roads, _junctions);
		
		int status = _dialog.open();
		if ( status != 0) {
			String reporte = "";
			Map<String, String> m = new LinkedHashMap<>();
			for(String s : _dialog.getSelectedSimObjects()) {
				roadMap.getSimObject(s).report(time, m);
				reporte += "[" + m.get("") + "]\n";
				for (String key : m.keySet()){
					if (key != "") reporte += key + " = " + m.get(key) + '\n';
				}
				reporte += '\n';
				m.clear();
			}
			report = reporte;
		}

	}
}