/**
 * 
 * DocumentId is a this wrapper for a String to designate a type to be used in
 * SearchEngine.java.
 *
 */

public class DocumentId {
	
	// Private data field
	private final String id; // id for the document
	
	/** Constructs a DocumentId Object initializing the data field to the parameter value. 
	 * 
	 * @param id - parameter which is initialized to the data field
	 */
	public DocumentId(String id) {
		this.id = id;
	}
	
	/** Returns the Id of the document
	 * 
	 * @return the id of the document
	 */
	public String getDocumentId() {
		return String.valueOf(id);
	}
	
	/** Overrides the hashcode method of the Object class.
	 * 
	 * @return the hashcode of the id
	 */ 
	public int hashCode() {
		return id.hashCode();
	}
	
	/** Overrides the equals method of the Object class.
	 * 
	 * @return the boolean value.
	 */
	public boolean equals(Object object) {
		if (object == id) {
			return true;
		}
		if (!(object instanceof DocumentId)) {
			return false;
		}

		DocumentId other = (DocumentId) object;
		return this.id.equals(other.id);
	}
	
	/**
	 * Returns Returns a String of the form "DocumentId = xx" where xx is this document's id.
	 * 
	 * @return  a String of the form "DocumentId = xx" where xx is this document's id.
	 */
	public String toString() {
		String msg = "";
		msg += "DocumentId = " + getDocumentId();
		return msg;
	}
}
