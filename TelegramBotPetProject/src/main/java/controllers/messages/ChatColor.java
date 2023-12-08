package controllers.messages;

public enum ChatColor {
        BLACK("\u001B[30m"),
        DARK_BLUE("\u001B[34m"),
        DARK_GREEN("\u001B[32m"),
        DARK_AQUA("\u001B[36m"),
        DARK_RED("\u001B[31m"),
        DARK_PURPLE("\u001B[35m"),
        GRAY("\u001B[37m"),
        DARK_GRAY("\u001B[90m"),
        BLUE("\u001B[94m"),
        GREEN("\u001B[92m"),
        AQUA("\u001B[96m"),
        RED("\u001B[91m"),
        LIGHT_PURPLE("\u001B[95m"),
        YELLOW("\u001B[93m"),
        WHITE("\u001B[97m");

        private final String code;

    ChatColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

}
