package es.ucm.fdi.view;

public enum Command {
	Load("Load Events"), Save("Save Events"), SaveReport("Save Report"), GenReport("Generate"),
	ClearReport("ClearReport"), CheckIn("Insert"), Run("Run"), Reset("Reset"), Output("Redirect Output"),
	Clear("Clear"), Quit("Exit");
	
	private String text;
	
	/** 
	 * Constructor de command.
	 * @param text : Texto identificador del comando
	*/
	
	Command(String text) {
		this.text = text;
	}
	
	/** 
	 * Devuelve el texto asociado a una acción.
	 * @return Texto identificador de la acción
	*/
	
	public String toString() {
		return text;
	}
}
