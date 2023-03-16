/**
 * Test code for SearchEngine for use in Eclipse.
 */
public class SearchEngineDriver {

	public static void main(String[] args) {
		SearchEngine searchEngine = new SearchEngine();
		
		// *****Testing addDocument *****
		String document = "this is a a sample.";
		DocumentId docId1 = new DocumentId("DOCUMENT1");
		searchEngine.addDocument(docId1, document);
		System.out.println(searchEngine);
		
		document = "this is another another example example example";
		DocumentId docId2 = new DocumentId("DOCUMENT2");
		searchEngine.addDocument(docId2, document);
		System.out.println("\nAfter second document:\n" + searchEngine);
		
		document = "here is another sample example";
		DocumentId docId3 = new DocumentId("DOCUMENT3");
		searchEngine.addDocument(docId3, document);
		System.out.println("\nAfter third document:\n" + searchEngine);
		System.out.println();
		
		// *****Testing indexLookup *****
		System.out.println(searchEngine.indexLookup("a"));
		System.out.println(searchEngine.indexLookup("another"));
		System.out.println(searchEngine.indexLookup("example"));
		System.out.println(searchEngine.indexLookup("sample"));
		System.out.println(searchEngine.indexLookup("hello"));
		
		// *****Testing termFrequency *****
		System.out.println();
		System.out.println(searchEngine.termFrequency(docId1, "a"));
		System.out.println(searchEngine.termFrequency(docId2, "EXAMPLE"));
		System.out.println(searchEngine.termFrequency(docId3, "here"));
		System.out.println(searchEngine.termFrequency(docId3, "there"));
		
		// *****Testing inverseDocumentFrequency *****
		System.out.println();
		System.out.println(searchEngine.inverseDocumentFrequency("a"));
		System.out.println(searchEngine.inverseDocumentFrequency("another"));
		System.out.println(searchEngine.inverseDocumentFrequency("example"));
		System.out.println(searchEngine.inverseDocumentFrequency("sample"));
		System.out.println(searchEngine.inverseDocumentFrequency("hello"));
		
		// *****Testing tfIdf *****
		System.out.println();
		System.out.println(searchEngine.tfIdf(docId1, "a"));
		System.out.println(searchEngine.tfIdf(docId2, "a"));
		System.out.println(searchEngine.tfIdf(docId3, "a"));
		System.out.println(searchEngine.tfIdf(docId1, "EXAMPLE"));
		System.out.println(searchEngine.tfIdf(docId2, "EXAMPLE"));
		System.out.println(searchEngine.tfIdf(docId3, "EXAMPLE"));
		System.out.println(searchEngine.tfIdf(docId3, "here"));
		System.out.println(searchEngine.tfIdf(docId3, "there"));
		
		// *****Testing relevanceLookup *****
//		System.out.println();
//		System.out.println(searchEngine.relevanceLookup("a"));
//		System.out.println(searchEngine.relevanceLookup("another"));
//		System.out.println(searchEngine.relevanceLookup("example"));
//		System.out.println(searchEngine.relevanceLookup("sample"));
//		System.out.println(searchEngine.relevanceLookup("hello"));
	}

}
