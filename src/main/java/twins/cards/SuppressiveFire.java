package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SuppressiveFire extends AbstractTwinsCard {

    public final static String ID = makeID("SuppressiveFire");

    //stupid intellij stuff skill, all_enemy, uncommon

    public SuppressiveFire() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        tags.add(DonuDecaMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (inFire || upgraded) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, autoWeak(q, 1));
                applyToEnemy(q, autoVuln(q, 1));
            }
        }
    }

    public void upp() {
        //TODO: think about this upgrade
    }
}