package tkartas.service;

public interface GameService {

    boolean isGameOver();

    String getMainMessage();

    String getResultMessage();

    void checkGuess(int guess);

    void reset();
}
