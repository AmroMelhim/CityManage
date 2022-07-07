


public class HashEntry {
	private int key;
	private Resident value;
	private byte status; // insert: 1, delete: 2, empty: 0

	HashEntry(int key, Resident value, byte status) {
		this.key = key;
		this.value = value;
		this.status = status;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value.toString();
	}
	
	public int getId() {
		return value.getId();
	}
	
	public String getName() {
		return value.getName();
	}
	
	public int getAge() {
		return value.getAge();
	}
	
	
	public Resident getValueObject() {
		return value;
	}

	public byte getStatus() {
		return status;
	}

	public void setDeleteStatus() {
		status = 2;
	}
	
	public String toStringFile() {
		return value.toStringFile();
		
	}
}
