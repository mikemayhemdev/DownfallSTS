package twins.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

@CardIgnore
public class LockOn extends AbstractTwinsCard {

    public final static String ID = makeID("LockOn");

    //stupid intellij stuff skill, enemy, common

    public LockOn() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, autoVuln(q, 1));
            }
        }
        else {
            applyToEnemy(m, autoVuln(m, 1));
        }
        fireBlaster(1);
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}