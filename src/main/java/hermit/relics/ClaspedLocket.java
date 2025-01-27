package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;


public class ClaspedLocket extends CustomRelic {
    public static final String ID = HermitMod.makeID("ClaspedLocket");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("clasped_locket.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("clasped_locket.png"));

    private boolean cardsReceived = true;
    private boolean canTrigger = true;

    public ClaspedLocket() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Memento.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Memento.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else { super.obtain(); }

        this.cardsReceived = false;
    }

    public void onCardDraw(AbstractCard card) {

        if (!this.canTrigger)
                return;

        if (card.type == AbstractCard.CardType.CURSE) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.canTrigger = false;
            // visual feedback so the player can tell if it triggered that turn
            stopPulse();
            this.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
            this.addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
        }

    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Memento.ID);
    }

    public void atTurnStart() {
        this.canTrigger = true;
        beginLongPulse(); //visual feedback
    }

    @Override
    public void onVictory() {
        stopPulse();
    }


    public void update() {
        super.update();

        if (!cardsReceived && !AbstractDungeon.isScreenUp && isObtained) {
            cardsReceived = true;
            CardCrawlGame.sound.play("NECRONOMICON");
            CardGroup curseaddgroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int aaa = 0; aaa < 2; aaa++) {
                AbstractCard InjuryCard = new Injury();
                curseaddgroup.addToBottom(InjuryCard.makeCopy());
            }
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(curseaddgroup, this.DESCRIPTIONS[1]);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
