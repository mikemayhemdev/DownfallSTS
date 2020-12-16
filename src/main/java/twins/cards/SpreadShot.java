package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpreadShot extends AbstractTwinsCard {

    public final static String ID = makeID("SpreadShot");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public SpreadShot() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(DonuDecaMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}