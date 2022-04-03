package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;
import static timeeater.util.Wiz.att;

public class Reverberate extends AbstractTimeEaterCard {
    public final static String ID = makeID("Reverberate");
    // intellij stuff attack, enemy, rare, 20, 6, , , , 

    public Reverberate() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 20;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractCard q = SuspendHelper.suspendGroup.getRandomCard(true).makeStatEquivalentCopy();
                q.updateCost(-99);
                att(new MakeTempCardInHandAction(q, true));
            }
        });
    }

    public void upp() {
        upgradeDamage(6);
    }
}