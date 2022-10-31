package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimeSacrificePower;
import sneckomod.SneckoMod;


public class ProtectTheBoss extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ProtectTheBoss";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slimesacrifice.png";
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ProtectTheBoss() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        //this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;


     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // Random random = new Random();
        //Integer chosenRand = random.nextInt(4);

/*
        if (chosenRand == 0) {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitBruiser(), false, true));
        } else if (chosenRand == 1) {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitLeeching(), false, true));
        } else if (chosenRand == 2) {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitAcid(), false, true));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitLicking(), false, true));
        }

        if (upgraded){
            if (chosenRand == 0) {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitBruiser(), false, true));
            } else if (chosenRand == 1) {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitLeeching(), false, true));
            } else if (chosenRand == 2) {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitAcid(), false, true));
            } else {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SplitLicking(), false, true));
            }
        }
*/
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SlimeSacrificePower(p, this.magicNumber), this.magicNumber, true));


    }

    public AbstractCard makeCopy() {
        return new ProtectTheBoss();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            // this.exhaust = false;
            upgradeBaseCost(0);


        }
    }
}

