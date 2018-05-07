package es.ucm.fdi.model.simulator;

import es.ucm.fdi.model.simulator.TrafficSimulator.UpdateEvent;

public interface Listener {
	
	void update(UpdateEvent ue, String error);
	
}