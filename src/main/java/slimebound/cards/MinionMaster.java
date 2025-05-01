package slimebound.cards;


import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;
import slimebound.actions.BuffCidStrengthAction;
import slimebound.actions.BuffPikeStrengthAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.BuffSecondarySlimeEffectsPower;
import slimebound.slimes.SlimeHelper;
import sneckomod.SneckoMod;

import java.util.ArrayList;


public class MinionMaster extends AbstractSlimeboundCard implements OctopusCard {
    public static final String ID = "Slimebound:MinionMaster";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/minionmaster.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static int upgradedamount = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public MinionMaster() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.magicNumber = this.baseMagicNumber = 1;

     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
        SlimeboundMod.loadJokeCardImage(this, "MinionMaster.png");

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String imagePath = "slimeboundResources/images/cards/minionmaster.png";
        if (SlimeHelper.doesCidHaveEnchantment(SlimeHelper.enchantment.DOUBLECOMMAND)) {
            cardList.add(new OctoChoiceCard("Slimebound:MinionMasterCid", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[5], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:MinionMasterCid", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[0], -1, -1, CardType.SKILL));
        }

        if (SlimeHelper.doesPikeHaveEnchantment(SlimeHelper.enchantment.DOUBLECOMMAND)) {
            cardList.add(new OctoChoiceCard("Slimebound:MinionMasterPike", EXTENDED_DESCRIPTION[3], imagePath, EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[5], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:MinionMasterPike", EXTENDED_DESCRIPTION[3], imagePath, EXTENDED_DESCRIPTION[0], -1, -1, CardType.SKILL));
        }
         return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:MassRepurposeCid": {
                //TODO - Add enchantment "when commanded, performs an additional action."
                break;
            }
            case "Slimebound:MassRepurposePike": {
                //TODO - Add enchantment "when commanded, performs an additional action."

                break;
            }
        }
    }


    public AbstractCard makeCopy() {
        return new MinionMaster();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);


        }
    }
}

