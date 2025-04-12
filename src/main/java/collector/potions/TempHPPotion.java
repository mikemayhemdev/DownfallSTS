package collector.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.vfx.combat.HealVerticalLineEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;


public class TempHPPotion extends CustomPotion {
    public static final String POTION_ID = makeID(TempHPPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TempHPPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            this.description = potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("expansioncontent:temp_hp")), BaseMod.getKeywordDescription("expansioncontent:temp_hp")));
    }

    public void use(AbstractCreature target) {
        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            CardCrawlGame.sound.play("HEAL_1");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("HEAL_2");
        } else {
            CardCrawlGame.sound.play("HEAL_3");
        }

        atb(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, potency));
    }

    public CustomPotion makeCopy() {
        return new TempHPPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 8;
    }
}
