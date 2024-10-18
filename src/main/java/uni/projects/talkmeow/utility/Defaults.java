package uni.projects.talkmeow.utility;

import java.util.List;

public class Defaults {

    public static final int MAX_BAN_STRIKES = 3;

    public static final List<String> prohibitedWords = List.of(
            "cilantro", "dog", "lonely", "sad", "depressed", "unhappy",
            "unloved", "unwanted", "unneeded", "unappreciated", "unworthy",
            "unimportant", "unlovable", "unloving", "miserable", "hopeless",
            "worthless", "useless", "pathetic", "failure", "defeated", "broken", "licorice", "hate", "XD", "lmao", "lol", "rofl", "lmfao", "haha", "hehe"
    );

    public static final String passwordRegex = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*\\d.*\\d)(?=.*[a-z].*[a-z].*[a-z]).{8,}$";
    public static final String usernameRegex = "^[a-zA-Z0-9_]+$";
    public static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";


}
