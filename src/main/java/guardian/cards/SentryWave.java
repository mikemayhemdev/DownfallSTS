package guardian.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import guardian.GuardianMod;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.patches.AbstractCardEnum;

public class SentryWave extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SentryWave");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/sentryWave.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 0;

    private boolean noHover = false;

    //TUNING CONSTANTS
    private static final int DEBUFFCOUNT = 1;
    private static final int UPGRADE_DEBUFF = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;



    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SentryWave(boolean noHover) {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = DEBUFFCOUNT;
        this.exhaust = true;
        this.socketCount = SOCKETS;
        this.noHover = noHover;
        if (!this.noHover) this.cardsToPreview = new SentryBeam(true);
        updateDescription();
        loadGemMisc();
    }

    public SentryWave(){
        this(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));

        if (upgraded) brace(2);

        if (AbstractDungeon.player.hasEmptyOrb()) {

            AbstractGuardianCard newCard = new SentryBeam();
            if (this.upgraded) newCard.upgrade();
            UnlockTracker.markCardAsSeen(SentryWave.ID);

            AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(newCard));
        }

        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new SentryWave();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            //upgradeMagicNumber(UPGRADE_DEBUFF);
            this.rawDescription = UPGRADED_DESCRIPTION;
            if (!this.noHover){
                AbstractCard q = new SentryBeam(true);
                q.upgrade();
                cardsToPreview = q;
            }
            this.initializeDescription();
        }
    }

    public void updateDescription() {
        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }
}


