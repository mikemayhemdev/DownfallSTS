package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.BurnAction;
import theHexaghost.powers.BurnPower;

public class InvigoratingFlames extends AbstractHexaCard {

    public final static String ID = makeID("InvigoratingFlames");

    //stupid intellij stuff SKILL, SELF_AND_ENEMY, UNCOMMON

    public InvigoratingFlames() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (m.hasPower(BurnPower.POWER_ID)) {
                    addToTop(new GainBlockAction(p, m.getPower(BurnPower.POWER_ID).amount));
                    addToTop(new RemoveSpecificPowerAction(m, p, BurnPower.POWER_ID));
                } else {
                    addToTop(new BurnAction(m, 5));
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}