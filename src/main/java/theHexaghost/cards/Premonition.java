package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import theHexaghost.actions.*;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theHexaghost.HexaMod;

import java.util.ArrayList;

public class Premonition extends AbstractHexaCard implements OctopusCard {

    public final static String ID = makeID("Premonition");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup"); // vanilla card type name

    public Premonition() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        HexaMod.loadJokeCardImage(this, "Premonition.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
//        atb(new PremonitionAction(upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoCard1", " ", HexaMod.makeCardPath("Premonition.png"), uiStrings.TEXT[0], CardColor.COLORLESS,CardType.ATTACK));
        cardList.add(new OctoChoiceCard("octo:OctoCard2", " ", HexaMod.makeCardPath("Premonition.png"), uiStrings.TEXT[1], CardColor.COLORLESS,CardType.SKILL));
        cardList.add(new OctoChoiceCard("octo:OctoCard3", " ", HexaMod.makeCardPath("Premonition.png"), uiStrings.TEXT[2], CardColor.COLORLESS,CardType.POWER));
        cardList.add(new OctoChoiceCard("octo:OctoCard4", " ", HexaMod.makeCardPath("Premonition.png"), uiStrings.TEXT[7], CardColor.COLORLESS,CardType.STATUS));
        cardList.add(new OctoChoiceCard("octo:OctoCard5", " ", HexaMod.makeCardPath("Premonition.png"), uiStrings.TEXT[3], CardColor.COLORLESS,CardType.CURSE));
        return cardList;
    }

    @Override
    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard c) {
        atb(new PredictAndPlayCardAction(c));
    }
}