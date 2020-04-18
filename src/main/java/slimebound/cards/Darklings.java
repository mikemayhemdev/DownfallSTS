package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import evilWithin.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;
import slimebound.actions.OctoChoiceAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class Darklings extends AbstractSlimeboundCard {
    public static String ID = "Slimebound:Darklings";
    public static String NAME;
    public static String DESCRIPTION;
    public static String IMG_PATH = "cards/darkling.png";
    public static CardStrings cardStrings;
    public static CardType TYPE = CardType.SKILL;
    public static CardRarity RARITY = CardRarity.RARE;
    public static CardTarget TARGET = CardTarget.SELF;
    public static int COST = 1;
    public static String UPGRADED_DESCRIPTION;


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Darklings() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 3; i++) {
            addToBot(new SlimeSpawnAction(new DarklingSlime(), false, true));

        }
    }



    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}



