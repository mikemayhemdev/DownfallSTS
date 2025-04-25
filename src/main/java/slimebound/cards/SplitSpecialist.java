package slimebound.cards;


import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;


import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Collections;


public class SplitSpecialist extends AbstractSlimeboundCard {
    public static String ID = "Slimebound:SplitSpecialist";
    public static String NAME;
    public static String DESCRIPTION;
    public static String IMG_PATH = "cards/splitspecialist.png";
    public static CardStrings cardStrings;
    public static CardType TYPE = CardType.SKILL;
    public static CardRarity RARITY = CardRarity.UNCOMMON;
    public static CardTarget TARGET = CardTarget.SELF;
    public static int COST = 2;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitSpecialist() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        exhaust = true;


/*
        cardList.add(new OctoChoiceCard("Slimebound:SplotBronze",
                NAMES[12],
                expansionContentMod.makeCardPath("QuickAutomaton.png"),
                TEXT[12] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:bronze_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotGhostflame",
                NAMES[13],
                expansionContentMod.makeCardPath("QuickHexa.png"),
                TEXT[13] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:ghostflame_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotTorchhead",
                NAMES[14],
                expansionContentMod.makeCardPath("QuickCollector.png"),
                TEXT[14] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:torchhead_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotCultist",
                NAMES[15],
                expansionContentMod.makeCardPath("QuickAwakened.png"),
                TEXT[15] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:cultist_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotProtector",
                NAMES[16],
                expansionContentMod.makeCardPath("QuickGuardian.png"),
                TEXT[16] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:protector_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotInsulting",
                NAMES[17],
                expansionContentMod.makeCardPath("QuickChamp.png"),
                TEXT[17] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:insulting_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotAncient",
                NAMES[18],
                expansionContentMod.makeCardPath("QuickAncients.png"),
                TEXT[18] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:ancient_slime").replaceAll("#b","").replaceAll("#y","")));
        cardList.add(new OctoChoiceCard("Slimebound:SplotSlowing",
                NAMES[19],
                expansionContentMod.makeCardPath("QuickTimeEater.png"),
                TEXT[19] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:time_slime").replaceAll("#b","").replaceAll("#y","")));
        SlimeboundMod.loadJokeCardImage(this, "SplitSpecialist.png");

 */

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        //TODO - pick one of three screen
    }

    /*
    public void choice(AbstractMonster m) {
        addToBot(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> realList = new ArrayList<>();
        Collections.shuffle(cardList, AbstractDungeon.cardRng.random);
        for (int i = 0; i < 3; i++) {
            realList.add(cardList.get(i));
        }
        return realList;

    }

     */

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        /*
        switch (card.cardID) {
            case "Slimebound:SplotBronze": {
                addToBot(new SlimeSpawnAction(new BronzeSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotGhostflame": {
                addToBot(new SlimeSpawnAction(new HexSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotTorchhead": {
                addToBot(new SlimeSpawnAction(new TorchHeadSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotCultist": {
                addToBot(new SlimeSpawnAction(new CultistSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotProtector": {
                addToBot(new SlimeSpawnAction(new ProtectorSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotInsulting": {
                addToBot(new SlimeSpawnAction(new ChampSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotAncient": {
                addToBot(new SlimeSpawnAction(new DrawingSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
            case "Slimebound:SplotSlowing": {
                addToBot(new SlimeSpawnAction(new SlowingSlime(), false, true));
                if (upgraded) addToBot(new CommandAction());
                break;
            }
        }

        if (upgraded) {
            checkMinionMaster();
        }

         */
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        cardsToPreview = null;
    }
}

