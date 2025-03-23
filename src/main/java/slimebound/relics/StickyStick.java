package slimebound.relics;

import automaton.cards.goodstatus.IntoTheVoid;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.EvolvePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;

public class StickyStick extends CustomRelic {
    public static final String ID = "Slimebound:StickyStick";
    public static final String IMG_PATH = "relics/StickyStick.png";
    public static final String IMG_PATH_LARGE = "relics/StickyStickLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/StickyStickOutline.png";
    private static final int HP_PER_CARD = 1;
    //Gelatinous Cube
    public StickyStick() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.SOLID);
        this.largeImg = TextureLoader.getTexture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onCardDraw(AbstractCard card) {
        if (this.counter == 2) {
            this.grayscale = true;
            return;
        }

        if (this.counter < 3) {
            if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
                this.flash();
                ++this.counter;
                //this.addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                if (card.cardID.equals(VoidCard.ID) || card.cardID.equals(IntoTheVoid.ID)) {
                    this.flash();
                    this.addToTop(new GainEnergyAction(1));
                }
            }
        }
    }

    public void atTurnStart() {
        this.counter = 0;
        this.grayscale = false;
    }

    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StickyStick();
    }

}