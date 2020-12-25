package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DelayedSlice extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedSlice");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 5;

    public DelayedSlice() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        thisEncodes();
        tags.add(AutomatonMod.NO_TEXT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay, int count) {
        super.onCompile(function, forGameplay, count);
        if (forGameplay) {
            allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}