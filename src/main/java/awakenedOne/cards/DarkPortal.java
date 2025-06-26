package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class DarkPortal extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(DarkPortal.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public static final String[] TEXT;

    public DarkPortal() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(DarkPortal.class.getSimpleName() + ".png"));
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

                        selecteda.setCostForTurn(0);
                    }
            ));
        }

        addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 1));

    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}