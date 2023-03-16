/** SearchEngineGUI allows the user to enter a search term and lists the
 * documents known to the search engine with that term with the most
 * relevant listed first.
 */

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class SearchEngineGUI implements ActionListener {

	public static void main(String[] args) throws FileNotFoundException {
		// call the constructor - runs the application
		new SearchEngineGUI();
	}

	// constants
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;

	private static String FILE_NAME = "data.txt";

	// frame to hold GUI
	private JFrame frame;

	// variable holding a Content object
	private SearchEngine searchEngine;

	// variables for search field and area for displaying details
	private JTextField searchField;

	// button for searching for term
	private JButton buttonSearch;

	// Area to display the relevant documents
	private JTextArea relevantDocArea;

	/**
	 * Constructs a Search Engine gui to allow entering a search term and 
	 * seeing the resulting "documents" with the search term
	 * @throws FileNotFoundException if the file of documents is not found
	 */
	public SearchEngineGUI() throws FileNotFoundException {
		// initialize the search engine with data from a file
		searchEngine = readFile();
		
		// initialize instance variables
		searchField = new JTextField("", 10);
		buttonSearch = new JButton("Search");

		// set a few properties of the window
		frame = new JFrame("Search Engine");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(0, 10));
		frame.add(constructNorthPanel(), BorderLayout.NORTH);
		frame.add(constructCenterPanel(), BorderLayout.CENTER);

		// Register to listen to button and enter in search field
		buttonSearch.addActionListener(this);
		searchField.addActionListener(this);

		frame.setVisible(true);
	}

	//constructs and returns the panel for the search field and button
	private JPanel constructNorthPanel() {
		JPanel north = new JPanel(new FlowLayout());
		north.add(searchField);
		north.add(buttonSearch);
		
		// make the search field look nicer
		searchField.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		searchField.setFont(new Font("Times", Font.ITALIC, 16));
		
		return north;
	}
	
		
	// constructs and returns the panel with the text area
	private JPanel constructCenterPanel() {
		JPanel center = new JPanel(new FlowLayout());
		
		relevantDocArea = new JTextArea();
		relevantDocArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		relevantDocArea.setBackground(frame.getBackground());
		relevantDocArea.setEditable(false);
		center.add(relevantDocArea);
		
		return center;
	}

	

	/**
	 * Called search button is clicked or enter pressed in search field
	 * 
	 * @param event
	 *            which signaled the button click
	 */
	public void actionPerformed(ActionEvent event) {
		String term = searchField.getText().strip();
		
		// if multiple words entered, use the first one.
		String[] termArray = term.split("\\s+");
		ArrayList<String> searchTerms = new ArrayList<>();
		for(int i=0;i<termArray.length; i++) {
			searchTerms.add(termArray[i]);
		}
		
		
		// check that search phrase isn't empty
		if (term != null && !term.strip().isEmpty()) {
			// search the videoContent for an item with searchPhrase
			List<DocumentId> listDocs = searchEngine.relevanceLookup(searchTerms);
			if (!listDocs.isEmpty()) {
				String docStrings = "Results for " + term + ":\n";
				for (DocumentId docId : listDocs) {
					docStrings += docId.toString().substring(docId.toString().indexOf("=") + 1) + "\n";
				}
				relevantDocArea.setText(docStrings);
			}else {
				relevantDocArea.setText("No documents contained \"" + term + "\".");
			}
		}
	}

	// reads a file to populate the content list
	private SearchEngine readFile() throws FileNotFoundException {
		SearchEngine engine = new SearchEngine();
		Scanner input = new Scanner(new File(FILE_NAME));
		while (input.hasNextLine()) {
			String documentText = input.nextLine().strip();
			String documentId = input.nextLine().strip();
			DocumentId docId = new DocumentId(documentId);
			engine.addDocument(docId, documentText);
			
		}
		return engine;
	}

}
