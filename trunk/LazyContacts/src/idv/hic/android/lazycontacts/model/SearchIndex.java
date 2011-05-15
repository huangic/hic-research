package idv.hic.android.lazycontacts.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="searchindex")
public class SearchIndex {

	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int contactId;
	@DatabaseField
	private String mappingNumber;
	@DatabaseField
	private String displayName;
	@DatabaseField
	private int phonicsType;
	@DatabaseField
	private String phonicData;
	@DatabaseField
	private String mappingPhonicData;
	
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getMappingNumber() {
		return mappingNumber;
	}
	public void setMappingNumber(String mappingNumber) {
		this.mappingNumber = mappingNumber;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getPhonicsType() {
		return phonicsType;
	}
	public void setPhonicsType(int phonicsType) {
		this.phonicsType = phonicsType;
	}
	public String getPhonicData() {
		return phonicData;
	}
	public void setPhonicData(String phonicData) {
		this.phonicData = phonicData;
	}
	public String getMappingPhonicData() {
		return mappingPhonicData;
	}
	public void setMappingPhonicData(String mappingPhonicData) {
		this.mappingPhonicData = mappingPhonicData;
	}
	
	
	
}
