package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemyTop;
import static collector.util.Wiz.atb;

public class Goodbye extends AbstractCollectorCard {
    public final static String ID = makeID(Goodbye.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , 2, 1

    public Goodbye() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (m.hasPower(DoomPower.POWER_ID)) {
                    int count = m.getPower(DoomPower.POWER_ID).amount;
                    applyToEnemyTop(m, new DoomPower(m, count * magicNumber));
                }
            }
        });
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}