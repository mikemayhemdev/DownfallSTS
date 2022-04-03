package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.EasyModalChoiceAction;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.SlowDownPower;
import timeeater.powers.SpeedUpPower;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Chronomancy extends AbstractTimeEaterCard {
    public final static String ID = makeID("Chronomancy");
    // intellij stuff power, self, uncommon, , , , , , 

    public Chronomancy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        cardToPreview.add(new EasyModalChoiceCard(cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1], () -> applyToSelfTop(new SpeedUpPower(1))));
        cardToPreview.add(new EasyModalChoiceCard(cardStrings.EXTENDED_DESCRIPTION[2], cardStrings.EXTENDED_DESCRIPTION[3], () -> applyToSelfTop(new SlowDownPower(1))));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> q = new ArrayList<>();
        q.add(new EasyModalChoiceCard(cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1], () -> applyToSelfTop(new SpeedUpPower(1))));
        q.add(new EasyModalChoiceCard(cardStrings.EXTENDED_DESCRIPTION[2], cardStrings.EXTENDED_DESCRIPTION[3], () -> applyToSelfTop(new SlowDownPower(1))));
        atb(new EasyModalChoiceAction(q));
    }

    @Override
    protected float getRotationTimeNeeded() {
        return 1.2F;
    }

    public void upp() {
        isInnate = true;
        uDesc();
    }
}