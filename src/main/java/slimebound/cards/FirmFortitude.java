package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.slimes.SlimeHelper;
import sneckomod.SneckoMod;


public class FirmFortitude extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:FirmFortitude";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/liquidate.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public FirmFortitude() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        //baseMagicNumber = magicNumber = 0;
        SlimeboundMod.loadJokeCardImage(this, "FirmFortitude.png");
        this.tags.add(SneckoMod.BANNEDFORSNECKO);

        exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int total = SlimeHelper.GetCidEnergy() + SlimeHelper.GetPikeEnergy();

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, total), total));

    }

 //   @Override
  //  public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    //    if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
     //       if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0) {
     //           if (!this.hasTag(expansionContentMod.UNPLAYABLE))
      //              this.tags.add(expansionContentMod.UNPLAYABLE);
    //            return false;
   //         }
   //     }
   //     this.tags.remove(expansionContentMod.UNPLAYABLE);
  //      return super.canUse(p, m);
  //  }

    public AbstractCard makeCopy() {
        return new FirmFortitude();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();

        }
    }
}


