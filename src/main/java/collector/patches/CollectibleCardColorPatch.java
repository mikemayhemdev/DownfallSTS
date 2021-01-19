package collector.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;

public class CollectibleCardColorPatch {

    public static class CardColorPatch {
        @SpireEnum
        public static CardColor COLLECTIBLE;
    }

    public static class LibColorPatch {
        @SpireEnum
        public static LibraryType COLLECTIBLE;
    }
}

