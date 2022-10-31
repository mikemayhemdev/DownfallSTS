package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

public class ExtraCursedKey extends CustomRelic {

    public static final String ID = downfallMod.makeID("ExtraCursedKey");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/ExtraCursedKey.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/ExtraCursedKey.png"));

    public ExtraCursedKey() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void justEnteredRoom(AbstractRoom room) {
        if (room instanceof TreasureRoom) {
            this.flash();
            this.pulse = true;
        } else {
            this.pulse = false;
        }

    }

    public void onChestOpen(boolean bossChest) {
        if (!bossChest) {
            for (int i = 0; i < 2; i++) {
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.returnRandomCurse(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }
}
