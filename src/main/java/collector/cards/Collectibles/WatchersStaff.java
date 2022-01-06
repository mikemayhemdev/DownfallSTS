package collector.cards.Collectibles;

import collector.powers.FalteringFocus;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class WatchersStaff extends AbstractCollectibleCard {
    public final static String ID = makeID("WatchersStaff");

    public WatchersStaff() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF,CollectorCardSource.FRONT);
       magicNumber = baseMagicNumber = 1;
        this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new ChooseWrath());
        stanceChoices.add(new ChooseCalm());
        this.addToBot(new ChooseOneAction(stanceChoices));
        atb(new GainEnergyAction(magicNumber));
        applyToFront(new FalteringFocus(p));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
