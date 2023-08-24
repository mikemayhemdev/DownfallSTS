package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import com.megacrit.cardcrawl.vfx.BobEffect;
import downfall.downfallMod;
import downfall.util.TextureLoader;

public class CBR_MercuryHourglass extends AbstractCharbossRelic {
    public static final String ID = "MercuryHourglass";
    private static Texture red = TextureLoader.getTexture(downfallMod.assetPath("images/relics/RedHourglass.png"));
    private BobEffect bob;

    public CBR_MercuryHourglass() {
        super(new MercuryHourglass());
        bob = new BobEffect();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStart() {
        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractCharBoss.boss, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MercuryHourglass();
    }

    @Override
    public void update() {
        super.update();
        bob.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        sb.setColor(Color.WHITE.cpy());
        sb.draw(red, this.owner.hb.x - 230.0F * Settings.scale, this.owner.hb.y + 240.0F * Settings.scale + bob.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0F, 0, 0, 128, 128, false, false);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(3), this.owner.hb.x - 205.0F * Settings.scale, this.owner.hb.y + 290.0F * Settings.scale + bob.y, Color.WHITE.cpy());
    }
}
