
/**
 * 
 * A simplified document indexer and search engine.
 * 
 * Documents are added to the engine one-by-one, and uniquely identified by a DocumentId.
 *
 * Documents are internally represented as "terms", which are lower-cased versions of each word 
 * in the document. 
 * 
 * Queries for terms are also made on the lower-cased version of the term. Terms are 
 * therefore case-insensitive.
 * 
 * Lookups for documents can be done by term, and the most relevant document(s) to a specific term 
 * (as computed by tf-idf) can also be retrieved.
 */

import java.util.*;

public class SearchEngine {

	// private data field
	private Map<String, Set<DocumentId>> setDocumentMap; // map which has a String as a key and set of documents as a value of that key.
	
	private Map<DocumentId, Map<String, Integer>> documentMap; // map which has a DocumentId as a Key and another Map as a value which contains String and Integer of that key

	/** Constructs an empty map for each data field
	 * 
	 */
	public SearchEngine() {
		this.setDocumentMap = new HashMap<>();
		this.documentMap = new HashMap<>();
	}

	/** addDocument method receives a document's id and a String which the text of the document and adds that document to each of the search engine's maps which were made empty in the constructor above.
	 * 
	 * @param id - id for the document
	 * @param term - A word in a document or word is being searched in a documents
	 */
	public void addDocument(DocumentId id, String term) {
		
		Map<String, Integer> valueMap = new HashMap<>();
		term = term.toLowerCase();
		String[] words = term.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].replaceAll("[^a-zA-Z']+$", "");
			words[i] = words[i].replaceFirst("^[^a-zA-Z]+", "");
			if (!setDocumentMap.containsKey(words[i])) {
				Set<DocumentId> documentSet = new HashSet<>();
				documentSet.add(id);
				setDocumentMap.put(words[i], documentSet);
			} else {
				Set<DocumentId> docuSet = setDocumentMap.get(words[i]);
				docuSet.add(id);
				setDocumentMap.put(words[i], docuSet);
			}
			if (!valueMap.containsKey(words[i])) {
				valueMap.put(words[i], 1);
			} else {
				valueMap.put(words[i], valueMap.get(words[i]) + 1);
			}
		}
		documentMap.put(id, valueMap);
	}
	
	/** The method receives a term and returns the set of the documents that contain the given term
	 * 
	 * @param term - A word in a document or word is being searched in a documents
	 * @return the set of the documents that contain the given term
	 */
	public Set<DocumentId> indexLookup(String term) {
		return setDocumentMap.get(term.toLowerCase());
	}
	
	/**
	 * Returns the frequency of the term in that document
	 * 
	 * @param id - id for the document
	 * @param term - A word where its frequency is calulated
	 * @return the frequency of the term in that document
	 */
	public int termFrequency(DocumentId id, String term) {
		if (documentMap.get(id) == null) {
			throw new IllegalArgumentException("Document id not in search engine.");
		}
		term = term.toLowerCase();
		Map<String, Integer> name = documentMap.get(id);
		if (name.containsKey(term)) {
			return name.get(term);
		} else {
			return 0;
		}
	}
	
	/** Returns the term's inverse document frequency(idf) by computing the terms idf as below.
	 * 
	 * @param term - A word where inverseDocumentFrequency of that word is calculated.
	 * @return the term's inverse document frequency(idf) by computing the terms idf as below.
	 */
	public double inverseDocumentFrequency(String term) {
		term = term.toLowerCase();
		if (!setDocumentMap.containsKey(term)) {
			double M = 0;
			double N = documentMap.size();

			double term1 = Math.log((1 + N) / (1 + M));
			return term1;
		} else {
			double M = setDocumentMap.get(term).size();
			double N = documentMap.size();

			double term1 = Math.log((1 + N) / (1 + M));
			return term1;
		}
	}
	
	/**
	 * Returns the termFrequencyinverseDocumentFrequency(tfidf) of this term for this particular document by multiplying inverseDocumentFrequency to termFrequency.
	 * @param id - id for the document
	 * @param term - A word where tfIdf is calculated
	 * @return the termFrequencyinverseDocumentFrequency(tfidf) of this term for this particular document by multiplying inverseDocumentFrequency to termFrequency.
	 */
	public double tfIdf(DocumentId id, String term) {
		term = term.toLowerCase();
		double x = inverseDocumentFrequency(term) * termFrequency(id, term);
		return x;
	}
	
	/** Returns a sorted list of document ids from most relevant to least relevant for the given term.
	 * 
	 * @param term - A String of arrayList used 
	 * @return a sorted list of document ids from most relevant to least relevant for the given term
	 */
	public List<DocumentId> relevanceLookup(ArrayList<String> terms) {
		Set<DocumentId>  set = new HashSet<>();
		List<DocumentId> id = new ArrayList<>();
		for(String term:terms) {
			if (setDocumentMap.containsKey(term)) {
				set.addAll(setDocumentMap.get(term));
			}
		}
		id.addAll(set);
		Collections.sort(id, new TfIdfComparator(this, terms));
		return id;

	}
	
	/**
	 * Returns both of the Maps in String form.
	 * 
	 * @return  both of the Maps in String form.
	 */
	public String toString() {
		String msg = "";
		msg += "Term to doc id map:" + "\n";
		msg += setDocumentMap + "\n";
		msg += "Doc id to term frequency map: " + "\n";
		msg += documentMap;

		return msg;
	}

}
