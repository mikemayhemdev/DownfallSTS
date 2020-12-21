package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FineTuning extends AbstractBronzeCard {

    public final static String ID = makeID("FineTuning");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 1;

    public FineTuning() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard q : FunctionHelper.held.group) {
            q.baseDamage += 1;
            q.damage += 1;
            q.baseBlock += 1;
            q.block += 1;
            q.baseMagicNumber += 1;
            q.magicNumber += 1;
            if (q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).baseAuto += 1;
                ((AbstractBronzeCard) q).auto += 1;
            }
            q.applyPowers();
            q.superFlash();
            FunctionHelper.applyPowers();
        }
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}