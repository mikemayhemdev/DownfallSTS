package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldSlam extends AbstractChampCard {

    public final static String ID = makeID("ShieldSlam");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;

    public ShieldSlam() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentBlock >= 20) {
            return super.canUse(p, m);
        }
        cantUseMessage = "I do not have enough Block.";
        return false;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}