package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.CBR_Necronomicon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;

public abstract class AbstractHermitBossCard extends AbstractBossCard {
    public AbstractHermitBossCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                                  CardColor color, CardRarity rarity, CardTarget target, AbstractMonster.Intent intent) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target, intent, true);
    }

    protected void renderHelperU(SpriteBatch sb, Color color, TextureAtlas.AtlasRegion img, float drawX, float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX + img.offsetX - (float)img.originalWidth / 2.0F, drawY + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
    }


    private static AbstractRelic nCon = new Necronomicon();

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (AbstractCharBoss.boss != null && AbstractCharBoss.boss.hasRelic(CBR_Necronomicon.ID) && this.cost >= 2) {
            nCon.currentX = this.current_x + 390.0f * this.drawScale / 3.0f * Settings.scale;
            nCon.currentY = this.current_y + 546.0f * this.drawScale / 3.0f * Settings.scale;
            nCon.scale = this.drawScale;
            nCon.renderOutline(sb, false);
            nCon.render(sb);
        }
    }
}
