package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class HeatMetal extends AbstractHexaCard {
    public final static String ID = makeID("HeatMetal");

    public HeatMetal() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        baseBurn = burn = 4;
        baseMagicNumber = magicNumber = 1;
        HexaMod.loadJokeCardImage(this, "HeatMetal.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        burn(m, burn);
        applyToEnemy(m, autoVuln(m, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeBurn(1);
            upgradeMagicNumber(1);
        }
    }
}