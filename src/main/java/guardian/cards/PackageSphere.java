package guardian.cards;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class PackageSphere extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("PackageSphere");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/packageSpheric.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;

    //TUNING CONSTANTS
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS


    public AbstractGuardianCard constPrev1 = new SphericShield();
    public AbstractGuardianCard constPrev2 = new FloatingOrbs();
    public AbstractGuardianCard constPrev3 = new Harden();

    public AbstractGuardianCard prev1;
    public AbstractGuardianCard prev2;
    public AbstractGuardianCard prev3;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public PackageSphere() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        this.exhaust = true;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        ArrayList derp = new ArrayList();
        AbstractCard tmp;

        tmp = new SphericShield();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
        tmp.modifyCostForCombat(-1);

        tmp = new FloatingOrbs();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
        tmp.modifyCostForCombat(-1);

        tmp = new Harden();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
        tmp.modifyCostForCombat(-1);

        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard) derp.get(0), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard) derp.get(1), (float) Settings.WIDTH * .75F, (float) Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard) derp.get(2), (float) Settings.WIDTH * .25F, (float) Settings.HEIGHT / 2.0F));

    }

    public AbstractCard makeCopy() {
        return new PackageSphere();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.rawDescription = UPGRADED_DESCRIPTION;

            this.initializeDescription();
            upgradeName();
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

    @Override
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);

        //Removes the preview when the player is manipulating the card or if the card is locked
        if (isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {
            return;
        }

        float drawScale = 0.5f;
        float yPosition1 = this.current_y + this.hb.height * 0.75f;
        float yPosition2 = this.current_y + this.hb.height * 0.25f;
        float yPosition3 = this.current_y - this.hb.height * 0.25f;

        //changes the Arcana preview to render below the Arcana in the shop so it doesn't clip out of the screen
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
            yPosition1 = this.current_y - this.hb.height * 0.75f;
            yPosition2 = this.current_y - this.hb.height * 0.25f;
            yPosition3 = this.current_y + this.hb.height * 0.25f;
        }

        float xPosition1;
        float xPosition2;
        float xPosition3;
        float xOffset1 = -this.hb.width * 0.75f;
        float xOffset2 = -this.hb.width * 0.25f;
        float xOffset3 = this.hb.width * 0.25f;

        //inverts the x position if the card is a certain amount to the right to prevent clipping issues
        if (this.current_x > Settings.WIDTH * 0.75F) {
            xOffset1 = -xOffset1;
            xOffset2 = -xOffset2;
            xOffset3 = -xOffset3;
        }

        xPosition1 = this.current_x + xOffset1;
        xPosition2 = this.current_x + xOffset2;
        xPosition3 = this.current_x + xOffset3;

        if (prev1 != null) {
            AbstractCard card = prev1.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition1;
                card.current_y = yPosition3;
                card.render(sb);
            }
        }
        if (prev2 != null) {
            AbstractCard card = prev2.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition1;
                card.current_y = yPosition2;
                card.render(sb);
            }
        }
        if (prev3 != null) {
            AbstractCard card = prev3.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition1;
                card.current_y = yPosition3;
                card.render(sb);
            }
        }
    }
}


