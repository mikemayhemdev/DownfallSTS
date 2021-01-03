package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DelayedSlice extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedSlice");

    //stupid intellij stuff attack, all_enemy, common

    public DelayedSlice() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        thisEncodes();
        baseMagicNumber = magicNumber = 6;
        //tags.add(AutomatonMod.NO_TEXT);
        baseDamage = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            for (AbstractMonster q : monsterList()) {
                if (!q.isDeadOrEscaped())
                addToBot(new LoseHPAction(q, q, magicNumber, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(4);
    }
}