 import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;

import javax.swing.JOptionPane;

import ticketing.IssueManager;
import ticketing.Ticket;
import ticketing.TicketException;
import static ticketing.IssueManager.*;

public class Example {

    public static void main(String[] args) throws TicketException {
        
        IssueManager ts = new IssueManager();

        HashSet<UserClass> both = new HashSet<>();
        both.add(UserClass.Reporter);
        both.add(UserClass.Maintainer);
        ts.createUser("alpha", UserClass.Reporter);
        ts.createUser("beta", UserClass.Reporter);
        ts.createUser("gamma", both);
        ts.createUser("delta", both);
        ts.createUser("epsilon", UserClass.Maintainer);
        
        System.out.println(ts.getUserClasses("gamma"));
        
        ts.defineComponent("System");
        ts.defineSubComponent("SubA", "/System");
        ts.defineSubComponent("SubB", "/System");
        ts.defineSubComponent("SubC", "/System");
        ts.defineSubComponent("SubB.1", "/System/SubB");
        ts.defineSubComponent("SubB.2", "/System/SubB");
        
        System.out.println("System has " + ts.getSubComponents("/System").size() + " children");
        System.out.println("SubB.2 has parent " + ts.getParentComponent("/System/SubB/SubB.2"));
        
        
        ts.openTicket("alpha", "/System/SubA", "Initial menu does not show 'open' item", Ticket.Severity.Major);
        ts.openTicket("alpha", "/System/SubA", "Cannot save form XYZ", Ticket.Severity.Major);
        ts.openTicket("alpha", "/System/SubB", "The colors in the diagram are hard to tell apart", Ticket.Severity.Minor);
        int id = ts.openTicket("alpha", "/System", "The system is not responding today", Ticket.Severity.Blocking);
        Ticket t = ts.getTicket(id);
        
        System.out.println("User " + t.getAuthor() + " created ticket " + t.getId() + " on component " + t.getComponent());
        
        ts.assingTicket(id, "delta");
        ts.closeTicket(id, "The user had the network cable unplugged...");
        
        System.out.println("The ticket status is " + t.getState() + " solution: " + t.getDescription());
        
        System.out.println("Count open tickets:\n"  +ts.countBySeverityOfState(Ticket.State.Open));
        System.out.println("Count all tickets:\n"  +ts.countBySeverityOfState(null));
        System.out.println("Most active maintainers:\n"  +ts.topMaintainers());
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This code serves the only purpose of checking whether
     * your entered the required personal information in file Student.txt
     */
    static {
        Properties p = new Properties();
        try{
            p.load(new FileInputStream("Student.txt"));
        }catch(IOException e){
            System.err.println("Could not open the file Student.txt");
            System.err.flush();
            JOptionPane.showMessageDialog(null, "Missing student information!\n\nPlease fill in the Student.txt file before submitting the final version.",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
        if( p.getProperty("ID","").equals("")){
            System.err.println("Missing student information. Please fill in the Student.txt file");
            System.err.flush();
            JOptionPane.showMessageDialog(null, "Missing student information!\n\nPlease fill in the Student.txt file before submitting the final version.",
                                            "Error",JOptionPane.ERROR_MESSAGE);
        }else{
            System.out.println("Project by " + p.getProperty("FirstName") + " " 
                                             + p.getProperty("LastName") 
                                             + " (" + p.getProperty("ID") + ")");
            System.out.flush();
        }
    }

}
