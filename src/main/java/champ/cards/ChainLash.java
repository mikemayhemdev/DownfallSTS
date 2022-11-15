package champ.cards;

import champ.ChampMod;
import champ.powers.DoubleStyleThisTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChainLash extends AbstractChampCard {

    public final static String ID = makeID("ChainLash");


    public ChainLash() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 3;
        tags.add(ChampMod.COMBO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        combo();
    }

    public void upp() {
        upgradeDamage(2);
    }
}