package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ConsumeSoul extends AbstractHexaCard {

    public final static String ID = makeID("ConsumeSoul");

    //stupid intellij stuff ATTACK, ENEMY, RARE

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;

    private static final int UPG_BLOCK = 5;

    private static final int MAGIC = 2;

    public ConsumeSoul() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeBlock(UPG_BLOCK);
        }
    }
}