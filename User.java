/** Represents a user in a social network. A user is characterized by a name,
 *  a list of user names that s/he follows, and the list's size. */
 public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; // fixed-size array for storing followees
        fCount = 0;                      // initial number of followees
    }

    /** Creates a user with some followees. The only purpose of this constructor is 
     *  to allow testing the toString and follows methods, before implementing other methods. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /** Returns the name of this user. */
    public String getName() {
        return name;
    }

    /** Returns the follows array. */
    public String[] getfFollows() {
        return follows;
    }

    /** Returns the number of users that this user follows. */
    public int getfCount() {
        return fCount;
    }

    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        // we just iterate over the array and check if the name is inside
        for(int i = 0; i < fCount; i++) {
            if(follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }
    /** Makes this user follow the given name. If successful, returns true. 
     *  If this user already follows the given name, or if the follows list is full, does nothing and returns false; */
    public boolean addFollowee(String name) {
        // we first check the capacity hasn't been reached
        // then we check the name isn't already in the array and only then do 
        // we add the followee
        if (fCount < maxfCount && !follows(name)) {
            follows[fCount] = name;
            fCount++;
            return true;
        }
        return false;
    }

    /** Removes the given name from the follows list of this user. If successful, returns true.
     *  If the name is not in the list, does nothing and returns false. */
    public boolean removeFollowee(String name) {
        // The cases in which the function yields nothing
        if (fCount == 0) return false;
        if (!this.follows(name)) return false;
        // an array of the size of the follows array-1 as we remove one value from it
        String[] tempFollows = new String[fCount];
        int index = 0;
        
        for (int i = 0; i < fCount; i++){
            if (follows[i] != name) {
                tempFollows[index] = follows[i];
                index++;
            } 
        }
        // we decrement fCount as we removed a followee
        fCount--;
        // we didn't include the value we wanted to remove in the new array
        // thus we just set the follows array to be the one without the name
        follows = tempFollows;
        return true;
    }

    /** Counts the number of users that both this user and the other user follow.
    /*  Notice: This is the size of the intersection of the two follows lists. */
    public int countMutual(User other) {
        int countM = 0;
        // we go over this User's followees and increment the counter
        // every time we find a new follower that is in common using the follows
        // method of the other user
        for (int i = 0; i < fCount; i++){
            if (other.follows(follows[i])) countM++;
        }
        return countM;
    }

    /** Checks is this user is a friend of the other user.
     *  (if two users follow each other, they are said to be "friends.") */
    public boolean isFriendOf(User other) {
        // we just use the follows method for the name of the other user
        // and also this user 
        if (this.follows(other.name) && other.follows(name)) return true;
        return false;
    }
    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}
