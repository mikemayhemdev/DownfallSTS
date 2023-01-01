package awakenedOne.ui;

import awakenedOne.powers.AwakenedPower;
import awakenedOne.relics.OnAwakenRelic;
import awakenedOne.util.ImageHelper;
import awakenedOne.util.TexLoader;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static awakenedOne.util.Wiz.applyToSelf;

public class AwakenButton extends ClickableUIElement {

    private static final Texture tex = TexLoader.getTexture("awakenedResources/images/ui/BecomeAwesomeButton.png");
    private static final Texture tex_hovered = TexLoader.getTexture("awakenedResources/images/ui/BecomeAwesomeButton_hovered.png");

    public AwakenButton() {
        super(tex, 750, 750, 150 * Settings.scale, 150 * Settings.scale);
    }

    @Override
    public void update() {
        super.update();
        if (hitbox.hovered) {
            ImageHelper.tipBoxAtMousePos("#yAwaken", "Click this to spend all your [E] and gain that much Awakened."); //TODO: Loc
        }
    }

    @Override
    protected void onHover() {
        if (canBeClicked())
            this.image = tex_hovered;
    }

    @Override
    protected void onUnhover() {
        this.image = tex;
    }

    @Override
    protected void onClick() {
        if (canBeClicked() && EnergyPanel.totalCount > 0) {
            int amount = EnergyPanel.totalCount;
            AbstractDungeon.player.energy.use(amount);
            applyToSelf(new AwakenedPower(amount));
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof OnAwakenRelic) {
                    ((OnAwakenRelic) r).onAwaken(amount);
                }
            }
        }
    }

    private boolean canBeClicked() {
        return !AbstractDungeon.isScreenUp && AbstractDungeon.actionManager.actions.isEmpty() && AbstractDungeon.actionManager.currentAction == null;
    }

}
