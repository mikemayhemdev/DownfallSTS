package collector.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import collector.CollectorMod;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;


public class MiniCursePotion extends CustomPotion {
    public static final String POTION_ID = makeID(MiniCursePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MiniCursePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = true;
        this.targetRequired = true;
        this.labOutlineColor= CollectorMod.potionLabColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            this.description = potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2] + potency + potionStrings.DESCRIPTIONS[3] + potency * 6 + potionStrings.DESCRIPTIONS[4];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.WEAK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.WEAK.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.VULNERABLE.NAMES[0]), GameDictionary.keywords.get(GameDictionary.VULNERABLE.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("collector:doom")), BaseMod.getKeywordDescription("collector:doom")));
    }

    public void use(AbstractCreature target) {
        atb(new VFXAction(new CollectorCurseEffect(target.hb.cX, target.hb.cY), .2F));
        applyToEnemy((AbstractMonster) target, new WeakPower(target, potency, false));
        applyToEnemy((AbstractMonster) target, new VulnerablePower(target, potency, false));
        applyToEnemy((AbstractMonster) target, new DoomPower((AbstractMonster) target, potency * 6));
    }

    public CustomPotion makeCopy() {
        return new MiniCursePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
