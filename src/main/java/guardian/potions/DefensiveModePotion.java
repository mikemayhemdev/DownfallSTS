package guardian.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;

public class DefensiveModePotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:DefensiveModePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public DefensiveModePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.S, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = GuardianCharacter.cardRenderColor;
    }


    public void initializeData() {
        this.potency = getPotency();

        this.description = (DESCRIPTIONS[0] + this.potency * 5 + DESCRIPTIONS[1] + this.potency + DESCRIPTIONS[2]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

    }

    public void use(AbstractCreature target) {


        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, this.potency * 5));
        for (int i = 0; i < this.potency; i++) {

            AbstractDungeon.actionManager.addToBottom(new SwitchToDefenseModeAction(AbstractDungeon.player));
        }

    }


    public CustomPotion makeCopy() {
        return new DefensiveModePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}


