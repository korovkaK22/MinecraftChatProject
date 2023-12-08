package controllers.messages;

public enum MessageType {

        QUIT_MESSAGE("\u001B[3m"), // Курсив
        JOIN_MESSAGE("\u001B[3m"), // Курсив
        CHAT_MESSAGE("\u001B[0m"), // Звичайний текст
        DEFAULT_MESSAGE("\u001B[0m"), // Звичайний текст
        WARN_MESSAGE("\u001B[1m"), // Жирний
        ADVANCEMENTS_MESSAGE("\u001B[3m"); // Курсив

        private final String code;

        MessageType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
}