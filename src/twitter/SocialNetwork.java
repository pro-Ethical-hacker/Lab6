/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        for (Tweet tweet : tweets) {
            String author = tweet.getAuthor();
            String text = tweet.getText();
            List<String> mentions = extractMentions(text);

            // Ensure the author is in the followsGraph
            followsGraph.putIfAbsent(author, new HashSet<>());

            for (String mention : mentions) {
                // Add the mentioned user to the set of users followed by the author
                followsGraph.get(author).add(mention);

                // Ensure the mentioned user is in the followsGraph
                followsGraph.putIfAbsent(mention, new HashSet<>());
            }
        }

        return followsGraph;
    }

    private static List<String> extractMentions(String text) {
        List<String> mentions = new ArrayList<>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            if (word.startsWith("@") && word.length() > 1) {
                mentions.add(word.substring(1));
            }
        }

        return mentions;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> followerCount = new HashMap<>();

        // Count the followers for each user
        for (Set<String> followers : followsGraph.values()) {
            for (String follower : followers) {
                followerCount.put(follower, followerCount.getOrDefault(follower, 0) + 1);
            }
        }

        // Create a list of users sorted by follower count in descending order
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(followerCount.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<String> influencers = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            influencers.add(entry.getKey());
        }

        return influencers;
    }


}
