package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Deprecate extends AbstractBronzeCard {

    public final static String ID = makeID("Deprecate");

    //stupid intellij stuff skill, all_enemy, common

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Deprecate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : monsterList()) {
            applyToEnemy(q, autoWeak(q, magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}