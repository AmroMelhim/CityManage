
public class HashMap {
	private int tableSize = 128;
	private HashEntry[] table;
	private int currentSize = 0;

	public HashMap(int size) {
		table = new HashEntry[size];
		for (int i = 0; i < size; i++)
			table[i] = null;
		tableSize = size;
		currentSize = 0;
	}

	public void makeEmpty() {
		for (int i = 0; i < table.length; i++)
			table[i] = null;

		currentSize = 0;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public int getTableSize() {
		return tableSize;
	}

	public boolean contains(int key) {
		return get(key) != null;
	}

	public String get(int id) {
		int i = 1;
		int key = getHash(id);
		while ((table[key] != null) && (table[key].getStatus() != 0)) {
			if (table[key].getKey() == id)
				return table[key].getValue();
			key = (key + i * i++) % tableSize;
		}
		return null;
	}

	public void remove(int id) {
		int i = 1;
		int key = getHash(id);

		while ((table[key] != null) && (table[key].getStatus() > 0) && (table[key].getKey() != id))
			key = (key + i * i++) % tableSize;

		currentSize--;
		table[key].setDeleteStatus();
	}


	public void insert(int key, Resident value) {
		if (currentSize >= tableSize / 2)
			rehash();
		int hash = getHash(key);
		int i = 1;
		while ((table[hash] != null) && (table[hash].getStatus() != 0) && (table[hash].getStatus() != 2)) 
			hash = (hash + i * i++) % tableSize;
			
			

		currentSize++;
		table[hash] = new HashEntry(key, value, (byte) 1);
	}

	public int getHash(int key) {
		int hashVal = key;

		hashVal %= tableSize;
		if (hashVal < 0)
			hashVal += tableSize;
		return hashVal;
	}

	private void rehash() {
		HashMap newList;
		newList = new HashMap(nextPrime(2 * table.length));
		// Copy table over
		for (int i = 0; i < table.length; i++)
			if ((table[i] != null) && (table[i].getStatus() == 1))
				newList.insert(table[i].getKey(), table[i].getValueObject());

		table = newList.table;
		tableSize = newList.tableSize;
	}

	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;
		for (; !isPrime(n); n += 2)
			;
		return n;
	}

	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
		if (n == 1 || n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n / 2; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}

	public void printHashTable() {
		for (int i = 0; i < table.length; i++)
			if ((table[i] != null) && (table[i].getStatus() == 1))
				System.out.println(table[i].getValue().toString());
	}

	// returns a string of the hash table including empty spots
	public String toString() {
		String str = "";

		for (int i = 0; i < table.length; i++) {
			try {
				if ((table[i].getStatus() == 1))

					str += table[i].getValue() + "\n";
				
			} catch (NullPointerException e) {
				str += "\n";

			}
		}

		return str;
	}

	// returns a string of the hash table as the file format
	public String toStringFile() {
		String str = "";
		for (int i = 0; i < table.length; i++)
			if ((table[i] != null) && (table[i].getStatus() == 1))
				str += table[i].toStringFile() + "\n";
		
		return str;

	}

}
