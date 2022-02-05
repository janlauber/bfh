package stockquoter.model;

public class Address {

	private String street;
	private String postalCode;
	private String city;
	private String country;

	public Address() {
	}

	public Address(String street, String postalCode, String city, String country) {
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
}
