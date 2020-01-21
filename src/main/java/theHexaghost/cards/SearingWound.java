package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.BurnPower;

public class SearingWound extends AbstractHexaCard {

    public final static String ID = makeID("SearingWound");

    //stupid intellij stuff ATTACK, ALL_ENEMY, UNCOMMON

    public SearingWound() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDying && q.hasPower(BurnPower.POWER_ID)) {
                atb(new LoseHPAction(q, p, q.getPower(BurnPower.POWER_ID).amount));
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}