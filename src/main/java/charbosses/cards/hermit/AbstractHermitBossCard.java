package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractHermitBossCard extends AbstractBossCard {
    public AbstractHermitBossCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                                  CardColor color, CardRarity rarity, CardTarget target, AbstractMonster.Intent intent) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target, intent);
    }

    protected void renderHelperU(SpriteBatch sb, Color color, TextureAtlas.AtlasRegion img, float drawX, float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX + img.offsetX - (float)img.originalWidth / 2.0F, drawY + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
    }
}
