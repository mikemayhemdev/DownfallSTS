package charbosses.cards.anticards;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.CharBossWatcher;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import expansioncontent.cards.AbstractExpansionCard;

public class PeaceOut extends AbstractExpansionCard {
    public final static String ID = makeID("PeaceOut");

    public PeaceOut() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractCharBoss.boss instanceof CharBossWatcher) {
            atb(new EnemyChangeStanceAction("Neutral"));
        }
        atb(new RemoveSpecificPowerAction(m, p, MantraPower.POWER_ID));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}


