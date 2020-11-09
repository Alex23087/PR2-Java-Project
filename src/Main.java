public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Post tests:");
        PostTest.testConstructor();
        PostTest.testContainsAny();
        PostTest.testGetAuthor();
        PostTest.testGetMentioned();
        PostTest.testGetText();


        System.out.println("Starting Post tests:");
        SocialNetworkTest sntestset = new SocialNetworkTest();
        sntestset.testAddUser();
        SocialNetworkTest.testFollow();
        SocialNetworkTest.testIsFollowedBy();
        SocialNetworkTest.testCreatePost();
        SocialNetworkTest.testContaining();
        SocialNetworkTest.testWrittenBy();
    }
}
