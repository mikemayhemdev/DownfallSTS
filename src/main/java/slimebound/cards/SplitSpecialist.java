package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class SplitSpecialist extends AbstractSlimeboundCard implements OctopusCard {
    public static String ID = "Slimebound:SplitSpecialist";
    public static String NAME;
    public static String DESCRIPTION;
    public static String IMG_PATH = "cards/splitspecialist.png";
    public static CardStrings cardStrings;
    public static CardType TYPE = CardType.SKILL;
    public static CardRarity RARITY = CardRarity.UNCOMMON;
    public static CardTarget TARGET = CardTarget.SELF;
    public static int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;
    public String[] NAMES = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").NAMES;
    public String[] TEXT = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").TEXT;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitSpecialist() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        choice();
    }

    public void choice() {
        addToBot(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("Slimebound:SplotBronze", NAMES[12], expansionContentMod.makeCardPath("QuickAutomaton.png"), TEXT[12]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotGhostflame", NAMES[13], expansionContentMod.makeCardPath("QuickHexa.png"), TEXT[13]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotTorchhead", NAMES[14], expansionContentMod.makeCardPath("QuickCollector.png"), TEXT[14]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotCultist", NAMES[15], expansionContentMod.makeCardPath("QuickAwakened.png"), TEXT[15]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotProtector", NAMES[16], expansionContentMod.makeCardPath("QuickGuardian.png"), TEXT[16]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotInsulting", NAMES[17], expansionContentMod.makeCardPath("QuickChamp.png"), TEXT[17]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotAncient", NAMES[18], expansionContentMod.makeCardPath("QuickAncients.png"), TEXT[18]));
        cardList.add(new OctoChoiceCard("Slimebound:SplotSlowing", NAMES[19], expansionContentMod.makeCardPath("QuickTimeEater.png"), TEXT[19]));
        ArrayList<OctoChoiceCard> realList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            realList.add(cardList.remove(AbstractDungeon.cardRandomRng.random(cardList.size() - 1)));
        }
        return realList;

    }

    public void doChoiceStuff(OctoChoiceCard card) {
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

        if (upgraded){
            checkMinionMaster();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}

