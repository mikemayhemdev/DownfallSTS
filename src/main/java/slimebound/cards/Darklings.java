package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.DarklingSlime;


public class Darklings extends AbstractSlimeboundCard {
    public static String ID = "Slimebound:Darklings";
    public static String NAME;
    public static String DESCRIPTION;
    public static String IMG_PATH = "cards/darkling.png";
    public static CardStrings cardStrings;
    public static CardType TYPE = CardType.POWER;
    public static CardRarity RARITY = CardRarity.SPECIAL;
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
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        SlimeboundMod.loadJokeCardImage(this, "Darklings.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
      //TODO new power *Pike and *Cid's attacks increase the other's damage by 1 for the rest of combat."
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
        }
    }
}



