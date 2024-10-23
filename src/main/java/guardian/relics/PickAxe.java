package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.ui.FindGemsOption;

import java.util.ArrayList;

public class PickAxe extends CustomRelic {
    public static final String ID = "Guardian:PickAxe";
    public static final String IMG_PATH = "relics/pick.png";
    public static final String OUTLINE_IMG_PATH = "relics/pickOutline.png";

    public PickAxe() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);

//        this.counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new FindGemsOption(true));
    }

    //    @Override
//    public void onTrigger() {
//        super.onTrigger();
////        this.counter--;
//        setCounter(this.counter);
//    }

//    public void setCounter(int counter) {
//        this.counter = counter;
//        if (counter == 0) {
//            this.counter = -2;
//            this.img = TextureLoader.getTexture(GuardianMod.getResourcePath("relics/pickUsed.png"));
//            this.usedUp();
//        } else {
//
//        }
//    }

    @Override
    public AbstractRelic makeCopy() {
        return new PickAxe();
    }
}