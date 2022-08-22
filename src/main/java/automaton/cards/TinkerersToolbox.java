package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedBlind;
import automaton.cards.encodedcards.EncodedTrip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import downfall.util.SelectCardsCenteredAction;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class TinkerersToolbox extends AbstractBronzeCard implements OctopusCard {

    public final static String ID = makeID("TinkerersToolbox");

    public TinkerersToolbox() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public ArrayList<OctoChoiceCard> choiceList() {

        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        if (upgraded) {
            cardList.add(new OctoChoiceCard("octo:TinkererDraw", this.name, AutomatonMod.makeCardPath("TinkerersToolbox.png"), this.EXTENDED_DESCRIPTION[3]));
            cardList.add(new OctoChoiceCard("octo:TinkererE", this.name, AutomatonMod.makeCardPath("TinkerersToolbox.png"), this.EXTENDED_DESCRIPTION[5]));
            cardList.add(new OctoChoiceCard("octo:TinkererHP", this.name, AutomatonMod.makeCardPath("TinkerersToolbox.png"), this.EXTENDED_DESCRIPTION[1]));
        }
        if (upgraded) {
            cardList.add(new OctoChoiceCard("octo:TinkererDraw", this.name, AutomatonMod.makeCardPath("TinkerersToolbox.png"), this.EXTENDED_DESCRIPTION[2]));
            cardList.add(new OctoChoiceCard("octo:TinkererE", this.name, AutomatonMod.makeCardPath("TinkerersToolbox.png"), this.EXTENDED_DESCRIPTION[4]));
            cardList.add(new OctoChoiceCard("octo:TinkererHP", this.name, AutomatonMod.makeCardPath("TinkerersToolbox.png"), this.EXTENDED_DESCRIPTION[0]));
        }
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:TinkererHP": {
                if (upgraded){
                    applyToSelf(new RepairPower(AbstractDungeon.player, 10));
                } else {
                    applyToSelf(new RepairPower(AbstractDungeon.player, 8));
                }

            }
            break;
            case "octo:TinkererDraw": {
                if (upgraded){
                    atb(new DrawCardAction(4));
                } else {
                    atb(new DrawCardAction(3));
                }
            }
            break;
            case "octo:TinkererE": {
                if (upgraded){
                    atb(new GainEnergyAction(3));
                } else {
                    atb(new GainEnergyAction(2));
                }
            }
            break;

        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }



}