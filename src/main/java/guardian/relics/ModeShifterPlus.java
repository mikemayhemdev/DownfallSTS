package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import guardian.GuardianMod;
import guardian.cards.GearUp;
import guardian.stances.DefensiveMode;

public class ModeShifterPlus extends CustomRelic {
    public static final String ID = "Guardian:ModeShifterPlus";
    public static final String IMG_PATH = "relics/guardianGear.png";
    public static final String OUTLINE_IMG_PATH = "relics/guardianGearOutline.png";

    public ModeShifterPlus() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)),
                new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new MakeTempCardInHandAction(new GearUp()));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (newStance instanceof DefensiveMode) {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToTop(new GainEnergyAction(1));
            addToTop(new DrawCardAction(AbstractDungeon.player, 2));
        }
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(ModeShifter.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(ModeShifter.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ModeShifterPlus();
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ModeShifter.ID);
    }
}
