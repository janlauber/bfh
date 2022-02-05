package stockquoter.model;

public class Company {

	private int id;
	private String name;
	private Address address;
	private String url;
	private String ceo;

	public Company() {
	}

	public Company(int id, String name, Address address, String url, String ceo) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.url = url;
		this.ceo = ceo;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public String getUrl() {
		return url;
	}

	public String getCeo() {
		return ceo;
	}
}
