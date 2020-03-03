package ticketing;

import java.util.HashSet;
import java.util.Set;

public class Component {
	private String name;
	private HashSet<Component> subC= new HashSet<>();
	private Component parent=null;
	public Component(String name, Component parent) {
		this.name = name;
		this.parent = parent;
	}
	public Component(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public Set<Component> getSubC() {
		return subC;
	}
	public Component getParent() {
		return parent;
	}
	public void addComponent(Component c){
		subC.add(c);
	}
}
