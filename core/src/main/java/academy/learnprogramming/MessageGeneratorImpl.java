package academy.learnprogramming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

   // private static final Logger log = LoggerFactory.getLogger(MessageGeneratorImpl.class);

    // == fields ==
    private final Game game;

    // == constructors
    @Autowired
    public MessageGeneratorImpl(Game game) {
        this.game = game;
    }

    // == public methods ==
    @PostConstruct
    public void gameValue() {
        log.debug("Value of game is {}", game);
    }

    public String getMainMessage() {
        return "Number is between " +
                game.getSmallest() +
                " and " +
                game.getBiggest() +
                ". Can you guess it?";
    }

    public String getResultMessage() {
        if(game.isGameWon()) {
            return "You guessed it! The number was " + game.getNumber();
        } else if(game.isGameLost()) {
            return "You lost. The number was " + game.getNumber();
        } else if (!game.isValidNumberRange()){
            return "Invalid number range!";
        } else if(game.getRemainingGuesses() == game.getGuessCount()){
            return "What is your first guess?";
        } else {
            String direction = "Lower";

            if(game.getGuess() < game.getNumber()) {
                direction = "Higher";
            }

            return direction + "! You have " + game.getRemainingGuesses() + " guesses left.";
        }
    }
}
