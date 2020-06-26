/*
package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
public class ConstructModeAttackPreventionPatch {
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    @SpirePrefixPatch
    public static SpireReturn<Boolean> Prefix(AbstractCard obj) {

        if (AbstractDungeon.player.hasPower("Guardian:ConstructModePower") && obj.type == AbstractCard.CardType.ATTACK) {
            obj.cantUseMessage = TEXT[4];
            return SpireReturn.Return(false);
        } else {

            return SpireReturn.Continue();
        }

    }
}


*/


