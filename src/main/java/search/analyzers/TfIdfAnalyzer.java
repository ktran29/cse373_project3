package search.analyzers;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;
import java.security.InvalidParameterException;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    // Feel free to add extra fields and helper methods.

    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: Feel free to change or modify these methods if you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * This method should return a dictionary mapping every single unique word found
     * in any documents to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
        IDictionary<String, Double> dict = new ArrayDictionary<>();
        int totalPages = 0;
        
        // Create initial Dictionary
        for (Webpage page : pages) {
            totalPages++;
            IDictionary<String, Double> temp = new ArrayDictionary<>();
            for (String term : page.getWords()) {
                temp.put(term, 1.0);
            }
            // add instance of word in doc to dictionary
            for (KVPair<String, Double> pair : temp) {
                String word = pair.getKey();
                if (dict.containsKey(word)) {
                    dict.put(word, dict.get(word) + 1);
                } else {
                    dict.put(word,  1.0);
                }
    			
            }
        }
        // Calculate IDF
        for (KVPair<String, Double> pair : dict) {
            String word = pair.getKey();
            dict.put(word, Math.log((totalPages / dict.get(word))));
        }
        return dict;
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * We are treating the list of words as if it were a document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        IDictionary<String, Double> dict = new ArrayDictionary<>();
        int totalWords = 0;
    	
        // Collect total number of words & words of each type in doc
        for (String word : words) {
            totalWords++;
            if (dict.containsKey(word)) {
                dict.put(word, (dict.get(word) + 1));
            } else {
                dict.put(word, 1.0);
            }
        }
    	
        // Calculate number tf for each word
        for (KVPair<String, Double> pair : dict) {
            String word = pair.getKey();
            dict.put(word, (dict.get(word) / totalWords));
        }
        return dict;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
    	
        // Create entire dictionary
        IDictionary<URI, IDictionary<String, Double>> tfIdfVectors = new ArrayDictionary<>();
        for (Webpage page : pages) {
            IDictionary<String, Double> tfScores = computeTfScores(page.getWords());
            for (KVPair<String, Double> pair : tfScores) {
                String word = pair.getKey();
                tfScores.put(word, (tfScores.get(word) * idfScores.get(word)));
            }
            tfIdfVectors.put(page.getUri(), tfScores);
        }
        return tfIdfVectors;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        if (documentTfIdfVectors.containsKey(pageUri)) {
            IDictionary<String, Double> documentVector = documentTfIdfVectors.get(pageUri);
            IDictionary<String, Double> queryVector = computeQueryTdIdf(query);
            double numerator = 0.0;
            
            for (KVPair<String, Double> word : queryVector) {
                double docWordScore = 0.0;
                if (documentVector.containsKey(word.getKey())) {
                    docWordScore += documentVector.get(word.getKey());
                }
                double queryWordScore = queryVector.get(word.getKey());
                numerator += docWordScore * queryWordScore;
                
            }
            
            double denominator = norm(documentVector) * norm(queryVector);
            
            if (denominator != 0.0) {
                return numerator / denominator;
            }
            
            return 0.0;
        }
        
        throw new InvalidParameterException();
    }
    
    private IDictionary<String, Double> computeQueryTdIdf(IList<String> query) {
        IDictionary<String, Double> dict = new ArrayDictionary<>();
        int totalWords = 0;
    	
        for (String word : query) {
            totalWords++;
            if (dict.containsKey(word)) {
                dict.put(word, (dict.get(word) + 1));
            } else {
                dict.put(word, 1.0);
            }
        }
    	
        // Calculate number tf for each word
        for (KVPair<String, Double> pair : dict) {
            String word = pair.getKey();
            dict.put(word, (dict.get(word) / totalWords) * idfScores.get(word));
        }
        return dict;
    	
    	
    }
    
    private Double norm(IDictionary<String, Double> vector) {
        double output = 0.0;
        for (KVPair<String, Double> pair : vector) {
            double score = pair.getValue();
            output += score * score;
        }
        return Math.sqrt(output);
    }
}
