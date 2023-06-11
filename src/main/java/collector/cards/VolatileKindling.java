package collector.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class VolatileKindling extends AbstractCollectorCard {
    public final static String ID = makeID(VolatileKindling.class.getSimpleName());
    // intellij stuff skill, none, uncommon, , , , , 5, 2

    public VolatileKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 5;
        //TODO: Annoying card to preview eventually
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
        makeInHand(q.makeStatEquivalentCopy()); //TODO: Confirm this actually works; if not check against other implementations more carefully
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}