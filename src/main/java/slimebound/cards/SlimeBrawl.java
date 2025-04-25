package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import sneckomod.SneckoMod;


public class SlimeBrawl extends AbstractSlimeboundCard {
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

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public SlimeBrawl() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "SlimeBrawl.png");
        this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        //TODO - pick one of three screen
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


