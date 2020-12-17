package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Constructor extends AbstractBronzeCard {

    public final static String ID = makeID("Constructor");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    public Constructor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        thisEncodes();
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = baseBlock;
        if (inFunc) {
            if (firstCard()) {
                baseBlock *= 2;
            }
        }
        super.applyPowers();
        baseBlock = realBaseDamage;
        this.isDamageModified = block != baseBlock;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = baseBlock;
        if (inFunc) {
            if (firstCard()) {
                baseBlock *= 2;
            }
        }
        super.calculateCardDamage(mo);
        baseBlock = realBaseDamage;
        this.isDamageModified =block != baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}