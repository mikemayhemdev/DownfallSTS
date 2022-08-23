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
        if (upgraded) {
            easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsDraw"), name, cardStrings.EXTENDED_DESCRIPTION[3], () -> atb(new DrawCardAction(4))));
            easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsEnergy"), name, cardStrings.EXTENDED_DESCRIPTION[5], () -> atb(new GainEnergyAction(3))));
            easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsHeal"), name, cardStrings.EXTENDED_DESCRIPTION[1], () -> applyToSelf(new RepairPower(p, 10))));
        } else {
            easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsDraw"), name, cardStrings.EXTENDED_DESCRIPTION[2], () -> atb(new DrawCardAction(3))));
            easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsEnergy"), name, cardStrings.EXTENDED_DESCRIPTION[4], () -> atb(new GainEnergyAction(2))));
            easyCardList.add(new EasyModalChoiceCard(makeID("DevToolsHeal"), name, cardStrings.EXTENDED_DESCRIPTION[0], () -> applyToSelf(new RepairPower(p, 8))));

        }
        atb(new EasyModalChoiceAction(easyCardList));
    }

    public void upp() {
    }
}