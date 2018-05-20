package es.ucm.fdi.control;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import es.ucm.fdi.model.events.EventBuilder;
import es.ucm.fdi.model.events.NewJunctionEventBuilder;
import es.ucm.fdi.model.events.NewRoadEventBuilder;
import es.ucm.fdi.model.events.NewVehicleEventBuilder;
import es.ucm.fdi.model.events.VehicleFaultyEventBuilder;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simulator.TrafficSimulator;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;

/** 
 * La clase Controller controla simulación.
 * @author Jaime Fernández y Brian Leiva
*/

public class Controller {
	private EventBuilder[] events = {
			new NewVehicleEventBuilder(), new NewRoadEventBuilder(),
			new NewJunctionEventBuilder(), new VehicleFaultyEventBuilder() };
	private Ini ini;
	private OutputStream out;
	private int timeLimit;
	private int delayTime;
	
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
	
	/** 
	 * Constructor de la clase Controller.
	 * @param ini : Flujo de entrada (formato ini)
	 * @param out : Flujo de salida
	 * @param timeLimit : Tiempo durante el que se ejecuta la simulación
	*/
	public Controller(Ini ini, OutputStream out, Integer timeLimit) {
		this.ini = ini;
		this.out = out;
		this.timeLimit = timeLimit;
		logger.info("Creating controller");
	}
	
	public void setIni (Ini ini){
		logger.fine("Ini modified");
		this.ini = ini;
	}
	
	public void setTime (int timeLimit){
		logger.fine("Time modified");
		this.timeLimit = timeLimit;
	}
	
	public void setDelayTime (int delayTime){
		logger.fine("Time modified");
		this.delayTime = delayTime;
	}
	
	public void setOutputStream (OutputStream out){
		logger.fine("Output stream modified");
		this.out = out;
	}
	
	public int getTime (){
		return timeLimit;
	}
	
	public int getDelayTime (){
		return delayTime;
	}
	
	/** 
	 * Método que lee las secciones de eventos, y les asigna el builder correspondiente a cada una.
	 * @param sim : La simulación de tráfico
	 * @throws IOException 
	 * @throws SimulatorException 
	*/
	
	public void insertEvents(TrafficSimulator sim) throws IOException, SimulatorException {
		for (IniSection n : ini.getSections()) {
			boolean b = false;
			if (!n.getTag().isEmpty()) {
				for (EventBuilder eBuilder : events) {
					if (n.getTag().equals(eBuilder.type())) {
						sim.insertaEvento(eBuilder.parse(n));
						b = true;
					}
				}
			}
			if (!b){
				throw new IllegalArgumentException("Incorrect tag: " + n.getTag());
			}
		}
	}
	
	/** 
	 * Método que ejecuta la simulación.
	 * @param sim : La simulación de tráfico
	*/
	
	public void execute(TrafficSimulator sim) throws IOException, SimulatorException{
		sim.execute(timeLimit, out, delayTime);
	}

}
