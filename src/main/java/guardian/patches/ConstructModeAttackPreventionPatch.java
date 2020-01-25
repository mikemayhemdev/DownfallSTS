package guardian.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import guardian.GuardianMod;

@SpirePatch(clz= AbstractCard.class,method = "hasEnoughEnergy")
public class ConstructModeAttackPreventionPatch {
    public static final String[] TEXT;

    @SpirePrefixPatch
    public static SpireReturn<Boolean> Prefix(AbstractCard obj) {

        if (AbstractDungeon.player.hasPower("Guardian:ConstructModePower") && obj.type == AbstractCard.CardType.ATTACK) {
            obj.cantUseMessage = TEXT[4];
            return SpireReturn.Return(false);
        } else {

            return SpireReturn.Continue();
        }

    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }
}




