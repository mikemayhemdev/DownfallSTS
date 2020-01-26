
package slimebound.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.SpawnedSlime;

import java.util.ArrayList;



public class CombatDrawPositionPatch {
    @SpirePatch(clz = MonsterGroup.class, method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractMonster[].class
                    })
    public static class clusterCultists {
        public static void Prefix(MonsterGroup obj, AbstractMonster[] monsters) {

           // SlimeboundMod.logger.info("Cultist cluster Patch hit.");
            if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                //TODO - rewrite this all to check if an enemy is too far left like the awakened one fight and not exactly that fight.
                //TODO - rewrite the next one to check for enemies left of the character position and not the exact Spire duo fight.
                // SlimeboundMod.logger.info("Cultist cluster Patch hit player is Slimebound.");
                if (monsters.length == 3) {
                    if (monsters[2].id == AwakenedOne.ID && monsters[1].id == Cultist.ID && monsters[0].id == Cultist.ID) {
                        // SlimeboundMod.logger.info("Cultist cluster Patch hit woke one found");

                        //monsters[1].hb_x -= -100 * Settings.scale;
                        monsters[1].drawX -= -100 * Settings.scale;
                        //monsters[0].hb_x -= -100 * Settings.scale;
                        monsters[0].drawX -= -170 * Settings.scale;

                    }
                }
                if (monsters.length == 2) {
                    if (monsters[0].id == SpireShield.ID && monsters[1].id == SpireSpear.ID) {
                         //SlimeboundMod.logger.info("Cultist cluster Patch hit Spire elites found");

                        //monsters[1].hb_x -= -100 * Settings.scale;
                        monsters[1].drawX -= -150 * Settings.scale;
                        //monsters[0].hb_x -= -100 * Settings.scale;
                        monsters[0].drawX -= 150 * Settings.scale;
                        ((SlimeboundCharacter) AbstractDungeon.player).leftScale = 0.4F;
                        ((SlimeboundCharacter) AbstractDungeon.player).xStartOffset = (float)Settings.WIDTH * 0.5F;
                        ((SlimeboundCharacter) AbstractDungeon.player).initializeSlotPositions();
                    }
                }
                ((SlimeboundCharacter) AbstractDungeon.player).moved = false;

            }

        }
    }
}


