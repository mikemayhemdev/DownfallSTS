package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.stances.DefensiveMode;

public class ModeShifterPlus extends CustomRelic {
    public static final String ID = "Guardian:ModeShifterPlus";
    public static final String IMG_PATH = "relics/guardianGear.png";
    public static final String OUTLINE_IMG_PATH = "relics/guardianGearOutline.png";
    private static final int HP_PER_CARD = 1;

    public ModeShifterPlus() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 8));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(DefensiveMode.STANCE_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DontLeaveDefensiveModePower(AbstractDungeon.player, 3), 3));
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

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ModeShifter.ID);
    }
}