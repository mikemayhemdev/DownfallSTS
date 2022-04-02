package timeeater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class MirrorImage extends AbstractTimeEaterCard {
    public final static String ID = makeID("MirrorImage");
    // intellij stuff skill, self, common, , , 4, 2, , 

    public MirrorImage() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SelectCardsAction(p.discardPile.group, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard q = cards.get(0);
            p.discardPile.removeCard(q);
            SuspendHelper.suspend(q);
        }));
    }

    public void upp() {
        upgradeBlock(2);
        exhaust = false;
        uDesc();
    }
}