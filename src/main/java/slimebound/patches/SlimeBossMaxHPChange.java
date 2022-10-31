
/*
package slimebound.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import slimebound.SlimeboundMod;

@SpirePatch(clz = SlimeBoss.class, method = SpirePatch.CONSTRUCTOR)
public class SlimeBossMaxHPChange {

    @SpirePostfixPatch
    public static void Postfix(SlimeBoss sb) {
        if (SlimeboundMod.huntedTriggered) {
            // //SlimeboundMod.logger.info("Hunted event triggeredd in: " + CardCrawlGame.dungeon.toString());
            SlimeboundMod.huntedTriggered = false;

            if (CardCrawlGame.dungeon instanceof TheCity) {
                //   //SlimeboundMod.logger.info("Slime Boss from Hunted in City");
                if (AbstractDungeon.ascensionLevel >= 9) {

                    sb.currentHealth = 200;
                    if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                        float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                        sb.currentHealth = (int) ((float) sb.currentHealth * mod);
                    }

                    if (ModHelper.isModEnabled("MonsterHunter")) {
                        sb.currentHealth = (int) ((float) sb.currentHealth * 1.5F);
                    }

                    sb.maxHealth = sb.currentHealth;

                } else {

                    sb.currentHealth = 180;
                    if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                        float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                        sb.currentHealth = (int) ((float) sb.currentHealth * mod);
                    }

                    if (ModHelper.isModEnabled("MonsterHunter")) {
                        sb.currentHealth = (int) ((float) sb.currentHealth * 1.5F);
                    }

                    sb.maxHealth = sb.currentHealth;
                }
            } else if (CardCrawlGame.dungeon instanceof TheBeyond) {
                //  //SlimeboundMod.logger.info("Slime Boss from Hunted in Beyond");

                if (AbstractDungeon.ascensionLevel >= 9) {

                    sb.currentHealth = 250;
                    if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                        float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                        sb.currentHealth = (int) ((float) sb.currentHealth * mod);
                    }

                    if (ModHelper.isModEnabled("MonsterHunter")) {
                        sb.currentHealth = (int) ((float) sb.currentHealth * 1.5F);
                    }

                    sb.maxHealth = sb.currentHealth;

                } else {

                    sb.currentHealth = 220;
                    if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                        float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                        sb.currentHealth = (int) ((float) sb.currentHealth * mod);
                    }

                    if (ModHelper.isModEnabled("MonsterHunter")) {
                        sb.currentHealth = (int) ((float) sb.currentHealth * 1.5F);
                    }

                    sb.maxHealth = sb.currentHealth;
                }

            }
        }
    }

}
*/