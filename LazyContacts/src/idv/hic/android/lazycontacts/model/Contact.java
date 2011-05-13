package idv.hic.android.lazycontacts.model;

import java.util.List;

public class Contact {
	
	private String rawId;
	private String id;
	
	private String sortKey;
	private String name;
	private List<String> phone;
	private List<String> email;
	private String contactType;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhone() {
		return phone;
	}
	public void setPhone(List<String> phone) {
		this.phone = phone;
	}
	public List<String> getEmail() {
		return email;
	}
	public void setEmail(List<String> email) {
		this.email = email;
	}
	public String getRawId() {
		return rawId;
	}
	public void setRawId(String rawId) {
		this.rawId = rawId;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getContactType() {
		return contactType;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getSortKey() {
		return sortKey;
	}
	
	
}
