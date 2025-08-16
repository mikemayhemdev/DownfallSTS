package guardian.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;
import guardian.cards.GearUp;

public class ModeShifter extends CustomRelic {
    public static final String ID = "Guardian:ModeShifter";
    public static final String IMG_PATH = "relics/modeShifter.png";
    public static final String OUTLINE_IMG_PATH = "relics/modeShifterOutline.png";

    public ModeShifter() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.STARTER, LandingSound.CLINK);
        this.tips.add(new CardPowerTip(new GearUp()));
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new MakeTempCardInHandAction(new GearUp()));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

//    @Override
//    public void onEquip() {
//        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(GuardianMod.getRewardGemCards(true, 1).get(0).makeStatEquivalentCopy(), (Settings.WIDTH * 0.5F), (Settings.HEIGHT * 0.5F)));
//    }

//    Add gem feature moved to GuardianMod.receivePostCreateStartingDeck , because under current way if you save&load
//    on floor one, the gem disappears,  remember to delete that too if the random add gem feature is changed someday


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ModeShifter();
    }
}