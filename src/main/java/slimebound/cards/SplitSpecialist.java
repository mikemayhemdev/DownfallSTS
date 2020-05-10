package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;
import slimebound.actions.OctoChoiceAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class SplitSpecialist extends AbstractSlimeboundCard {
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
        cardList.add(new OctoChoiceCard("Slimebound:SplotBronze", "Split: Bronze", expansionContentMod.makeCardPath("QuickAutomaton.png"), "Split into a Bronze_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotGhostflame", "Split: Ghostflame", expansionContentMod.makeCardPath("QuickHexa.png"), "Split into a Ghostflame_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotTorchhead", "Split: Torchhead", expansionContentMod.makeCardPath("QuickCollector.png"), "Split into a Torchhead_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotCultist", "Split: Cultist", expansionContentMod.makeCardPath("QuickAwakened.png"), "Split into a Cultist_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotProtector", "Split: Protector", expansionContentMod.makeCardPath("QuickGuardian.png"), "Split into a Protector_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotInsulting", "Split: Insulting", expansionContentMod.makeCardPath("QuickChamp.png"), "Split into an Insulting_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotAncient", "Split: Ancient", expansionContentMod.makeCardPath("QuickAncients.png"), "Split into an Ancient_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotSlowing", "Split: Time", expansionContentMod.makeCardPath("QuickTimeEater.png"), "Split into a Time_Slime."));
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
                break;
            }
            case "Slimebound:SplotGhostflame": {
                addToBot(new SlimeSpawnAction(new HexSlime(), false, true));
                break;
            }
            case "Slimebound:SplotTorchhead": {
                addToBot(new SlimeSpawnAction(new TorchHeadSlime(), false, true));
                break;
            }
            case "Slimebound:SplotCultist": {
                addToBot(new SlimeSpawnAction(new CultistSlime(), false, true));
                break;
            }
            case "Slimebound:SplotProtector": {
                addToBot(new SlimeSpawnAction(new ProtectorSlime(), false, true));
                break;
            }
            case "Slimebound:SplotInsulting": {
                addToBot(new SlimeSpawnAction(new ChampSlime(), false, true));
                break;
            }
            case "Slimebound:SplotAncient": {
                addToBot(new SlimeSpawnAction(new DrawingSlime(), false, true));
                break;
            }
            case "Slimebound:SplotSlowing": {
                addToBot(new SlimeSpawnAction(new SlowingSlime(), false, true));
                break;
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}

