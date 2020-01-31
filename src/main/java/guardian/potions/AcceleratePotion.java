package guardian.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.orbs.StasisOrb;

public class AcceleratePotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:AcceleratePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public AcceleratePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.STEROID);
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0]);
        this.isThrown = false;
        this.targetRequired = false;
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("accelerate")), GameDictionary.keywords.get("accelerate")));

    }

    public void use(AbstractCreature target) {

        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.BorderFlashEffect(com.badlogic.gdx.graphics.Color.GOLD, true));
        AbstractDungeon.topLevelEffectsQueue.add(new com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect());

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof StasisOrb) {
                o.onStartOfTurn();
                ((StasisOrb) o).stasisCard.superFlash(Color.GOLDENROD);
            }
        }

    }


    public CustomPotion makeCopy() {
        return new AcceleratePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 4;
    }
}


