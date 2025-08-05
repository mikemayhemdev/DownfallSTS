package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class MiniBlackHole extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("MiniBlackHole");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("MiniBlackHole.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("MiniBlackHole.png"));
    public boolean firstTurn = false;

    public MiniBlackHole() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    //Alethea

    @Override
    public void atPreBattle() {
        firstTurn = true;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (!firstTurn) {
            if (!this.grayscale) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            }
        }
        this.grayscale = false;
        this.firstTurn = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            if (!this.grayscale) {
                this.flash();
                this.grayscale = true;
            }
        }
    }

    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
