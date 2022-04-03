package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class InfuseEnergy extends AbstractTimeEaterCard {
    public final static String ID = makeID("InfuseEnergy");
    // intellij stuff attack, enemy, uncommon, 6, 4, , , , 

    public InfuseEnergy() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : SuspendHelper.suspendGroup.group) {
                    if (q.cost > 0) {
                        q.updateCost(-1);
                        q.superFlash();
                    }
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(4);
    }
}