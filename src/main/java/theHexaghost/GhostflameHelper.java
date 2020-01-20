package theHexaghost;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import theHexaghost.ghostflames.*;

import java.util.ArrayList;

public class GhostflameHelper {
    public static ArrayList<AbstractGhostflame> hexaGhostFlames = new ArrayList<>();

    public static AbstractGhostflame activeGhostFlame;

    public static void init() {
        hexaGhostFlames.clear();
        hexaGhostFlames.add(new SearingGhostflame(AbstractDungeon.player.drawX - 95.0F, AbstractDungeon.player.drawY + 375.0F));
        hexaGhostFlames.add(new CrushingGhostflame(AbstractDungeon.player.drawX + 85.0F, AbstractDungeon.player.drawY + 375.0F));
        hexaGhostFlames.add(new SearingGhostflame(AbstractDungeon.player.drawX + 155.0F, AbstractDungeon.player.drawY + 245.0F));
        hexaGhostFlames.add(new BolsteringGhostflame(AbstractDungeon.player.drawX + 85.0F, AbstractDungeon.player.drawY + 115.0F));
        hexaGhostFlames.add(new SearingGhostflame(AbstractDungeon.player.drawX - 95.0F, AbstractDungeon.player.drawY + 115.0F));
        hexaGhostFlames.add(new InfernoGhostflame(AbstractDungeon.player.drawX - 165.0F, AbstractDungeon.player.drawY + 245.0F));
        hexaGhostFlames.get(0).activate();
    }

    public static void advance() {
        AbstractGhostflame x = hexaGhostFlames.get((hexaGhostFlames.indexOf(activeGhostFlame) + 1) % hexaGhostFlames.size());
        if (x.charged) {
            x.extinguish();
        }
        x.activate();
    }

    public static void retract() {
        AbstractGhostflame x;
        if (activeGhostFlame == hexaGhostFlames.get(0)) {
            x = hexaGhostFlames.get(hexaGhostFlames.size() - 1);
        } else
            x = hexaGhostFlames.get((hexaGhostFlames.indexOf(activeGhostFlame) - 1));
        if (x.charged) {
            x.extinguish();
        }
        x.activate();
    }

    public static void update() {
        if (HexaMod.renderFlames)
            for (AbstractGhostflame gf : hexaGhostFlames) {
                gf.update();
            }
    }

    public static void render(SpriteBatch sb) {
        if (HexaMod.renderFlames) {
            for (AbstractGhostflame gf : hexaGhostFlames) {
                if (gf.hitbox.hovered) {
                    if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                        TipHelper.renderGenericTip(
                                (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                gf.getClass().getSimpleName().replaceAll("([^_])([A-Z])", "$1 $2"),
                                gf.getDescription());
                    } else {
                        TipHelper.renderGenericTip((float) InputHelper.mX - 350.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                gf.getClass().getSimpleName().replaceAll("([^_])([A-Z])", "$1 $2"),
                                gf.getDescription());
                    }
                }
            }
        }
    }
}
