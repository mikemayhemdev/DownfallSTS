package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Blockblast extends AbstractTwinsCard {

    public final static String ID = makeID("Blockblast");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public Blockblast() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(DonuDecaMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        fireShields(1);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}