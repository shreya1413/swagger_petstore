package swagger.io.swaggerpetstoreservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {
	public final static String AVAILABLE = "available";
	public final static String PENDING = "pending";
	public final static String SOLD = "sold";

	private int id;
	private Category category;
	private String name;
	private List<String> photoUrls;
	private String status;

	public Pet() {
		super();
	}

	public Pet(int id, Category category, String name, List<String> photoUrls, String status) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.photoUrls = photoUrls;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", category=" + category + ", name=" + name + ", photoUrls=" + photoUrls + ", status="
				+ status + "]";
	}

}
