package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;
import guardian.rewards.GemReward;
import guardian.rewards.GemRewardAllRarities;

import java.util.ArrayList;

public class SackOfGems extends CustomRelic {
    public static final String ID = "Guardian:SackOfGems";
    public static final String IMG_PATH = "relics/sackOfgems.png";
    public static final String OUTLINE_IMG_PATH = "relics/sackOfgemsOutline.png";
    private static final int HP_PER_CARD = 1;

    public SackOfGems() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 5);
        ArrayList<AbstractCard> rewards = new ArrayList<>();
        int rando;
        for(int i = 0; i < 5; ++i) {
            rando = AbstractDungeon.cardRng.random(gems.size() - 1);
            rewards.add(gems.get(rando));
            gems.remove(rando);
        }

        int times = 0;
        for (AbstractCard c: rewards){

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)(Settings.WIDTH * (0.1 + (0.2 * times))), (float)(Settings.HEIGHT / 2)));
            times++;
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new SackOfGems();
    }
}