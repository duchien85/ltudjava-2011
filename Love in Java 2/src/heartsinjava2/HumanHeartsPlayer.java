package heartsinjava2;

import java.util.*;

public final class HumanHeartsPlayer extends AbstractPlayer implements HeartsPlayer {

    public HumanHeartsPlayer(String name) throws ArgumentNullException {
        super(name);
    }

    public Deck.Card[] discardThreeCardsToPass(PassDirection passDirection) {
        final Deck.Card[] cards = new Deck.Card[3];
        final ArrayList<Deck.Card> hand = this.getHand();

        //see Deck - compareTo
        Collections.sort(hand);

        for (int index = 0; index < 3; index++) {
            cards[index] = chooseCard();
        }
               
        System.out.printf("%s passes %s %s\n", this.getName(), passDirection, HeartsUtilities.formatCards(cards));
        return cards;
    }

    public Deck.Card playTrick(Deck.Card[] playedCards, Suit trickSuit) {
        final ArrayList<Deck.Card> hand = this.getHand();
        Deck.Card cardToPlay;

        Collections.sort(hand);

        cardToPlay = chooseCard();

        System.out.printf("%s plays %s\n", this.getName(), HeartsUtilities.formatCard(cardToPlay));
        return cardToPlay;
    }

    //return card chosen by the player
    public Deck.Card chooseCard() {
        final ArrayList<Deck.Card> hand = this.getHand();
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        do {
            try {
                System.out.print(HeartsUtilities.printToPlayer(hand));
                System.out.print("Choose your card. Enter a number: ");
                int choice = scanner.nextInt();
                Deck.Card cardToPlay = null;

                for (Deck.Card card : hand) {
                    if (hand.indexOf(card) == choice) {
                        System.out.printf("You chose %s\n", card.toString());
                        cardToPlay = card;
                    }
                }
                
                hand.remove(hand.indexOf(cardToPlay));
                continueLoop = false;
                return cardToPlay;
            }
            catch (ArrayIndexOutOfBoundsException indexException) {
                System.err.printf("\nException: %s\n", indexException);
                System.out.println("No such card. Choose your card again.");
            }
            catch (InputMismatchException inputMismatchException) {
                System.err.printf("\nException: %s\n", inputMismatchException);
                scanner.nextLine();
                System.out.println("No such card. Choose your card again.");
            }
        } while (continueLoop);

        return null;
    }
}