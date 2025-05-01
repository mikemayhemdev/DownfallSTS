package slimebound.cards;


import collector.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;


import slimebound.actions.CommandCidAction;
import slimebound.actions.CommandPikeAction;
import slimebound.actions.EnergyToCidAction;
import slimebound.actions.EnergyToPikeAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.slimes.SlimeHelper;

import java.util.ArrayList;


public class Split extends AbstractSlimeboundCard implements OctopusCard {
    public static final String ID = "Slimebound:Split";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/split.png");
    private static final CardStrings cardStrings;
    public static final String[] EXTENDED_DESCRIPTION;

    public Split() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.BASIC, CardTarget.NONE);
        SlimeboundMod.loadJokeCardImage(this, "Split.png");
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String imagePath = "slimeboundResources/images/cards/alltogether.png";

        cardList.add(new OctoChoiceCard("Slimebound:SplitPike", EXTENDED_DESCRIPTION[0], imagePath, EXTENDED_DESCRIPTION[1], -1, -1, magicNumber, CardType.SKILL));
        cardList.add(new OctoChoiceCard("Slimebound:SplitCid", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[3], -1, -1, magicNumber, CardType.SKILL));
        //TODO - Given this is a starter card, maybe we do the MTG method of spell out what Command is going to do exactly on the text.

        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:SplitPike": {
                addToBot(new EnergyToPikeAction(magicNumber));
                addToBot(new CommandPikeAction());
                break;
            }
            case "Slimebound:SplitCid": {
                addToBot(new EnergyToCidAction(magicNumber));
                addToBot(new CommandCidAction());

                break;
            }
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}

