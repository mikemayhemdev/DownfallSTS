package downfall.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = EmptyOrbSlot.class, method = "updateDescription")
public class EmptyOrbSlotPatch {
    public static Texture NORMAL_ORB = ImageMaster.ORB_SLOT_1;
    public static Texture SLIME_ORB = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/orbs/empty1.png");
    public static final OrbStrings normalOrbString = CardCrawlGame.languagePack.getOrbString("Empty");
    public static final OrbStrings slimeOrbString = CardCrawlGame.languagePack.getOrbString("Slimebound:EmptySlimeSlot");
    public static final OrbStrings guardianOrbString = CardCrawlGame.languagePack.getOrbString("Guardian:EmptyStasisSlot");

    public static void Postfix(EmptyOrbSlot EmptyOrbSlot_instance) {
        OrbStrings orbString;
        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            ImageMaster.ORB_SLOT_1 = SLIME_ORB;
            orbString = slimeOrbString;
        } else {
            ImageMaster.ORB_SLOT_1 = NORMAL_ORB;
            if (AbstractDungeon.player instanceof GuardianCharacter)
                orbString = guardianOrbString;
            else
                orbString = normalOrbString;
        }
        EmptyOrbSlot_instance.name = orbString.NAME;
        EmptyOrbSlot_instance.description = orbString.DESCRIPTION[0];
    }
}


