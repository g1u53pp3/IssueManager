package ticketing;

public class Ticket {
	private int id;
	private String userName;
	private String description;
	private String componentPath;
	private Severity sev;
	private State sta;
	public void setSta(State sta) {
		this.sta = sta;
	}

	private String solDesc;
	private String assigned;

	
	
    
    public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public void setSolDesc(String solDesc) {
		this.solDesc = solDesc;
	}

	public Ticket(int id, String userName, String description, String componentPath,
			Severity s) {
    	this.id=id;
		this.userName = userName;
		this.description = description;
		this.componentPath = componentPath;
		this.sev = s;
		this.sta= State.Open;
		
	}

	/**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic };
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed }
    
    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }
    
    public Severity getSeverity() {
        return sev;
    }

    public String getAuthor(){
        return userName;
    }
    
    public String getComponent(){
        return componentPath;
    }
    
    public State getState(){
        return sta;
    }
    
    public String getSolutionDescription() throws TicketException {
        return solDesc;
    }
}
