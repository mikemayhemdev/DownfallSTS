package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedBlur;
import automaton.cards.encodedcards.EncodedTwinStrike;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import downfall.powers.FadedPower;

public class Shell extends AbstractBronzeCard {

    public final static String ID = makeID("Shell");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 5;

    public Shell() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(AutomatonMod.ENCODES);
        cardsToPreview = new EncodedBlur();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FadedPower(p, magicNumber));

        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }


    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        cardsToPreview.upgrade();
    }
}