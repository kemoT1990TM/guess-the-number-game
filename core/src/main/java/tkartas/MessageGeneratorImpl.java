package tkartas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

    // == constants ==
    private static final String MAIN_MESSAGE="game.main.message";
    private static final String WIN="game.win";
    private static final String LOST="game.lost";
    private static final String INVALID_RANGE="game.invalid.range";
    private static final String FIRST_GUESS="game.first.guess";
    private static final String HIGHER="game.higher";
    private static final String LOWER="game.lower";
    private static final String REMAINING="game.remaining";

    // == fields ==
    private final Game game;
    private final MessageSource messageSource;

    // == constructors ==
    public MessageGeneratorImpl(Game game, MessageSource messageSource) {
        this.game = game;
        this.messageSource = messageSource;
    }

    // == init ==
    @PostConstruct
    public void init(){
        log.info("game = {}",game);
    }

    // == public methods ==

    @Override
    public String getMainMessage() {
//        return "Number is between "+
//                game.getSmallest()+
//                " and "+
//                game.getBiggest()+
//                ". Can you guess it?";
        return getMessage(MAIN_MESSAGE,game.getSmallest(),game.getBiggest());
    }

    @Override
    public String getResultMessage() {
        if (game.isGameWon()) {
//            return "You guessed it! The number was " + game.getNumber();
           return getMessage(WIN,game.getNumber());
        } else if (game.isGameLost()) {
//            return "You lost! The number was " + game.getNumber();
            return getMessage(LOST,game.getNumber());
        } else if (!game.isValidNumberRange()) {
//            return "Invalid number range!";
            return getMessage(INVALID_RANGE);
        } else if(game.getRemainingGuesses()==game.getGuessCount()){
//            return "What is your first guess?";
            return getMessage(FIRST_GUESS);
        } else {
//            String direction = "Lower";
            String direction = getMessage(LOWER);
            if(game.getGuess()<game.getNumber()){
//                direction = "Higher";
                direction=getMessage(HIGHER);
            }
//            return direction+"! You have "+game.getRemainingGuesses()+" guesses left";
            return getMessage(REMAINING,direction,game.getRemainingGuesses());
        }
    }

    // == private methods ==
    private String getMessage(String code, Object... args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
