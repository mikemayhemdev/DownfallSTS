package slimebound.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.PoisonSlime;
import slimebound.orbs.ShieldSlime;
import slimebound.orbs.SlimingSlime;


public class SpawnSlimePotion extends CustomPotion {
    public static final String POTION_ID = "Slimebound:SpawnSlimePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public SpawnSlimePotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPHERE, PotionColor.POISON);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = SlimeboundMod.SLIME_COLOR;
    }


    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = potionStrings.DESCRIPTIONS[1];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("slimeboundmod:leeching")), BaseMod.getKeywordDescription("slimeboundmod:leeching")));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("slimeboundmod:mire")), BaseMod.getKeywordDescription("slimeboundmod:mire")));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("slimeboundmod:guerilla")), BaseMod.getKeywordDescription("slimeboundmod:guerilla")));
    }

    public void use(AbstractCreature target) {

        if (AbstractDungeon.player.maxOrbs < this.potency * 3) {

            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction((this.potency * 3) - AbstractDungeon.player.maxOrbs));

        }

        for (int i = 0; i < this.potency; i++) {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, false));
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlimingSlime(), false, false));
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, false));
        }

    }


    public CustomPotion makeCopy() {
        return new SpawnSlimePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


