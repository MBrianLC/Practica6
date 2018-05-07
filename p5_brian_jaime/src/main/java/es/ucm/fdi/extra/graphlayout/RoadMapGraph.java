package es.ucm.fdi.extra.graphlayout;

import javax.swing.*;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;
import es.ucm.fdi.model.simulator.Listener;
import es.ucm.fdi.model.simulator.RoadMap;
import es.ucm.fdi.model.simulator.TrafficSimulator.UpdateEvent;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class RoadMapGraph extends JFrame implements Listener {
	
	public GraphComponent _graphComp;
    
	public RoadMapGraph(RoadMap roadMap) {
		_graphComp = new GraphComponent();
		if (roadMap != null) {
			generateGraph(roadMap);
		}
	}

	protected void generateGraph(RoadMap roadMap) {

		Graph g = new Graph();
		Map<Junction, Node> js = new HashMap<>();
		for (Junction j : roadMap.getJunctions()) {
			Node n = new Node(j.getID());
			js.put(j, n);
			g.addNode(n);
		}
		for (Road r : roadMap.getRoads()) {
			Edge e = new Edge(r.getID(), js.get(r.getIni()), js.get(r.getFin()), r.getLong());
			for (int i = 0; i < r.getLong(); ++i) {
				if (r.getVehicles().containsKey(i)){
					for(Vehicle v: r.getVehicles().get(i)){
						Dot d = new Dot(v.getID(), v.getPos());
						e.addDot(d);
					}
				}
			}
			g.addEdge(e);
		}
		
		_graphComp.setGraph(g);

	}

	@Override
	public void update(UpdateEvent ue, String error) {
		generateGraph(ue.getRoadMap());
	}
}