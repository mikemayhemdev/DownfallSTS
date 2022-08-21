package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedMiracle;
import automaton.cards.goodstatus.Daze;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class BuggyMess extends AbstractBronzeCard {

    public final static String ID = makeID("BuggyMess");

    //stupid intellij stuff skill, self, uncommon

    public BuggyMess() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new EncodedMiracle();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(new Dazed());
        atb(new GainEnergyAction(magicNumber));
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}