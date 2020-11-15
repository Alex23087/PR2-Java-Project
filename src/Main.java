public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Post tests:");
        PostTest.testConstructor();
        PostTest.testContainsAny();
        PostTest.testGetAuthor();
        PostTest.testGetMentioned();
        PostTest.testGetText();
        PostTest.testGetId();

        System.out.println("\n\n");

        System.out.println("Starting SocialNetwork tests:");
        SocialNetworkTest.testAddUser();
        SocialNetworkTest.testFollow();
        SocialNetworkTest.testIsFollowedBy();
        SocialNetworkTest.testCreatePost();
        SocialNetworkTest.testContaining();
        SocialNetworkTest.testWrittenBy();
        SocialNetworkTest.testGetMentionedUsers();
        SocialNetworkTest.testInfluencers();
        SocialNetworkTest.testGuessFollowers();
        SocialNetworkTest.testPublishedPostWithId();

        System.out.println("\n\n");

        System.out.println("Starting DeletableSocialNetwork tests:");
        DeletableSocialNetworkTest.testDeletePost();
    }
}
