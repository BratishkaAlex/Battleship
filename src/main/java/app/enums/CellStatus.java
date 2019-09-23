package app.enums;

public class CellStatus {
    public enum CellStatuses {
        DONE("done"), EMPTY("empty"), HIT("hit");
        private final String status;

        CellStatuses(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }
}
