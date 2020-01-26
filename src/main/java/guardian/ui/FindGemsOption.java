package guardian.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.relics.PickAxe;
import guardian.vfx.CampfireFindGemsEffect;
import guardian.vfx.SocketGemEffect;


public class FindGemsOption extends AbstractCampfireOption
{
    private static final UIStrings UI_STRINGS;


    public static final String[] DESCRIPTIONS;


    //private ArrayList<String> idleMessages;
    public FindGemsOption(boolean active) {
        this.label =  DESCRIPTIONS[0];
        this.description = DESCRIPTIONS[1];

        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/minecampfire.png"));
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/minecampfiredisabled.png"));
        }
    }

    @Override
    public void useOption() {

            AbstractDungeon.effectList.add(new CampfireFindGemsEffect());
            AbstractDungeon.player.getRelic(PickAxe.ID).onTrigger();

    }




    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("Guardian:FindGemsOption");
        DESCRIPTIONS = UI_STRINGS.TEXT;

    }
}
