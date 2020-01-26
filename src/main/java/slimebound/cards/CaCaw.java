package slimebound.cards;



import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import slimebound.SlimeboundMod;
import slimebound.powers.SlimeRitualPower;


public class CaCaw extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:CaCaw";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/ritual.png";

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;

    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;

    public CaCaw() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        tags.add(SlimeboundMod.STUDY_AWAKENEDONE);
        tags.add(SlimeboundMod.STUDY);



        this.magicNumber = this.baseMagicNumber = 1;



    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("VO_CULTIST_1A", -.3f);

        AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, "Caw... Caw...", true));

        if (upgraded)         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SlimeRitualPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new CaCaw();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            //upgradeMagicNumber(1);

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


