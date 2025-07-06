package champ.cards;

import champ.powers.DoubleStyleThisTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class ShieldSigil extends AbstractChampCard {

    public final static String ID = makeID("ShieldSigil");


    public ShieldSigil() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseDamage = 3;
        loadJokeCardImage(this, "ShieldSigil.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        applyToSelf(new DoubleStyleThisTurnPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeDamage(2);
    }
}