package collector.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class BramblesparKindling extends AbstractCollectorCard {
    public final static String ID = makeID(BramblesparKindling.class.getSimpleName());
    // intellij stuff skill, none, uncommon, , , , , 5, 2

    public BramblesparKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 5;
        AbstractCard q = new Strike();
        q.name = q.name + "+" + magicNumber;
        q.upgraded = true;
        q.baseDamage += 3 * magicNumber;
        cardsToPreview = q;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractCard q = new Strike();
        q.name = q.name + "+" + magicNumber;
        q.upgraded = true;
        q.baseDamage += 3 * magicNumber;
        makeInHand(q.makeStatEquivalentCopy()); // It works!
    }

    public void upp() {
        upgradeMagicNumber(2);
        AbstractCard q = new Strike();
        q.name = q.name + "+" + magicNumber;
        q.upgraded = true;
        q.baseDamage += 3 * magicNumber;
        cardsToPreview = q;
    }
}