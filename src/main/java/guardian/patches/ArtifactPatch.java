/*
package guardian.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import guardian.GuardianMod;
import guardian.cards.AncientConstruct;
import guardian.powers.ConstructPower;

@SpirePatch(clz= ArtifactPower.class,method = "onSpecificTrigger")
public class ArtifactPatch {

    @SpirePrefixPatch
    public static void Prefix(ArtifactPower obj) {

        if (obj.owner == AbstractDungeon.player) {
            if (AbstractDungeon.player.hasPower(ConstructPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(obj.owner, obj.owner, new StrengthPower(obj.owner, obj.amount), obj.amount));

            }
        }

    }


}
*/



