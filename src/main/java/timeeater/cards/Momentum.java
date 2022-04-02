package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.HastePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Momentum extends AbstractTimeEaterCard {
    public final static String ID = makeID("Momentum");
    // intellij stuff attack, enemy, common, 12, , , , 1, 1

    public Momentum() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        applyToSelf(new HastePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}