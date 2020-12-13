package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.LivingBombPower;

public class SoulSteal extends AbstractHexaCard {

    public final static String ID = makeID("SoulSteal");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON


    public SoulSteal() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = 5;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        applyToEnemy(m, new LivingBombPower(m, magicNumber));

    }

    public void upgrade() {
        if (!upgraded) {
            exhaust = false;
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}