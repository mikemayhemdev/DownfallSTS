package awakenedOne.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.HexCurse;

public class HexMasterPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = HexMasterPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public HexMasterPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (usedCard.type == AbstractCard.CardType.POWER) {
            this.flash();
            Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var2.next();
                if (!mo.isDead) {
                    if (!mo.hasPower(UltimateHexDebuff.POWER_ID)) {
                        HexCurse(amount, mo, AbstractDungeon.player);
                    }
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
