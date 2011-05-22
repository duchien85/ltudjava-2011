package heartsinjava2;

import java.util.ArrayList;

public final class HeartsUtilities {

    private HeartsUtilities() {
    }

    public static boolean isPointCard(Deck.Card card) {
        return card.getSuit() == Suit.HEARTS ||
                (card.getSuit() == Suit.SPADES && card.getRank() == Rank.QUEEN);
    }

    public static String formatCard(Deck.Card card) {
        return HeartsUtilities.formatCards(new Deck.Card[] { card });
    }

    public static String formatCards(Deck.Card[] cards) {
        StringBuilder stringBuilder = new StringBuilder("{ ");

        for (Deck.Card card : cards) {
            stringBuilder.append(card.toString());
            stringBuilder.append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public static String printToPlayer(ArrayList<Deck.Card> cards) {
        StringBuilder stringBuilder = new StringBuilder("\n");

        for (Deck.Card card : cards) {
            stringBuilder.append(String.format("%2d: ", cards.indexOf(card)));
            stringBuilder.append(String.format("%18s\n", card.toString()));
        }

        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}