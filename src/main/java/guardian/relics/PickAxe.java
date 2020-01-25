package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;

public class PickAxe extends CustomRelic {
    public static final String ID = "Guardian:PickAxe";
    public static final String IMG_PATH = "relics/pick.png";
    public static final String OUTLINE_IMG_PATH = "relics/pickOutline.png";
    private static final int HP_PER_CARD = 1;

    public PickAxe() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);

        this.counter = 3;
    }

    @Override
    public void onEquip() {
        super.onEquip();
    }

    @Override
    public String getUpdatedDescription() {

        return this.DESCRIPTIONS[0];

    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        this.counter--;
        setCounter(this.counter);



    }

    public void setCounter(int counter) {
        this.counter = counter;
        if (counter == 0) {
            this.counter = -2;
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("relics/pickUsed.png"));
            this.usedUp();
        } else {

        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new PickAxe();
    }
}