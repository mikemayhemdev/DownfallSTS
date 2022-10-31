package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;
import guardian.actions.BraceAction;
import guardian.powers.DefensiveModeBooster;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.powers.ModeShiftPower;

public class ModeShifter extends CustomRelic{
    public static final String ID = "Guardian:ModeShifter";
    public static final String IMG_PATH = "relics/modeShifter.png";
    public static final String OUTLINE_IMG_PATH = "relics/modeShifterOutline.png";
    private static final int HP_PER_CARD = 1;

    public ModeShifter() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(GuardianMod.getRewardGemCards(true, 1).get(0).makeStatEquivalentCopy(), (Settings.WIDTH * 0.5F), (Settings.HEIGHT * 0.5F)));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ModeShifter();
    }
}