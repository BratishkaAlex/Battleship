package app.enums;

public class Enums {
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

    public enum CellStatus {
        DONE("done"), EMPTY("empty"), HIT("hit");
        private final String status;

        CellStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }
}
