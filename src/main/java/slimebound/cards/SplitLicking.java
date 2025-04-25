package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandCidAction;
import slimebound.actions.CommandPikeAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeProjectileEffect;


public class SplitLicking extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SplitMire";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/splitsludging.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitLicking() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 2;
        //this.slimed = this.baseSlimed = 8;
        //this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "SplitMire.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        //TODO - Pike applies Goop equal to damage dealt. Buff power to track count?

        addToBot(new CommandPikeAction());

    }

    public AbstractCard makeCopy() {
        return new SplitLicking();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);


        }
    }
}

