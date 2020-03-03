package ticketing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ticketing.Ticket.Severity;
import ticketing.Ticket.State;

public class IssueManager {
	private TreeMap<String,User> mapu= new TreeMap<>();
	private HashSet<Component> setC= new HashSet<>();
	private Integer i,id=1;
	private TreeMap<Integer,Ticket> mapt = new TreeMap<>();


    /**
     * Eumeration of valid user classes
     */
    public static enum UserClass { Reporter, Maintainer }
    
    public void createUser(String username, UserClass... classes) throws TicketException {
    	HashSet<UserClass> tmp= new HashSet<>(); for (UserClass u : classes) tmp.add(u); createUser(username,tmp);
    }

    public void createUser(String username, Set<UserClass> classes) throws TicketException {
    	if (mapu.containsKey(username)) throw new TicketException();
    	if (classes.isEmpty()) throw new TicketException();
    	User s= new User(username,classes);
    	mapu.put(username, s);
        
    }
    
    public Set<UserClass> getUserClasses(String username){
        return mapu.get(username).getuClass();
    }
    
    public void defineComponent(String name) throws TicketException {
    	if(setC.stream().anyMatch(a->a.getName()==name)) throw new TicketException();
    	Component c = new Component(name);
    	setC.add(c);
    	
        
    }
    public void defineSubComponent(String name, String parentPath) throws TicketException {
    	String s[] = parentPath.split("/");
    	Component c=null ;
    	for ( i=0; i<(s.length); i++){
    		if (i==0) continue;
    		if (i==1) c= setC.stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
    		if (i>1) c= c.getSubC().stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
    		if (c==null) throw new TicketException();
    	}
    		if (c.getSubC().stream().filter(a->a.getName().equals(name)).findAny().isPresent()) throw new TicketException();
    	c.addComponent(new Component(name,c));
 
    	
    	
    	
    	
           
    }
    
    public Set<String> getSubComponents(String path){
    	String s[] = path.split("/");
	Component c=null ;
	for ( i=0; i<(s.length); i++){
		if (i==0) continue;
		if (i==1) c= setC.stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
		if (i>1) c= c.getSubC().stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
		
							}
       
	return c.getSubC().stream().map(a->a.getName()).collect(Collectors.toSet());
    }

    public String getParentComponent(String path){String s[] = path.split("/");
	Component c=null ;
	for ( i=0; i<(s.length); i++){
		if (i==0) continue;
		if (i==1) c= setC.stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
		if (i>1) c= c.getSubC().stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
							}
		if (c.getParent()==null) return null;
        return "/"+c.getParent().getName();
    }

    public int openTicket(String username, String componentPath, 
    		String description, Ticket.Severity severity) throws TicketException {
    	if (!mapu.containsKey(username)) throw new TicketException();
    	if(!mapu.get(username).getuClass().contains(UserClass.Reporter)) throw new TicketException();
    	String s[] = componentPath.split("/");
    	Component c=null ;
    	for ( i=0; i<(s.length); i++){
    		if (i==0) continue;
    		if (i==1) c= setC.stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
    		if (i>1) c= c.getSubC().stream().filter(a->a.getName().equals(s[i])).findAny().orElse(null);
    		if (c==null) throw new TicketException();
    	}
    	mapt.put(id, new Ticket(id,username,description, componentPath,severity ));
    	id++;

    	
        return id-1;
    }
    
    public Ticket getTicket(int ticketId){
        return mapt.get(ticketId);
    }
    
    public List<Ticket> getAllTickets(){
        return mapt.values().stream().sorted((a,b)->a.getSeverity().compareTo(b.getSeverity())).collect(Collectors.toList());
    }
    
    public void assingTicket(int ticketId, String username) throws TicketException {
    	if (!mapt.containsKey(ticketId)) throw new TicketException();
    	if (!mapu.containsKey(username)) throw new TicketException();
    	Ticket t =mapt.get(ticketId);
    	if (!mapu.get(username).getuClass().contains(UserClass.Maintainer)) throw new TicketException();
        t.setAssigned(username);
        t.setSta(State.Assigned);
        
    }

    public void closeTicket(int ticketId, String description) throws TicketException {
    	if(! mapt.get(ticketId).getState().equals(State.Assigned)) throw new TicketException();
    	Ticket t= mapt.get(ticketId);
    	t.setSolDesc(description);
    	t.setSta(State.Closed);
    	mapu.get(t.getAssigned()).setNumtick();
        
    }

    
    public SortedMap<Ticket.Severity,Long> countBySeverityOfState(Ticket.State state){
    	if (state==null) return mapt.values().stream().sorted((a,b)->a.getSeverity().compareTo(b.getSeverity())).collect(Collectors.groupingBy(Ticket::getSeverity,TreeMap::new,Collectors.counting()));
    	return mapt.values().stream()
    			.filter(a->a.getState().equals(state))
    			.sorted((a,b)->a.getSeverity().compareTo(b.getSeverity()))
    			.collect(Collectors.groupingBy(Ticket::getSeverity,TreeMap::new,Collectors.counting()));
    }

    public List<String> topMaintainers(){
        return mapu.values().stream().filter(a->a.getuClass().contains(UserClass.Maintainer)).sorted((b,a)->a.getNumtick()-b.getNumtick()).map(a->a.getUsername()+":"+a.getNumtick()).collect(Collectors.toList());
 
    }

}
