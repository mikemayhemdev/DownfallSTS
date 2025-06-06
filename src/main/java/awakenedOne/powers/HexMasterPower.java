package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashSet;
import java.util.Set;

import static awakenedOne.AwakenedOneMod.HexCurse;

public class HexMasterPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = HexMasterPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public HexMasterPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    private Set<AbstractCreature> alreadyHit;

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        this.alreadyHit = new HashSet<>(); // New card. Reset limits.
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target.equals(AbstractDungeon.player)){
            return;
        }
        if(alreadyHit.contains(target)){
            return;
        }
        if(info.type == DamageInfo.DamageType.NORMAL) {
            //alreadyHit.add(target);
            if (target.hasPower(EnemyHexedPower.POWER_ID)) {
                HexCurse(amount, target, AbstractDungeon.player);
            }
        }
    }


    //@Override
    //    public void onAfterCardPlayed(AbstractCard usedCard) {
    //        if (usedCard.type == AbstractCard.CardType.POWER) {
    //            this.flash();
    //            Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
    //
    //            while (var2.hasNext()) {
    //                AbstractMonster mo = (AbstractMonster) var2.next();
    //                if (!mo.isDead) {
    //                    if (!mo.hasPower(UltimateHexDebuff.POWER_ID)) {
    //                        HexCurse(amount, mo, AbstractDungeon.player);
    //                    }
    //                }
    //            }
    //        }
    //    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
