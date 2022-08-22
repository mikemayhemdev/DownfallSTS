package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Boost extends AbstractBronzeCard {

    public final static String ID = makeID("Boost");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 6;
    private static final int MAGIC = 2;
    private static final int DAMAGE = 7;

    public Boost() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        for (AbstractCard q : FunctionHelper.held.group) {
            if (q instanceof AbstractBronzeCard) {
                for (int i = 0; i < magicNumber; i++) {
                    ((AbstractBronzeCard) q).fineTune(true, true);
                }
            }
        }
    }


    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}