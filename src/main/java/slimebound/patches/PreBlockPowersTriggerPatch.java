package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import slimebound.powers.FirmFortitudePower;

import java.util.Iterator;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class PreBlockPowersTriggerPatch {

    public static void Prefix(AbstractPlayer obj, DamageInfo info) {

        int damageAmount = info.output;
        Iterator var4;
        AbstractPower p;
        if (info.owner != null) {
            var4 = AbstractDungeon.player.powers.iterator();

            while (var4.hasNext()) {
                p = (AbstractPower) var4.next();
                if (p instanceof FirmFortitudePower)
                    damageAmount = ((FirmFortitudePower) p).onAttackedPreBlock(info, damageAmount);
            }
        }
        if (damageAmount == 0) {
            AbstractDungeon.effectList.add(new StrikeEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, damageAmount));

        }
        info.output = damageAmount;
    }
}
