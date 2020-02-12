package slimebound.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import slimebound.characters.SlimeboundCharacter;
import slimebound.relics.ScrapOozeRelic;
import slimebound.ui.ScrapBonfireOption;

import java.util.ArrayList;

@SpirePatch(clz = AbstractPlayer.class, method = "increaseMaxOrbSlots",
        paramtypez = {
                int.class,
                boolean.class})
public class MaxOrbsPatch {
    public static void Prefix(AbstractPlayer obj) {

        if (obj instanceof SlimeboundCharacter && obj.maxOrbs == 5) {
            AbstractDungeon.effectList.add(new ThoughtBubble(obj.dialogX, obj.dialogY, 3.0F, obj.MSG[3], true));
        }
    }
}



