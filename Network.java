/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        // if users is empty no user exists with any name
        if (userCount == 0) return null;
        if (name == null) return null;
        // we go over all users
        for (int i = 0; i < userCount; i++){
            // we search for the user and return it if found
            if (users[i].getName().toLowerCase().equals(name.toLowerCase())) return users[i];
        }
        // not found
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        // users array is filled
        if (userCount == users.length || 
                        this.getUser(name) != null)
                                return false;
        // if the user doesn't exist in the netowrk we add it and increment the count
        if (this.getUser(name) == null){
            users[userCount] = new User(name);
            userCount++;
            return true;
        }
        // if the user already exists in the network
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User user1 = this.getUser(name1);
        User user2 = this.getUser(name2);
        // one of the users is not found in the network
        if (user1 == null || user2 == null) return false;
        // if user1 already follows user2 or if user1 is user2 we cannot add the followee
        if (user1.follows(name2) || user1 == user2) { 
            return false;
        } else {
            // tries to make user1 follow user2 if successful will return true
            boolean hasWorked = user1.addFollowee(name2);
            if (hasWorked) return true;
        }
        
        // wasn't successful in adding the followee
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int maxMutual = 0;
        String recommendedFollowee = "";
        User givenUser = this.getUser(name);
        // if we have less then 2 users we cannot recommend anyone 
        // also if the given user is not even in the network we cannot recommend anyone
        if (userCount <= 1) return recommendedFollowee;
        if (givenUser == null) return recommendedFollowee;
        for (int i = 0; i < userCount; i++){
            // we check first that the user isn't the given one
            if (users[i] != givenUser){
                // here we just count mutual followees and compare to the max
                int currMutualCount = users[i].countMutual(givenUser);
                if (maxMutual < currMutualCount){
                    maxMutual = currMutualCount;
                    recommendedFollowee = users[i].getName();
                }
            }
        }
        return recommendedFollowee;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int mostAppearences = 0;
        String mostPopularUser = null;
        // if no users are in the network we have no reason to check
        if (userCount == 0) return mostPopularUser;
        // if only one user is in the network he is the most popular by default
        if (userCount == 1) return users[0].getName();
        for (int i = 0; i < userCount; i++){
            // we count the number of followees of each user
            // and find the user with the most followees
            int currNumFollowees = followeeCount(users[i].getName());
            if (mostAppearences < currNumFollowees){
                mostAppearences = currNumFollowees;
                mostPopularUser = users[i].getName();
            }
        }
        return mostPopularUser;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int numFollowees = 0;
        // iterates over the network, searching for users which follow 
        // the user of the given name
        for(int i = 0; i < userCount; i++){
            if (users[i].follows(name)) numFollowees++;
        }
        return numFollowees;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       // we go over the network adding each user's description to the
        // textual description of the network
        String strToPrint = "Network:";
        for (int i = 0; i < userCount; i++){
            strToPrint += "\n" +users[i].toString();
        }
        
        return strToPrint;
    }
}
