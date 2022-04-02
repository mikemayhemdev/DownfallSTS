package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.applyToEnemy;
import static timeeater.util.Wiz.atb;

public class Feeblemind extends AbstractTimeEaterCard {
    public final static String ID = makeID("Feeblemind");
    // intellij stuff attack, enemy, common, 9, 1, , , 1, 1

    public Feeblemind() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new Feeble();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        atb(new SuspendAction(new Feeble()));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}