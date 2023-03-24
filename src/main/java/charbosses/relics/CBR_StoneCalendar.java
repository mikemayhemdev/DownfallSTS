package charbosses.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StoneCalendar;
import com.megacrit.cardcrawl.vfx.BobEffect;
import downfall.downfallMod;
import downfall.util.TextureLoader;

public class CBR_StoneCalendar extends AbstractCharbossRelic {
    public static final String ID = "Stone Calendar";
    private static Texture red = TextureLoader.getTexture(downfallMod.assetPath("images/relics/RedStoneCalendar.png"));
    private BobEffect bob;

    public CBR_StoneCalendar() {
        super(new StoneCalendar());
        bob = new BobEffect();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 7 + this.DESCRIPTIONS[1] + 52 + this.DESCRIPTIONS[2];
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStartPostDraw() {
        ++this.counter;
        if (this.counter == 7) {
            this.beginLongPulse();
        }
    }

    @Override
    public void update() {
        super.update();
        bob.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (counter == 7) {
            sb.setColor(Color.WHITE.cpy());
            sb.draw(red, this.owner.hb.x - 180.0F * Settings.scale, this.owner.hb.y + 240.0F * Settings.scale + bob.y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0F, 0, 0, 128, 128, false, false);
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(52), this.owner.hb.x - 155.0F * Settings.scale, this.owner.hb.y + 290.0F * Settings.scale + bob.y, Color.WHITE.cpy());
        }
    }

    public void onPlayerEndTurn() {
        if (this.counter == 7) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, 52, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            this.stopPulse();
            this.grayscale = true;
        }

    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_StoneCalendar();
    }
}
