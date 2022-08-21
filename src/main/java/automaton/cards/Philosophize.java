package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedDeadlyPoison;
import automaton.cards.encodedcards.EncodedInflame;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Philosophize extends AbstractBronzeCard {

    public final static String ID = makeID("Philosophize");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Philosophize() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        cardsToPreview = new EncodedInflame();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, auto));
        for (AbstractMonster q : monsterList()) {
            applyToEnemy(q, new StrengthPower(q, magicNumber));
        }
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());

    }


    public void upp() {
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}