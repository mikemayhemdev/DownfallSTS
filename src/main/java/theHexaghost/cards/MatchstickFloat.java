package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.RetractAction;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import java.util.ArrayList;
@NoCompendium
public class MatchstickFloat extends AbstractHexaCard implements OctopusCard {

//This card exists for the sake of tracking what Sneaky Teakwood Match does. It shouldn't be encountered in a run. If a player reported this, that means there's a bug.

    public final static String ID = makeID("MatchstickFloat");

    public MatchstickFloat() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "Float.png");
    }

    public ArrayList<OctoChoiceCard> choiceList() {

        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoRetractM", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoNothingM", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[2]));
        cardList.add(new OctoChoiceCard("octo:OctoAdvanceM", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[0]));

        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoAdvanceM":
                atb(new AdvanceAction(false));
                System.out.println("DEBUG: Used MatchstickCase to Advance.");
                break;
            case "octo:OctoRetractM":
                atb(new RetractAction());
                System.out.println("DEBUG: Used MatchstickCase to Retract.");
                break;
            case "octo:OctoNothingM":
                System.out.println("DEBUG: Did nothing with MatchstickCase.");
                break;
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AnimateHopAction(p));
        atb(new DrawCardAction(1));
        if (upgraded) {
            atb(new OctoChoiceAction(m, this));
        } else {
            atb(new AdvanceAction(false));
        }
    }

    public void upgrade() {
        if (!upgraded) {
          //  upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}