package slimebound.cards;


import collector.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;


import slimebound.patches.AbstractCardEnum;
import slimebound.slimes.AbstractSlime;
import slimebound.slimes.SlimeHelper;

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
    public static int COST = 2;
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

    public SplitSpecialist() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String imagePath = "slimeboundResources/images/cards/splitspecialist.png";
        if (SlimeHelper.DoesPikeHaveEnchantment(AbstractSlime.SlimeEnchantmentType.CHAMP)) {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantCidChamp", EXTENDED_DESCRIPTION[0], imagePath, EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[7], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantCidChamp", EXTENDED_DESCRIPTION[0], imagePath, EXTENDED_DESCRIPTION[1], -1, -1, CardType.SKILL));
        }

        if (SlimeHelper.DoesPikeHaveEnchantment(AbstractSlime.SlimeEnchantmentType.CULTIST)) {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantCidCultist", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[7], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantCidCultist", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[3], -1, -1, CardType.SKILL));
        }

        if (SlimeHelper.DoesPikeHaveEnchantment(AbstractSlime.SlimeEnchantmentType.TORCHHEAD)) {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantCidTorchhead", EXTENDED_DESCRIPTION[4], imagePath, EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[7], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantCidTorchhead", EXTENDED_DESCRIPTION[4], imagePath, EXTENDED_DESCRIPTION[5], -1, -1, CardType.SKILL));
        }
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:EnchantCidChamp": {
                //TODO - Add enchantment "attacks apply 1 vulnerable."
                break;
            }
            case "Slimebound:EnchantPikeGuardian": {
                //TODO - Add enchantment "attacks increase damage by 1."

                break;
            }
            case "Slimebound:EnchantPikeHexaghost": {
                //TODO - Add enchantment "attacks benefit from your strength."

                break;
            }
        }
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

