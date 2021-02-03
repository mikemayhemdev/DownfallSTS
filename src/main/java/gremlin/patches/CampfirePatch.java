package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gremlin.characters.GremlinCharacter;
import gremlin.ui.campfire.ResurrectOption;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.CampfireUI", method = "initializeButtons")
public class CampfirePatch
{
    public static void Postfix(final Object meObj) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if(((GremlinCharacter)AbstractDungeon.player).canRez()) {
                final CampfireUI campfire = (CampfireUI) meObj;
                try {
                    final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>) ReflectionHacks.getPrivate((Object) campfire, (Class) CampfireUI.class, "buttons");
                    campfireButtons.add(new ResurrectOption());
                    float x = 950.0f;
                    final float y = 990.0f - 270.0f * ((campfireButtons.size() + 1) / 2);
                    if (campfireButtons.size() % 2 == 0) {
                        x = 1110.0f;
                        campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
                    }
                    campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
                } catch (SecurityException ex) {
                } catch (IllegalArgumentException ex2) {
                }
            }
        }
    }
}