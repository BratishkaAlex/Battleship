package app.enums;

public class EndGameNotification {
    public enum EndGameNotificationMessages {
        WIN("You win"), LOSE("You lose"), ENEMY_LEAVE("Enemy has left the game"), SERVER_ERROR("Server error"), GAME_ERROR("Game error");
        private final String message;

        EndGameNotificationMessages(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
