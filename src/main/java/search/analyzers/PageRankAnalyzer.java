package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;
import search.models.Webpage;

import java.net.URI;
import java.security.InvalidParameterException;

/**
 * This class is responsible for computing the 'page rank' of all available webpages.
 * If a webpage has many different links to it, it should have a higher page rank.
 * See the spec for more details.
 */
public class PageRankAnalyzer {
    private IDictionary<URI, Double> pageRanks;

    /**
     * Computes a graph representing the internet and computes the page rank of all
     * available webpages.
     *
     * @param webpages  A set of all webpages we have parsed.
     * @param decay     Represents the "decay" factor when computing page rank (see spec).
     * @param epsilon   When the difference in page ranks is less then or equal to this number,
     *                  stop iterating.
     * @param limit     The maximum number of iterations we spend computing page rank. This value
     *                  is meant as a safety valve to prevent us from infinite looping in case our
     *                  page rank never converges.
     */
    public PageRankAnalyzer(ISet<Webpage> webpages, double decay, double epsilon, int limit) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

        // Step 1: Make a graph representing the 'internet'
        IDictionary<URI, ISet<URI>> graph = this.makeGraph(webpages);

        // Step 2: Use this graph to compute the page rank for each webpage
        this.pageRanks = this.makePageRanks(graph, decay, limit, epsilon);

        // Note: we don't store the graph as a field: once we've computed the
        // page ranks, we no longer need it!
    	
    }

    /**
     * This method converts a set of webpages into an unweighted, directed graph,
     * in adjacency list form.
     *
     * You may assume that each webpage can be uniquely identified by its URI.
     *
     * Note that a webpage may contain links to other webpages that are *not*
     * included within set of webpages you were given. You should omit these
     * links from your graph: we want the final graph we build to be
     * entirely "self-contained".
     */
    private IDictionary<URI, ISet<URI>> makeGraph(ISet<Webpage> webpages) {
    		IDictionary<URI, ISet<URI>> graph = new ChainedHashDictionary<>();
    		
    		for (Webpage page : webpages) {
    			URI uri = page.getUri();
    			graph.put(uri, new ChainedHashSet<>());
    			IList<URI> links = page.getLinks();
    			for (URI link : links) {
    				if (webpages.contains(new Webpage(uri, links, null, null, null)) && !uri.equals(link)) {
    					graph.get(uri).add(link);
    				}
    			}
    		}
    		
    		return graph;
        
    }

    /**
     * Computes the page ranks for all webpages in the graph.
     *
     * Precondition: assumes 'this.graphs' has previously been initialized.
     *
     * @param decay     Represents the "decay" factor when computing page rank (see spec).
     * @param epsilon   When the difference in page ranks is less then or equal to this number,
     *                  stop iterating.
     * @param limit     The maximum number of iterations we spend computing page rank. This value
     *                  is meant as a safety valve to prevent us from infinite looping in case our
     *                  page rank never converges.
     */
    private IDictionary<URI, Double> makePageRanks(IDictionary<URI, ISet<URI>> graph,
                                                   double decay,
                                                   int limit,
                                                   double epsilon) {
    	
    	
    		IDictionary<URI, Double> newRanks = new ChainedHashDictionary<>();
    		double surfers = (1 - decay) / graph.size();
    		
    		
        // Step 1: The initialize step should go here
        for (KVPair<URI, ISet<URI>> page : graph) {
    			newRanks.put(page.getKey(), (double) 1 / graph.size());
    		}
        
        for (int i = 0; i < limit; i++) {
        	
    			IDictionary<URI, Double> updatingRanks = new ChainedHashDictionary<>();
    			IDictionary<URI, Double> updatedRanks = new ChainedHashDictionary<>();
        		// Step 2: The update step should go here
        		
        		for (KVPair<URI, Double> page : newRanks) {
        			URI key = page.getKey();
        			updatingRanks.put(key, 0.0);
        		}
        		
        		for (KVPair<URI, Double> page : newRanks) {
        			URI vectorKey = page.getKey();
        			double vectorValue = page.getValue();
        		    ISet<URI> linkedPages = graph.get(vectorKey);
        		    double updateValue;
        		    
        		    if (!linkedPages.isEmpty()) {
        		    		updateValue = decay * vectorValue / linkedPages.size();
        		    		for (URI linkedPage : linkedPages) {
        		    			if (updatingRanks.containsKey(linkedPage)) {
        		    				double linkedValue = updatingRanks.get(linkedPage);
            		    			updatingRanks.put(linkedPage, linkedValue + updateValue);
        		    			}
        		    		}
        		    } else {
        		    		updateValue = decay * vectorValue / graph.size();
        		    		for (KVPair<URI, Double> updatingPage : updatingRanks) {
        		    			URI key = updatingPage.getKey();
        		    			double value = updatingPage.getValue();
        		    			updatingRanks.put(key, value + updateValue);
        		    		}
        		    }
        		}
        		
        		for (KVPair<URI, Double> page : updatingRanks) {
        			URI key = page.getKey();
        			double value = page.getValue();
        			updatingRanks.put(key, value + surfers);
        			updatedRanks.put(key, value + surfers);
        		}
        		
            // Step 3: the convergence step should go here.
            // Return early if we've converged.
        		
        		int count = 0;
        		
        		for (KVPair<URI, Double> page : updatingRanks) {
        			URI key = page.getKey();
        			double value = page.getValue();
        			if (Math.abs(value - newRanks.get(key)) < epsilon) {
        				count++;
        			}
        		}
        		
        		if (count == graph.size()) {
        			return updatingRanks;
        		}
        		
        		newRanks = updatedRanks;
        		
        }
        return newRanks;
    }

    /**
     * Returns the page rank of the given URI.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public double computePageRank(URI pageUri) {
        if (pageRanks.containsKey(pageUri)) {
        		return pageRanks.get(pageUri);
        }
        throw new InvalidParameterException();
    }
}
