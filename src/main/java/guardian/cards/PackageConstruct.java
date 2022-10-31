package guardian.cards;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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

public class PackageConstruct extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("PackageConstruct");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/packageConstruct.png";
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


    public AbstractGuardianCard constPrev1 = new ModeShift();
    public AbstractGuardianCard constPrev2 = new OmegaCannon();
    public AbstractGuardianCard constPrev3 = new HammerDown();

    public AbstractGuardianCard prev1;
    public AbstractGuardianCard prev2;
    public AbstractGuardianCard prev3;
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public PackageConstruct() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        this.exhaust = true;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

        prev1 = constPrev1;
        prev2 = constPrev2;
        prev3 = constPrev3;

        if (upgraded){
            prev1.upgrade();
            prev2.upgrade();
            prev3.upgrade();
        }
    }

    @Override
    public void hover() {
        prev1 = constPrev1;
        prev2 = constPrev2;
        prev3 = constPrev3;
        super.hover();
    }

    @Override
    public void unhover() {
        super.unhover();
        prev1 = null;
        prev2 = null;
        prev3 = null;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        ArrayList<AbstractCard> derp = new ArrayList();
        AbstractCard tmp;

        tmp = new ModeShift();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
      //  tmp.modifyCostForCombat(-1);

        tmp = new OmegaCannon();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
      //  tmp.modifyCostForCombat(-1);

        tmp = new HammerDown();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
      //  tmp.modifyCostForCombat(-1);


        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard)derp.get(0), true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard) derp.get(1), true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard) derp.get(2), true));
    }

    public AbstractCard makeCopy() {
        return new PackageConstruct();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;

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

    @Override
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);

        if (!flipPreviewMode) {
            //Removes the preview when the player is manipulating the card or if the card is locked
            if (!hb.hovered || isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {
                return;
            }

            float drawScale = 0.5f;
            float yPosition1 = Settings.HEIGHT * 0.2F;

            float xPosition1 = Settings.WIDTH * 0.35F;
            float xPosition2 = Settings.WIDTH * 0.5F;
            float xPosition3 = Settings.WIDTH * 0.65F;

            if (prev1 != null) {
                AbstractCard card = prev1.makeStatEquivalentCopy();
                if (card != null) {
                    card.drawScale = drawScale;
                    card.current_x = xPosition1;
                    card.current_y = yPosition1;
                    card.render(sb);
                }
            }
            if (prev2 != null) {
                AbstractCard card = prev2.makeStatEquivalentCopy();
                if (card != null) {
                    card.drawScale = drawScale;
                    card.current_x = xPosition2;
                    card.current_y = yPosition1;
                    card.render(sb);
                }
            }
            if (prev3 != null) {
                AbstractCard card = prev3.makeStatEquivalentCopy();
                if (card != null) {
                    card.drawScale = drawScale;
                    card.current_x = xPosition3;
                    card.current_y = yPosition1;
                    card.render(sb);
                }
            }
        } else {
            if (isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {
                return;
            }
            if (hb.hovered){

                float drawScale = 0.5f;
                float yPosition1 = this.current_y + this.hb.height * 1.2f;
                float yPosition2 = this.current_y + this.hb.height * 0.7f;
                float yPosition3 = this.current_y + this.hb.height * 0.2f;

                float xPosition1;
                float xOffset1 = -this.hb.width * 0.75f;

                //inverts the x position if the card is a certain amount to the right to prevent clipping issues
                if (this.current_x > Settings.WIDTH * 0.75F) {
                    xOffset1 = -xOffset1;
                }

                xPosition1 = this.current_x + xOffset1;

                if (prev1 != null) {
                    AbstractCard card = prev1.makeStatEquivalentCopy();
                    if (card != null) {
                        card.drawScale = drawScale;
                        card.current_x = xPosition1;
                        card.current_y = yPosition1;
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
    }
}


