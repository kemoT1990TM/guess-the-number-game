package tkartas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Getter
@Component
public class GameImpl implements Game {

    // == fields ==
    private final int guessCount;
    private int number;
    private int smallest;
    private int biggest;
    private int remainingGuesses;
    private boolean validNumberRange=true;

    @Setter
    private int guess;

    @Getter(AccessLevel.NONE)
    private final NumberGenerator numberGenerator;

    @Autowired
    public GameImpl(@GuessCount int guessCount,NumberGenerator numberGenerator) {
        this.guessCount = guessCount;
        this.numberGenerator=numberGenerator;
    }

    // == init ==
    @PostConstruct
    @Override
    public void reset() {
        smallest=0;
        guess=0;
        remainingGuesses=guessCount;
        biggest=numberGenerator.getMaxNumber();
        smallest=numberGenerator.getMinNumber();
        number=numberGenerator.next();
        log.debug("the number is {}",number);
    }

    @PreDestroy
    public void preDestroy(){
        log.info("in Game preDestroy");
    }

    // == public methods ==
//    Setter not needed as we are using Autowiring
//    public void setNumberGenerator(NumberGenerator numberGenerator){
//        this.numberGenerator=numberGenerator;
//    }

    @Override
    public void check() {
        checkValidNumberRange();

        if(validNumberRange){
            if(guess>number){
                biggest=guess-1;
            }
            if(guess<number){
                smallest=guess+1;
            }
        }
        remainingGuesses--;
    }

    @Override
    public boolean isGameWon() {
        return guess==number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWon() && remainingGuesses<=0;
    }

    // == private methods ==
    private void checkValidNumberRange(){
        validNumberRange=(guess>=smallest)&&(guess<=biggest);
    }
}
