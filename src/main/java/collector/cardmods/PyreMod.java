package collector.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import static collector.CollectorMod.makeID;

public class PyreMod extends AbstractCardModifier {
    public static final String ID = makeID("PyreMod");
    public static final TextureAtlas.AtlasRegion healthBlob = CollectorMod.UIAtlas.findRegion("heartOrb");

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    public int exhaustAmt;

    public PyreMod(int exhaustAmt) {
        this.exhaustAmt = exhaustAmt;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PyreMod(exhaustAmt);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return AbstractDungeon.player.hand.size() - 1 >= exhaustAmt;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale);
        renderHelper(sb, healthBlob, card.current_x, card.current_y, card);
        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(exhaustAmt), card.current_x, card.current_y, -133.0F * card.drawScale * Settings.scale, 133 * card.drawScale * Settings.scale, card.angle, false, Color.WHITE.cpy());
    }

    private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
        Color color = Color.WHITE.cpy();
        color.a = C.transparency;
        sb.setColor(color);
        sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }
}
