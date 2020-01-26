package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import slimebound.SlimeboundMod;
import slimebound.powers.SlimeRitualPower;


public class Icky extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Icky";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/icky.png";

    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;

    private static final int COST = -2;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;

    public Icky() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 1;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        } else {
            AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
        }
    }

    public void triggerWhenDrawn() {

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Slimed(), 1));
    }


    public AbstractCard makeCopy() {
        return new Icky();
    }

    public void upgrade() {
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


