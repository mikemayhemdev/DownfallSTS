package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;
import slimebound.actions.OctoChoiceAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.BronzeSlime;
import slimebound.orbs.CultistSlime;
import slimebound.orbs.HexSlime;
import slimebound.orbs.TorchHeadSlime;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class SplitSpecialist extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SplitSpecialist";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/zzzcorrosivespit.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("Slimebound:SplotBronze", "Split: Bronze", expansionContentMod.makeCardPath("QuickAutomaton.png"), "Split into a Bronze_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotGhostflame", "Split: Ghostflame", expansionContentMod.makeCardPath("QuickHexa.png"), "Split into a Ghostflame_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotTorchhead", "Split: Torchhead", expansionContentMod.makeCardPath("QuickCollector.png"), "Split into a Torchhead_Slime."));
        cardList.add(new OctoChoiceCard("Slimebound:SplotCultist", "Split: Cultist", expansionContentMod.makeCardPath("QuickAwakened.png"), "Split into a Cultist_Slime."));
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
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}

