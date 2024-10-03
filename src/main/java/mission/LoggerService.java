package mission;

import java.util.logging.Logger;

public class LoggerService {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public void logInfo(String message) {
        log.info(message);
    }
}
