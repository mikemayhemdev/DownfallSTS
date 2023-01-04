package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class CursedClamor extends AbstractAwakenedCard {
    public final static String ID = makeID(CursedClamor.class.getSimpleName());
    // intellij stuff attack, enemy, common, 20, 6, , , , 

    public CursedClamor() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 20;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (EnergyPanel.totalCount > 0) {
                    att(new GainEnergyAction(2));
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(6);
    }
}