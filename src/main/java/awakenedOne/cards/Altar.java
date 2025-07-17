package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.actions.HandSelectAction;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Altar extends AbstractAwakenedCard {
    public final static String ID = makeID(Altar.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public Altar() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 6;
        this.tags.add(AwakenedOneMod.DELVE);
        selfRetain = true;
        loadJokeCardImage(this, makeBetaCardPath(Altar.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new HandSelectAction(1, (c) -> true, list -> {
            for (AbstractCard c : list)
            {
                Wiz.p().hand.moveToExhaustPile(c);
                if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
                    atb(new ConjureAction(false));
                }

            }
            list.clear();
        }, null, uiStrings.TEXT[0],false,false,false));
    }

    public void upp() {
        upgradeBlock(3);
    }
}