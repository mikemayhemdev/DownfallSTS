package collector.potions;


import basemod.abstracts.CustomPotion;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;


public class MiniCursePotion extends CustomPotion {
    public static final String POTION_ID = makeID(MiniCursePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MiniCursePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.ELIXIR); //TODO: Potion visual stuff
        this.isThrown = true;
        this.targetRequired = true;
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
    }

    public void use(AbstractCreature target) {
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
