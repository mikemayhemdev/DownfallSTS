package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class HammerBlow extends AbstractTimeEaterCard {
    public final static String ID = makeID("HammerBlow");
    // intellij stuff attack, enemy, common, 14, 4, , , , 

    public HammerBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 14;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : SuspendHelper.suspendGroup.group) {
                    q.upgrade();
                    q.superFlash();
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(4);
    }
}