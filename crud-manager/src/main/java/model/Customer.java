package model;

public class Customer {
	private int id;
	private String name;
	private String telephone;
	private String email;
	private String vehicle;
	private String plate;
	
	public Customer() {
		this(0);
	}
	public Customer(int id) {
		this.id = id;
		setName("");
		setTelephone("");
		setEmail("");
		setVehicle("");
		setPlate("");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	
}
