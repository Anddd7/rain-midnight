package github.eddy.game.excepution;

/**
 * @author edliao
 */
public class ServerException extends RuntimeException {

  public ServerException(String message) {
    super(message);
  }

  public ServerException(Throwable cause) {
    super(cause);
  }
}
