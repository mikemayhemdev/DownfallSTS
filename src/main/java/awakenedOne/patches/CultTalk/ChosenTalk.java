package awakenedOne.patches.CultTalk;

import awakenedOne.AwakenedOneChar;
import awakenedOne.AwakenedOneMod;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.city.Chosen;


@SpirePatch(clz = Chosen.class, method = "takeTurn")
public class ChosenTalk {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("awakened:CultTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(Chosen sb) {
        if (AbstractDungeon.player instanceof AwakenedOneChar && !AwakenedOneMod.ChosenTalked) {
            //random DOESN'T MATTER if it's not affecting battle, i.e visual effects
            //if anything, if this DID affect rng it would be the most horrifying meta knowledge ever
            int temp = MathUtils.random(1, 9);
            if (temp < 4) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[6], 1.0F, 2.0F));
            } else if (temp < 6) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[7], 1.0F, 2.0F));
            } else if (temp > 6) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[8], 1.0F, 2.0F));

            }
            AwakenedOneMod.ChosenTalked = true;
        }
    }
}