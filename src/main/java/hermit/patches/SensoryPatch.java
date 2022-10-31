package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.localization.CharacterStrings;

import static hermit.HermitMod.makeID;


public class SensoryPatch {
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(makeID("hermit"));
    private static final String[] TEXT = characterStrings.TEXT;

    @SpirePatch(clz = SensoryStone.class, method = "getRandomMemory")
    public static class DisableHealing
    {
        @SpirePostfixPatch
        public static void Postfix(SensoryStone stone)
        {
            int rando=AbstractDungeon.miscRng.random(4);
            if (rando==1)
            stone.imageEventText.updateBodyText(TEXT[3]);
        }

    }


}
