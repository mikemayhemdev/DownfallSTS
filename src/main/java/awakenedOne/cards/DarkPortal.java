package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.SelectCardsCenteredAction;

public class DarkPortal extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(DarkPortal.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;
    public static final String[] TEXT;

    public DarkPortal() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.discardPile.isEmpty()) {
            this.addToBot(new SelectCardsCenteredAction(

                    p.discardPile.group,
                    1,
                    TEXT[0],

                    (selectedCards) -> {

                        AbstractCard selecteda = selectedCards.get(0);
                        p.discardPile.removeCard(selecteda);
                        p.hand.addToHand(selecteda);
                        selecteda.lighten(false);
                        selecteda.unhover();
                        selecteda.applyPowers();

                        selecteda.costForTurn = 0;
                    }
            ));
        }

        if (upgraded)
        addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 1));

        if (!upgraded)
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}