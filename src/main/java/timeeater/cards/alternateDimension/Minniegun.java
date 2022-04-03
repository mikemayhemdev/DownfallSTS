package timeeater.cards.alternateDimension;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;

public class Minniegun extends AbstractDimensionalCard {
    public final static String ID = makeID("Minniegun");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Minniegun() {
        super(ID, 3, CardType.ATTACK, CardTarget.ALL_ENEMY);
        baseDamage = 2;
        setFrame("minniegunframe.png");
        exhaust = true;

    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (int i = 0; i < 12; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    public void upp() {
        upgradeDamage(1);
    }
}