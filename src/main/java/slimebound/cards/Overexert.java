package slimebound.cards;


import collector.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;

import slimebound.patches.AbstractCardEnum;
import slimebound.powers.LoseSlimesPower;
import slimebound.powers.PotencyPower;
import slimebound.slimes.SlimeHelper;
import sneckomod.SneckoMod;

import java.util.ArrayList;


public class Overexert extends AbstractSlimeboundCard implements OctopusCard {
    public static final String ID = "Slimebound:Overexert";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/knowledgepool.png";
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public Overexert() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 2;
        SlimeboundMod.loadJokeCardImage(this, "Overexert.png");

        //this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String imagePath = "slimeboundResources/images/cards/knowledgepool.png";
        if (SlimeHelper.doesCidHaveEnchantment(SlimeHelper.enchantment.NOENEERGYCOST)) {
            cardList.add(new OctoChoiceCard("Slimebound:MinionMasterCid", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[5], -1, -1, CardType.SKILL));
        } else {
            cardList.add(new OctoChoiceCard("Slimebound:MinionMasterCid", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[0], -1, -1, CardType.SKILL));
        }

        if (SlimeHelper.doesPikeHaveEnchantment(SlimeHelper.enchantment.NOENEERGYCOST)) {
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
        return new Overexert();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.initializeDescription();

        }
    }
}

