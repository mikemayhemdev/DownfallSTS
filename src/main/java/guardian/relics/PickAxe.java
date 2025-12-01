package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;
import guardian.GuardianMod;
import guardian.rewards.GemReward;
import guardian.rewards.GemRewardButRelicRng;

public class PickAxe extends CustomRelic {
    public static final String ID = "Guardian:PickAxe";
    public static final String IMG_PATH = "relics/pick.png";
    public static final String OUTLINE_IMG_PATH = "relics/pickOutline.png";
    private static final int HP_PER_CARD = 1;

    public PickAxe() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.CLINK);
//        this.counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    public void onEquip() {
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger) {
            AbstractDungeon.getCurrRoom().rewards.add(new GemRewardButRelicRng());
            //            AbstractDungeon.combatRewardScreen.open();
            //            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
        }
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        this.counter = -2;
        this.grayscale = true;
        setCounter(this.counter);
    }

    public void setCounter(int counter) {
      //  this.counter = -2;
    //    this.img = TextureLoader.getTexture(GuardianMod.getResourcePath("relics/pickUsed.png"));
        //this.usedUp();
    }

    public void onVictory(){
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger) {
            if (this.counter != -2) {
                this.flash();
                AbstractDungeon.getCurrRoom().rewards.add(new GemReward());
            }
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48; // cannot appear in act 4
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PickAxe();
    }
}