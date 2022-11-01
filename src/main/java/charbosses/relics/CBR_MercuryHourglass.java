package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MercuryHourglass;

public class CBR_MercuryHourglass extends AbstractCharbossRelic {
    public static final String ID = "MercuryHourglass";

    public CBR_MercuryHourglass() {
        super(new MercuryHourglass());
        this.counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStart() {
        //   this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractCharBoss.boss, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MercuryHourglass();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.owner.hb.x - 80.0F * Settings.scale, this.owner.hb.y + 180.0F * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, 0F, 0, 0, 128, 128, false, false);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(3), this.owner.hb.x - 80.0F * Settings.scale, this.owner.hb.y + 180.0F * Settings.scale, Color.WHITE.cpy());

        // this.hb.render(sb);
    }
}
