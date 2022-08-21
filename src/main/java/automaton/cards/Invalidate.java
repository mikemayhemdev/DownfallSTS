package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedBlind;
import automaton.cards.encodedcards.EncodedTrip;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.RetractAction;

import java.util.ArrayList;

public class Invalidate extends AbstractBronzeCard implements OctopusCard {

    public final static String ID = makeID("Invalidate");

    //stupid intellij stuff skill, self, common


    public Invalidate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;

        tags.add(AutomatonMod.ENCODES);

    }

    public ArrayList<OctoChoiceCard> choiceList() {

        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        if (upgraded) {
            cardList.add(new OctoChoiceCard("octo:OctoInvalidateWeak", this.name, AutomatonMod.makeCardPath("Invalidate.png"), this.EXTENDED_DESCRIPTION[2], new EncodedTrip(), true));
            cardList.add(new OctoChoiceCard("octo:OctoInvalidateVuln", this.name, AutomatonMod.makeCardPath("Invalidate.png"), this.EXTENDED_DESCRIPTION[3], new EncodedBlind(), true));
        }
        if (upgraded) {
            cardList.add(new OctoChoiceCard("octo:OctoInvalidateWeak", this.name, AutomatonMod.makeCardPath("Invalidate.png"), this.EXTENDED_DESCRIPTION[0], new EncodedTrip(), false));
            cardList.add(new OctoChoiceCard("octo:OctoInvalidateVuln", this.name, AutomatonMod.makeCardPath("Invalidate.png"), this.EXTENDED_DESCRIPTION[1], new EncodedBlind(), false));
        }
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoInvalidateWeak": {
                applyToEnemy(m, autoWeak(m, magicNumber));
                addCardToFunction(new EncodedTrip());

            }
                break;
            case "octo:OctoInvalidateVuln": {
                applyToEnemy(m, autoVuln(m, magicNumber));
                addCardToFunction(new EncodedBlind());

            }
                break;

        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}