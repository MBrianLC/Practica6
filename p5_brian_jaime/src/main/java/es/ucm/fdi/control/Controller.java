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
		logger.info("Creando controller");
	}
	
	public void setIni (Ini ini){
		logger.fine("Ini modificado");
		this.ini = ini;
	}
	
	public void setTime (int timeLimit){
		logger.fine("Tiempo modificado");
		this.timeLimit = timeLimit;
	}
	
	public void setDelayTime (int delayTime){
		logger.fine("Tiempo modificado");
		this.delayTime = delayTime;
	}
	
	public void setOutputStream (OutputStream out){
		logger.fine("Salida de texto modificada");
		this.out = out;
	}
	
	public int getTime (){
		return timeLimit;
	}
	
	public int getDelayTime (){
		return delayTime;
	}
	
	/** 
	 * Método que lee las secciones de eventos, les asigna el builder correspondiente a cada una, y ejecuta la simulación.
	 * @param sim : La simulación de tráfico
	 * @throws IOException 
	 * @throws SimulatorException 
	*/
	
	public void execute(TrafficSimulator sim) throws IOException, SimulatorException {
		logger.info("Identificando simulación");
		for (IniSection n : ini.getSections()) {
			boolean b = false;
			try {
				if (!n.getTag().isEmpty()) {
					for (EventBuilder eBuilder : events) {
						if (n.getTag().equals(eBuilder.type())) {
							sim.insertaEvento(eBuilder.parse(n));
							b = true;
						}
					}
				}
				if (!b){
					logger.severe("Tag no identificada");
					throw new IllegalArgumentException("Incorrect tag: " + n.getTag());
				}
			} catch(IllegalArgumentException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}
		sim.execute(timeLimit, out, delayTime);
	}

}
