package academy.learnprogramming;

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

    // == constanta ==
    //private static final Logger log = LoggerFactory.getLogger(GameImpl.class);

    // == fields ==
    @Getter(AccessLevel.NONE) //because NumberGenerator does not have a getter
    private final NumberGenerator numberGenerator;
    private final int guessCount;
    private int number;

    private int smallest;
    private int biggest;
    private int remainingGuesses;
    private boolean validNumberRange = true;

    @Setter
    private int guess;

    // == constructor based dependency injection ==

    @Autowired
    public GameImpl(NumberGenerator numberGenerator, @GuessCount  int guessCount) {
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;
    }

    // == init ==
    @PostConstruct
    public void reset() {
        smallest = numberGenerator.getMinNumber();
        guess = numberGenerator.getMinNumber();
        remainingGuesses = guessCount;
        biggest = numberGenerator.getMaxNumber();
        number = numberGenerator.next();

    }

    @PreDestroy
    public void preDestroy() {
        log.info("in Game preDestroy()");
    }

    // == public methods==

    public int getNumber() {
        return number;
    }

    public int getGuess() {
        return guess;
    }

    //public void setGuess(int guess) {this.guess = guess;}

    /* These aren't needed with the @Getter annotation from Lombok
    public int getSmallest() {

        return smallest;
    }

    public int getBiggest() {
        return biggest;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public int getGuessCount() {
        return guessCount;
    }
*/
    public void check() {
        checkValidNumberRange();
        if(validNumberRange) {
            if(guess > number) {
                biggest = guess-1;
            }

            if(guess < number) {
                smallest = guess+1;
            }
        }
        remainingGuesses--;
    }
/* Also not needed thanks to Getter
    public boolean isValidNumberRange() {
        return validNumberRange;
    }
*/
    public boolean isGameWon() {
        return guess == number;
    }

    public boolean isGameLost() {
        return !isGameWon() && remainingGuesses <= 0;
    }

    // == private methods ==
    private void checkValidNumberRange() {
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }
}
