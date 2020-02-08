package guardian.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.vfx.SocketGemEffect;


public class EnhanceBonfireOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final UIStrings UI_STRINGS;

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("Guardian:EnhanceBonfireOptions");
        DESCRIPTIONS = UI_STRINGS.TEXT;

    }

    //private ArrayList<String> idleMessages;
    public EnhanceBonfireOption(boolean active) {
        this.label = DESCRIPTIONS[0];

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            c.update();
        }

        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfire.png"));
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfiredisabled.png"));
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            GuardianMod.currSocketGemEffect = new SocketGemEffect();
            AbstractDungeon.effectList.add(GuardianMod.currSocketGemEffect);

        }
    }

    public void reCheck() {
        if (GuardianMod.getSocketableCards().size() == 0) {
            this.usable = false;
        }
        if (GuardianMod.getGemCards().size() == 0) {
            this.usable = false;
        }
        if (this.usable) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfire.png"));
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfiredisabled.png"));
        }

    }
}
