package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.TurnBasedSlowPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class HeadSlam extends AbstractTimeEaterCard {
    public final static String ID = makeID("HeadSlam");
    // intellij stuff attack, enemy, common, 7, 3, , , , 

    public HeadSlam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        applyToEnemy(m, new TurnBasedSlowPower(m, 1));
    }

    public void upp() {
        upgradeDamage(3);
    }
}