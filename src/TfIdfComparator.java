/**
 * Compare two documents in a search engine by tf-idf using a given term.
 * 
 * Using this comparator, the *larger* item should "come before" a smaller one so
 * that sort returns the list in descending (largest-to-smallest) order. 
 * 
 * It breaks ties by using the lexicographic ordering of the document IDs (that is, by using 
 * o1.id.compareTo(o2.id)).
 * 
 */
import java.util.Comparator;
import java.util.*;

public class TfIdfComparator implements Comparator<DocumentId> {
	private final SearchEngine searchEngine;
	private final ArrayList<String> term;
	
	public TfIdfComparator(SearchEngine searchEngine, ArrayList<String> term) {
		this.searchEngine = searchEngine;
		this.term = term;
	}
	private double weightedAverage(DocumentId id) {
		if(term.size()==1) {
			return searchEngine.tfIdf(id, term.get(0));
		}
		return searchEngine.tfIdf(id, term.get(0))*0.6 +searchEngine.tfIdf(id, term.get(1))*0.4;
	}
	@Override
	public int compare(DocumentId o1, DocumentId o2) {
		if (weightedAverage(o1) < weightedAverage(o2)) {
			return 1;
		} else if (weightedAverage(o1) > weightedAverage(o2)) {
			return -1;
		} else {
			return o1.getDocumentId().compareTo(o2.getDocumentId());
		}
	}
}
