package slimebound.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = EmptyOrbSlot.class, method = "updateDescription"
)
public class EmptyOrbSlotGraphicsPatch {
    public static Texture NORMAL_ORB = ImageMaster.ORB_SLOT_1;
    public static Texture SLIME_ORB = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/orbs/empty1.png");

    public static void Postfix(EmptyOrbSlot EmptyOrbSlot_instance) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            ImageMaster.ORB_SLOT_1 = SLIME_ORB;
        } else {
            ImageMaster.ORB_SLOT_1 = NORMAL_ORB;
        }
    }
}

