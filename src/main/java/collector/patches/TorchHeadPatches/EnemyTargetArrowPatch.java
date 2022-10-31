package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.CollectorMod;
import collector.util.DuoUtils.TargetArrow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

public class EnemyTargetArrowPatch {
    private static float arrowTime = 0.0f;
    private static float alpha = 0.0f;
    private static AbstractMonster hoveredMonster = null;
    private static boolean targetMarkerHovered = false;

    private static float alphaSpeed = 3.0f;

    @SpirePatch(clz = MonsterGroup.class, method = "render")
    public static class MonsterGroupRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(MonsterGroup __instance, SpriteBatch sb) {
            if (AbstractDungeon.player instanceof CollectorChar) {

                    AbstractMonster theirHovered = __instance.hoveredMonster;
                    if (theirHovered != null && theirHovered.isDeadOrEscaped()) {
                        theirHovered = null;
                    }
                    if (hoveredMonster != theirHovered) {
                        if (theirHovered == null) {
                            alpha -= Gdx.graphics.getDeltaTime() * alphaSpeed;
                            if (alpha <= 0.0f) {
                                alpha = 0.0f;
                                hoveredMonster = null;
                                arrowTime = 0.0f;
                            }
                        } else {
                            hoveredMonster = theirHovered;
                            alpha = 0.0f;
                            arrowTime = 0.0f;
                        }
                    } else if (hoveredMonster != null) {
                        alpha += Gdx.graphics.getDeltaTime() * alphaSpeed / 4;
                        if (alpha > 0.7f) {
                            alpha = 0.7f;
                        }
                    }

                    if (CollectorMod.targetMarker.hb.hovered) {
                        hoveredMonster = null;
                        if (!targetMarkerHovered) {
                            targetMarkerHovered = true;
                            alpha = 0.0f;
                            arrowTime = 0.0f;
                        }
                        alpha += Gdx.graphics.getDeltaTime() * alphaSpeed;
                        if (alpha > 1.0f) {
                            alpha = 1.0f;
                        }
                    } else if (targetMarkerHovered) {
                        alpha -= Gdx.graphics.getDeltaTime() * alphaSpeed;
                        if (alpha <= 0.0f) {
                            alpha = 0.0f;
                            targetMarkerHovered = false;
                        }
                    }
                }

                if (targetMarkerHovered && AbstractDungeon.getMonsters() != null) {
                    for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        if (!m.isDeadOrEscaped()) {
                            AbstractCreature target = CollectorChar.getCurrentTarget(m);
                            TargetArrow.drawTargetArrow(
                                    sb, m.hb, target.hb, target instanceof CollectorChar ? TargetArrow.CONTROL_HEIGHT * 0.5f * Settings.scale : TargetArrow.CONTROL_HEIGHT * Settings.scale, arrowTime, alpha, null);
                        }
                    }
                } else if (hoveredMonster != null) {
                    if (alpha > 0.2f) {
                        AbstractCreature target = CollectorChar.getCurrentTarget(hoveredMonster);
                        TargetArrow.drawTargetArrow(
                                sb, hoveredMonster.hb, target.hb, target instanceof CollectorChar ? TargetArrow.CONTROL_HEIGHT * 0.5f * Settings.scale : TargetArrow.CONTROL_HEIGHT * Settings.scale, arrowTime, alpha - 0.2f, null);
                    }
                }
                arrowTime += Gdx.graphics.getDeltaTime();
            }
        }
    }
