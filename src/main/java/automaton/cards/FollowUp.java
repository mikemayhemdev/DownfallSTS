package automaton.cards;

import automaton.FunctionHelper;
import automaton.actions.RepeatCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FollowUp extends AbstractBronzeCard {

    public final static String ID = makeID("FollowUp");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;

    public FollowUp() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        blck();
        if (AbstractDungeon.actionManager.lastCard instanceof FunctionCard) {
            atb(new RepeatCardAction(this));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}