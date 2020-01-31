package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.GuardianMod;


public class ModeShift extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ModeShift");
    public static final String NAME;
    public static final String IMG_PATH = "cards/ModeShift.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 4;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 2;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public ModeShift() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

//this.sockets.add(GuardianMod.socketTypes.RED);


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        final int str = p.hasPower(StrengthPower.POWER_ID) ? p.getPower(StrengthPower.POWER_ID).amount : 0;
        final int dex = p.hasPower(DexterityPower.POWER_ID) ? p.getPower(DexterityPower.POWER_ID).amount : 0;
        if (dex - str != 0)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, dex - str), dex - str));
        if (str - dex != 0)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, str - dex), str - dex));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    public AbstractCard makeCopy() {
        return new ModeShift();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
            this.retain = true;
        }


    }
}


