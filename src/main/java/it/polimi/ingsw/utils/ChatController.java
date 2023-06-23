package it.polimi.ingsw.utils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>
 *     Class to manage all Chats behavior
 * </p>
 * <p>
 *     It contains:
 *     <ul>
 *         <li>1 publicChat</li>
 *         <li>all privateChats grouped in a HashMap accessible by player's username</li>
 *     </ul>
 * </p>
 * <p>
 *     Its role is to group all chats in one place
 *     and to manage their construction and calls
 * </p>
 */
public class ChatController implements Serializable {
    private Chat publicChat;
    private HashMap<String, Chat> privateChats;

    /**
     * <p>
     *     Constructor for Client
     * </p>
     * <p>
     *     Instantiate both the publicChat and
     *     the HashMap for the privateChats
     * </p>
     */
    public ChatController() {
        publicChat = new Chat();
        privateChats = new HashMap<>();
    }

    /**
     * <p>
     *     Constructor for Server
     * </p>
     * <p>
     *     Instantiate only the HashMap for the privateChats
     * </p>
     * <p>
     *     In order to save memory the publicChat on server's
     *     hand is separated from the ChatController because
     *     equals for all players. In this way the maximum number
     *     of Chats instanced will be, in a 4 players game, 13 (3
     *     private chats for each player + 1 public instead of 4)
     *     making the backup upload faster
     * </p>
     */
    public ChatController (boolean forServer){
        privateChats = new HashMap<>();
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public Chat getPrivateChat (String username) {
        return privateChats.get(username);
    }

    public HashMap<String, Chat> getPrivateChats() {
        return privateChats;
    }

    public void setPublicChat (Chat publicChat) { this.publicChat = publicChat; }

    public void setPrivateChats(HashMap<String, Chat> privateChats) { this.privateChats = privateChats; }

}