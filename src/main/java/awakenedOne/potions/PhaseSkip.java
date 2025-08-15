package awakenedOne.potions;

import awakenedOne.AwakenedOneMod;
import awakenedOne.ui.OrbitingSpells;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import static awakenedOne.util.Wiz.awaken;

public class PhaseSkip extends CustomPotion {
    public static final String POTION_ID = "awakened:PhaseSkip";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public PhaseSkip() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        if (AwakenedOneMod.awakenedthiscombat == false) {
            AbstractDungeon.actionManager.addToTop(new VFXAction(target, new IntenseZoomEffect(target.hb.cX, target.hb.cY, true), 0.05F, true));
            AbstractDungeon.actionManager.addToTop(new SFXAction("VO_AWAKENEDONE_1"));
            awaken();
            OrbitingSpells.upgradeall();
        }
    }

    public CustomPotion makeCopy() {
        return new PhaseSkip();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}
