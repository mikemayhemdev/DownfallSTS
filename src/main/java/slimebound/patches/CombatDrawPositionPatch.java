/*
package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import slimebound.characters.SlimeboundCharacter;


public class CombatDrawPositionPatch {
    @SpirePatch(clz = MonsterGroup.class, method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractMonster[].class
            })
    public static class clusterCultists {
        public static void Prefix(MonsterGroup obj, AbstractMonster[] monsters) {

            // //SlimeboundMod.logger.info("Cultist cluster Patch hit.");
            if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                // //SlimeboundMod.logger.info("Cultist cluster Patch hit player is Slimebound.");
                if (monsters.length == 3) {
                    if (monsters[2].id == AwakenedOne.ID && monsters[1].id == Cultist.ID && monsters[0].id == Cultist.ID) {
                        // //SlimeboundMod.logger.info("Cultist cluster Patch hit woke one found");

                        //monsters[1].hb_x -= -100 * Settings.scale;
                        monsters[1].drawX -= -100 * Settings.scale;
                        //monsters[0].hb_x -= -100 * Settings.scale;
                        monsters[0].drawX -= -170 * Settings.scale;

                    }
                }
                if (monsters.length == 2) {
                    if (monsters[0].id == SpireShield.ID && monsters[1].id == SpireSpear.ID) {
                        ////SlimeboundMod.logger.info("Cultist cluster Patch hit Spire elites found");

                        //monsters[1].hb_x -= -100 * Settings.scale;
                        monsters[1].drawX -= -150 * Settings.scale;
                        //monsters[0].hb_x -= -100 * Settings.scale;
                        monsters[0].drawX -= 150 * Settings.scale;
                        ((SlimeboundCharacter) AbstractDungeon.player).leftScale = 0.4F;
                        ((SlimeboundCharacter) AbstractDungeon.player).xStartOffset = (float) Settings.WIDTH * 0.5F;
                        ((SlimeboundCharacter) AbstractDungeon.player).initializeSlotPositions();
                    }
                }
                ((SlimeboundCharacter) AbstractDungeon.player).moved = false;

            }

        }
    }
}



*/