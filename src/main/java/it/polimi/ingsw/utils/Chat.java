package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     Class that implements an ArrayList of messages creating a chat
 * </p>
 * <p>
 *     It manages the addition and removal of messages in the Chat
 *     and it preserves them in an ArrayList that becomes the Chat's history
 * </p>
 */
public class Chat implements Serializable {
    private final ArrayList<ChatMessage> chatMessages;        // List of previous messages from latest (index = 0) to oldest (index = oldestMessage)
    private boolean chatIsEnable;
    private int oldestMessage;              // Position in the ArrayList of the oldestMessage
    private final int maxNumberOfMessages;              // Maximum number of messages allowed in the chat
    private int unReadMessages;

    /**
     * Constructor standard that sets the maximum
     * number of messages contained in a Chat
     * automatically to 20
     */
    public Chat () {
        chatMessages = new ArrayList<>();
        maxNumberOfMessages = 20;
        unReadMessages = 0;
        chatIsEnable = false;
    }

    /**
     * Constructor that sets the maximum
     * number of messages contained in a
     * Chat to @maxNumberOfMessages
     *
     * @param maxNumberOfMessages       The maximum number of messages in the chat's history
     */
    public Chat(int maxNumberOfMessages) {
        chatMessages = new ArrayList<>();
        this.maxNumberOfMessages = maxNumberOfMessages;
        unReadMessages = 0;
        chatIsEnable = false;
    }

    /**
     * <p>
     *    Method that adds a new ChatMessage into the Chat
     * </p>
     * <p>
     *     In case the Chat is full it refreshes the
     *     history of messages by deleting the oldest one
     * </p>
     *
     * @param message       ChatMessage to add
     */
    public synchronized void addMessage(ChatMessage message) {
        // Managing the spacial case of first message in Chat
        if (chatMessages.isEmpty()) {
            chatMessages.add(0, message);
            oldestMessage = 0;
            return;
        }

        // Checking for Chat's refresh
        if (oldestMessage == (maxNumberOfMessages -1)) chatRefresh();

        chatMessages.add(0, message);
        oldestMessage++;
    }

    /**
     * <p>
     *    Method that creates a new ChatMessage from the given
     *    username and message and adds it into the Chat.
     * </p>
     * <p>
     *     In case the Chat is full it refreshes the
     *     history of messages by deleting the oldest one
     * </p>
     *
     * @param username      Player sending the message
     * @param message       Message sent
     */
    public synchronized void addMessage(String username, String message) {
        // Managing the spacial case of first message in Chat
        if (chatMessages.isEmpty()) {
            chatMessages.add(0, new ChatMessage(username, message));
            oldestMessage = 0;
            return;
        }

        // Checking for Chat's refresh
        if (oldestMessage == (maxNumberOfMessages -1)) chatRefresh();

        chatMessages.add(0, new ChatMessage(username, message));
        oldestMessage++;
    }

    /**
     * <p>
     *    Method that creates a new ChatMessage from the given
     *    forwarding player, message and receiving player
     *    and adds it into the Chat
     * </p>
     * <p>
     *     In case the Chat is full it refreshes the
     *     history of messages by deleting the oldest one
     * </p>
     *
     * @param forwardingPlayer      Player sending the message
     * @param message       Message sent
     * @param receivingPlayer       Player receiving the message
     */
    public synchronized void addMessage(String forwardingPlayer, String message, String receivingPlayer) {
        // Managing the spacial case of first message in Chat
        if (chatMessages.isEmpty()) {
            chatMessages.add(0, new ChatMessage(forwardingPlayer, message, receivingPlayer));
            oldestMessage = 0;
            return;
        }

        // Checking for Chat's refresh
        if (oldestMessage == (maxNumberOfMessages -1)) chatRefresh();

        chatMessages.add(0, new ChatMessage(forwardingPlayer, message, receivingPlayer));
        oldestMessage++;
    }

    /**
     * Method that deletes the oldest message in the Chat
     */
    private void chatRefresh () {
        chatMessages.remove(oldestMessage);
        oldestMessage--;
    }

    /**
     * <strong>Getter</strong> -> Gets all Chat's messages
     *
     * @return      Chat's history
     */
    public List<ChatMessage> getChatMessages () {return chatMessages;}

    /**
     * <strong>Getter</strong> -> Gets the position of the oldest message
     *
     * @return      Integer representing the position in the ArrayList of the oldest message
     */
    public int getOldestMessage() {return oldestMessage;}

    /**
     * Method that updates the number representing the un read message
     */
    public synchronized void updateUnReadMessages () {unReadMessages++;}

    /**
     * Method that resets to zero the
     * number representing the un read message
     */
    public synchronized void resetUnReadMessages () {unReadMessages = 0;}

    /**
     * <strong>Getter</strong> -> Gets the number of unread messages
     *
     * @return      Number of unread messages
     */
    public int getUnReadMessages() {return unReadMessages;}

    /**
     * <strong>Getter</strong> -> Gets the maximum number of messages allowed in the Chat's history
     *
     * @return      Maximum number of messages
     */
    public int getMaxNumberOfMessages() {return maxNumberOfMessages;}

    /**
     * <strong>Getter</strong> -> Gets boolean used to check if the Chat is open on screen
     *
     * @return      Boolean used to check if the Chat is open on screen
     */
    public boolean ChatIsEnable() {
        return chatIsEnable;
    }

    /**
     * <strong>Setter</strong> -> Sets the boolean ChatIsEnable to ChatIsEnable
     *
     * @param chatIsEnable      Boolean used to set chatIsEnable
     */
    public void setChatIsEnable(boolean chatIsEnable) {
        this.chatIsEnable = chatIsEnable;
    }

    /**
     * Method that does Override of equals in order
     * to properly confront 2 Chat-like structures
     *
     * @param o     Chat-type Object to analyze
     * @return      True or false depending on the result of the confrontation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return oldestMessage == chat.oldestMessage &&
                maxNumberOfMessages == chat.maxNumberOfMessages &&
                unReadMessages == chat.unReadMessages &&
                chatIsEnable == chat.chatIsEnable &&
                Objects.equals(chatMessages, chat.chatMessages);
    }

    //TODO sembra non venga mai utilizzato - FLA
    /**
     * Method defined in the class Object that allows to extend the
     * "equals" confrontation to hash-type structures
     *
     * @return      Returns an entire value that represents the hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(chatMessages, chatIsEnable, oldestMessage, maxNumberOfMessages, unReadMessages);
    }
}
