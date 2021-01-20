package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CursedKey;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.actions.BanditIOUAction;
import downfall.downfallMod;

public class ExtraCursedKey extends CursedKey {

    public static String ID = downfallMod.makeID("ExtraCursedKey");

    public ExtraCursedKey() {
        super();
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    }

    @Override
    public void onChestOpen(boolean bossChest) {
        if (!bossChest) {
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.returnRandomCurse(), (float) (Settings.WIDTH * 0.35), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.returnRandomCurse(), (float) (Settings.WIDTH * 0.65), (float) (Settings.HEIGHT / 2)));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new ExtraCursedKey();
    }

}
