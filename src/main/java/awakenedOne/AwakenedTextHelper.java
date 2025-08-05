package awakenedOne;
import awakenedOne.cards.AbstractAwakenedCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.ACTIVECHANT;

public class AwakenedTextHelper {
    protected static String[] ifStr = CardCrawlGame.languagePack.getUIString("awakened:highlightChant").TEXT;

    public static void colorCombos(AbstractAwakenedCard card, boolean resetColors) {
        if (AbstractDungeon.player != null) {
            if (card.rawDescription.contains(ifStr[0])) {
                if ((card.trig_chant) && !resetColors) {
                    card.rawDescription = card.rawDescription.replace(ifStr[0], ifStr[1]);
                }
            } else if (card.rawDescription.contains(ifStr[1])) {
                if ((!card.trig_chant) || resetColors) {
                    card.rawDescription = card.rawDescription.replace(ifStr[1], ifStr[0]);
                }
            }
        }
        card.initializeDescription();
    }
}
