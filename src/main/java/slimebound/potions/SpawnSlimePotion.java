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

import java.util.Random;


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
        this.description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {

        for (int i = 0; i < this.potency; i++) {
            if (AbstractDungeon.cardRng.randomBoolean()) {
                SlimeboundMod.spawnSpecialistSlime();
            } else {
                SlimeboundMod.spawnNormalSlime();
            }
        }
    }


    public CustomPotion makeCopy() {
        return new SpawnSlimePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }
}


