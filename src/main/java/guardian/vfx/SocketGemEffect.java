package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

import java.util.Iterator;

public class SocketGemEffect extends AbstractGameEffect {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Guardian:EnhanceBonfireOptions");
        TEXT = uiStrings.TEXT;
    }

    public boolean openedScreen = false;
    public boolean gemSelect = false;
    public boolean socketSelect = false;
    public boolean confirmSelect = false;
    public AbstractGuardianCard gemChosen;
    private Color screenColor;

    public SocketGemEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }

        Iterator var1;
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && gemSelect) {
            var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var1.hasNext()) {
                AbstractGuardianCard c = (AbstractGuardianCard) var1.next();
                this.gemChosen = c;

            }
            gemSelect = false;
            socketSelect = true;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            GuardianMod.gridScreenForGems = false;
            GuardianMod.gridScreenForSockets = true;
            AbstractDungeon.gridSelectScreen.open(GuardianMod.getSocketableCards(), 1, TEXT[4], false, false, true, false);


        }

        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && socketSelect) {
            var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var1.hasNext()) {
                AbstractGuardianCard cg = (AbstractGuardianCard) var1.next();
                cg.addGemToSocket(gemChosen);
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(cg.makeStatEquivalentCopy()));
                ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
                GuardianMod.gridScreenForSockets = false;

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();


        }

        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && confirmSelect) {
            var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var1.hasNext()) {
                AbstractGuardianCard cg = (AbstractGuardianCard) var1.next();
                cg.addGemToSocket(gemChosen);
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(cg.makeStatEquivalentCopy()));
                ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
                GuardianMod.gridScreenForSockets = false;

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();


        }

        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup gemCards = GuardianMod.getGemCards();
            this.gemSelect = true;
            GuardianMod.gridScreenForGems = true;
            AbstractDungeon.gridSelectScreen.open(gemCards, 1, TEXT[3], false, false, true, false);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    GuardianMod.socketBonfireOption.reCheck();
                    ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                    // there was a bug with the fire sound persisting and I'm not sure why,
                    // so this is basically a randomly thrown out preventative measure.
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();

                }
            }

        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }

    }

    public void dispose() {
    }
}
