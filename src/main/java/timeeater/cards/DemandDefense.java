package timeeater.cards;

import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class DemandDefense extends AbstractTimeEaterCard {
    public final static String ID = makeID("DemandDefense");
    // intellij stuff skill, , uncommon, , , 12, 3, , 

    public DemandDefense() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBlock = 12;
        cardsToPreview = new Shame();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SuspendAction(new Shame()));
    }

    public void upp() {
        upgradeBlock(3);
    }
}