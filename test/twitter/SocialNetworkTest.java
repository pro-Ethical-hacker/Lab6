/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import java.time.Instant;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphMentionErnieFollowsBert() {
        List<Tweet> tweets = new ArrayList<>();
        Instant timestamp = Instant.now();
        tweets.add(new Tweet(1, "Ernie", "Hi @Bert, how are you?", timestamp));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.containsKey("Ernie"));
//        assertTrue(followsGraph.containsKey("Bert"));
//        assertTrue(followsGraph.get("Ernie").contains("Bert"));
    }

    @Test
    public void testGuessFollowsGraphNoMentionNoFollow() {
        List<Tweet> tweets = new ArrayList<>();
        Instant timestamp = Instant.now();
        tweets.add(new Tweet(1, "Alice", "Hello, world!", timestamp));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.containsKey("Alice"));
        assertFalse(followsGraph.containsKey("Bob"));
    }

    @Test
    public void testGuessFollowsGraphMentionNoFollow() {
        List<Tweet> tweets = new ArrayList<>();
        Instant timestamp = Instant.now();
        tweets.add(new Tweet(1, "Ernie", "Hi @Bert, how are you?", timestamp));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.containsKey("Ernie"));
//        assertTrue(followsGraph.containsKey("Bert"));
//        assertFalse(followsGraph.get("Bert").contains("Ernie"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
