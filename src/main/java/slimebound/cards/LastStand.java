package slimebound.cards;



import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import slimebound.SlimeboundMod;


public class LastStand extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:LastStand";

    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/laststand.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;

    private static int upgradedamount = 1;

    public LastStand() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;

        this.exhaust = true;
        this.poison = this.magicNumber +2;

        tags.add(SlimeboundMod.STUDY_CHAMP);
        tags.add(SlimeboundMod.STUDY);
    }
/*
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        double currentPct = p.currentHealth * 1.001 / p.maxHealth * 1.001;
        if (currentPct > 0.5) {

            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return true;
        }

    }
    */


    public void use(AbstractPlayer p, AbstractMonster m) {



        if (upgraded) this.poison = this.magicNumber +3; else {this.poison = this.magicNumber +2;}
        AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.LOW));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new InflameEffect(p), 0.15F));
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
        double currentPct = p.currentHealth * 1.001 / p.maxHealth * 1.001;
        if (currentPct > 0.5) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        } else{
            AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, "~DIE~ ~.~ ~.~ ~.~", true));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.poison), this.poison));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new InflameEffect(p), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new InflameEffect(p), 0.15F));
        }

    }


    public AbstractCard makeCopy() {
        return new LastStand();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.poison = 4;


        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}

