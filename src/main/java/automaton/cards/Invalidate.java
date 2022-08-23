package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.EasyModalChoiceAction;
import automaton.cards.encodedcards.EncodedBlind;
import automaton.cards.encodedcards.EncodedTrip;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class Invalidate extends AbstractBronzeCard {

    public final static String ID = makeID("Invalidate");

    public Invalidate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new EasyModalChoiceCard(makeID("InvalidateWeak"), name, cardStrings.EXTENDED_DESCRIPTION[0] + magicNumber + cardStrings.EXTENDED_DESCRIPTION[1] + LocalizedStrings.PERIOD, () -> {
            applyToEnemy(m, new WeakPower(m, magicNumber, false));
            AbstractCard q = new EncodedTrip();
            if (upgraded) q.upgrade();
            addCardToFunction(q);
        }));
        easyCardList.add(new EasyModalChoiceCard(makeID("InvalidateVuln"), name, cardStrings.EXTENDED_DESCRIPTION[0] + magicNumber + cardStrings.EXTENDED_DESCRIPTION[2] + LocalizedStrings.PERIOD, () -> {
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
            AbstractCard q = new EncodedBlind();
            if (upgraded) q.upgrade();
            addCardToFunction(q);
        }));
        atb(new EasyModalChoiceAction(easyCardList));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}