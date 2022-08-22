package automaton.cards;

import automaton.actions.EasyModalChoiceAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;

import java.util.ArrayList;

public class TinkerersToolbox extends AbstractBronzeCard {

    public final static String ID = makeID("TinkerersToolbox");

    public TinkerersToolbox() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true; // Leaving it without upgrades due to iffy upgrade preview (not enough variables)
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsDraw"), cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1], () -> atb(new DrawCardAction(3))));
        easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsEnergy"), cardStrings.EXTENDED_DESCRIPTION[2], cardStrings.EXTENDED_DESCRIPTION[3], () -> atb(new GainEnergyAction(2))));
        easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsHeal"), cardStrings.EXTENDED_DESCRIPTION[4], cardStrings.EXTENDED_DESCRIPTION[5], () -> applyToSelf(new RepairPower(p, 10))));
        atb(new EasyModalChoiceAction(easyCardList));
    }

    public void upp() {
    }
}