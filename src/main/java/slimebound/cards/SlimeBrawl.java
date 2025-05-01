package slimebound.cards;


import collector.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import slimebound.slimes.SlimeHelper;
import sneckomod.SneckoMod;

import java.util.ArrayList;


public class SlimeBrawl extends AbstractSlimeboundCard implements OctopusCard {
    public static final String ID = "Slimebound:SlimeBrawl";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/alltogether.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    }


    public SlimeBrawl() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "SlimeBrawl.png");
        this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String imagePath = "slimeboundResources/images/cards/alltogether.png";
        if (SlimeHelper.doesPikeHaveEnchantment(SlimeHelper.enchantment.TIMEEATER)) {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantPikeTimeEater", EXTENDED_DESCRIPTION[0], imagePath, EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[7], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantPikeTimeEater", EXTENDED_DESCRIPTION[0], imagePath, EXTENDED_DESCRIPTION[1], -1, -1, CardType.SKILL));
        }

        if (SlimeHelper.doesPikeHaveEnchantment(SlimeHelper.enchantment.GUARDOIAN)) {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantPikeGuardian", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[7], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantPikeGuardian", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[3], -1, -1, CardType.SKILL));
        }

        if (SlimeHelper.doesPikeHaveEnchantment(SlimeHelper.enchantment.HEXAGHOST)) {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantPikeHexaghost", EXTENDED_DESCRIPTION[4], imagePath, EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[7], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:EnchantPikeHexaghost", EXTENDED_DESCRIPTION[4], imagePath, EXTENDED_DESCRIPTION[5], -1, -1, CardType.SKILL));
        }
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:EnchantPikeTimeEater": {
                //TODO - Add enchantment "attacks apply 1 weak"
                break;
            }
            case "Slimebound:EnchantPikeGuardian": {
                //TODO - Add enchantment "attacks grant 4 temp thorns."

                break;
            }
            case "Slimebound:EnchantPikeHexaghost": {
                //TODO - Add enchantment "attacks grant you 1 strength."

                break;
            }
        }
    }

    public AbstractCard makeCopy() {

        return new SlimeBrawl();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeBaseCost(1);

        }

    }
}


