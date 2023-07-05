package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.cards.Ember;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;

public class HolidayCoal extends CustomRelic {
    public static final String ID = CollectorMod.makeID(HolidayCoal.class.getSimpleName());
    private static final String IMG_PATH = HolidayCoal.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = HolidayCoal.class.getSimpleName() + ".png";

    private static final int BLOCK_AMT = 4;

    public HolidayCoal() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        grayscale = false;
    }

    @Override
    public void atTurnStart() {
        grayscale = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!grayscale) {
            flash();
            grayscale = true;
            atb(new GainBlockAction(AbstractDungeon.player, BLOCK_AMT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_AMT + DESCRIPTIONS[1];
    }
}

