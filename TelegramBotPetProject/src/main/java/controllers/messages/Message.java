package controllers.messages;

public class Message {
    private MessageType messageType = MessageType.CHAT_MESSAGE;
    private String sender = "undefined";
    private String text = "undefined";
    private ChatColor chatColor = ChatColor.WHITE;
    private String platform = "undefined";

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        if (sender == null){
            throw new NullPointerException("sender is null");
        }
        if (sender.trim().length() == 0){
            throw new IllegalArgumentException("sender size is 0");
        }
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (this.text == null){
            throw new NullPointerException("text is null");
        }
        if (this.text.trim().length() == 0){
            throw new IllegalArgumentException("text size is 0");
        }
        this.text = text;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public void setChatColor(ChatColor chatColor) {
        this.chatColor = chatColor;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageType != message.messageType) return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return chatColor == message.chatColor;
    }

    @Override
    public int hashCode() {
        int result = messageType != null ? messageType.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (chatColor != null ? chatColor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", chatColor=" + chatColor +
                '}';
    }



}
