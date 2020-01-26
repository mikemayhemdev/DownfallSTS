package theHexaghost;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.ghostflames.*;
import theHexaghost.util.OnAdvanceOrRetractSubscriber;
import theHexaghost.util.OnAdvanceSubscriber;
import theHexaghost.util.OnRetractSubscriber;

import java.util.ArrayList;

public class GhostflameHelper {
    public static ArrayList<AbstractGhostflame> hexaGhostFlames = new ArrayList<>();

    public static AbstractGhostflame activeGhostFlame;

    public static void init() {
        hexaGhostFlames.clear();
        hexaGhostFlames.add(new SearingGhostflame(AbstractDungeon.player.drawX - (120.0F * Settings.scale), AbstractDungeon.player.drawY + (370.0F * Settings.scale)));
        hexaGhostFlames.add(new CrushingGhostflame(AbstractDungeon.player.drawX + (60.0F * Settings.scale), AbstractDungeon.player.drawY + (370.0F * Settings.scale)));
        hexaGhostFlames.add(new SearingGhostflame(AbstractDungeon.player.drawX + (130.0F * Settings.scale), AbstractDungeon.player.drawY + (240.0F * Settings.scale)));
        hexaGhostFlames.add(new BolsteringGhostflame(AbstractDungeon.player.drawX + (60.0F * Settings.scale), AbstractDungeon.player.drawY + (110.0F * Settings.scale)));
        hexaGhostFlames.add(new SearingGhostflame(AbstractDungeon.player.drawX - (120.0F * Settings.scale), AbstractDungeon.player.drawY + (110.0F * Settings.scale)));
        hexaGhostFlames.add(new InfernoGhostflame(AbstractDungeon.player.drawX - (190.0F * Settings.scale), AbstractDungeon.player.drawY + (240.0F * Settings.scale)));
        hexaGhostFlames.get(0).activate();
    }


    public static AbstractGhostflame getNextGhostFlame() {
        AbstractGhostflame x;
        if (activeGhostFlame == hexaGhostFlames.get(5)) {
            x = hexaGhostFlames.get(0);
        } else
            x = hexaGhostFlames.get((hexaGhostFlames.indexOf(activeGhostFlame) + 1));
        return x;
    }

    public static AbstractGhostflame getPreviousGhostFlame() {
        AbstractGhostflame x;
        if (activeGhostFlame == hexaGhostFlames.get(0)) {
            x = hexaGhostFlames.get(hexaGhostFlames.size() - 1);
        } else
            x = hexaGhostFlames.get((hexaGhostFlames.indexOf(activeGhostFlame) - 1));
        return x;
    }

    public static void advance() {
        AbstractGhostflame x = hexaGhostFlames.get((hexaGhostFlames.indexOf(activeGhostFlame) + 1) % hexaGhostFlames.size());
        if (x.charged) {
            x.extinguish();
        }
        x.activate();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnAdvanceSubscriber) ((OnAdvanceSubscriber) p).onAdvance();
            if (p instanceof OnAdvanceOrRetractSubscriber) ((OnAdvanceOrRetractSubscriber) p).onAdvanceOrRetract();
        }
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
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnRetractSubscriber) ((OnRetractSubscriber) p).onRetract();
            if (p instanceof OnAdvanceOrRetractSubscriber) ((OnAdvanceOrRetractSubscriber) p).onAdvanceOrRetract();
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof OnAdvanceOrRetractSubscriber) ((OnAdvanceOrRetractSubscriber) c).onAdvanceOrRetract();
        }
    }

    public static Color partialTransparent = new Color(1F, 1F, 1F, 0.75F);

    public static void update() {
        if (HexaMod.renderFlames)
            for (AbstractGhostflame gf : hexaGhostFlames) {
                gf.update();
            }
    }

    public static boolean showAll = false;

    public static void render(SpriteBatch sb) {
        if (HexaMod.renderFlames && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            showAll = false;
            for (AbstractGhostflame gf : hexaGhostFlames) {
                if (gf.hitbox.hovered) {
                    showAll = true;
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
