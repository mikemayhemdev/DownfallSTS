package guardian.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.stances.DefensiveMode;
import guardian.characters.GuardianCharacter;

public class DefensiveModePotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:DefensiveModePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public DefensiveModePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = GuardianCharacter.cardRenderColor;
    }


    public void initializeData() {
        this.potency = getPotency();
        if (potency > 1){
            this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[2]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("guardianmod:defensive mode")), GameDictionary.keywords.get("guardianmod:defensive mode")));

    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, 10));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(DefensiveMode.STANCE_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DontLeaveDefensiveModePower(AbstractDungeon.player, potency), potency));
    }


    public CustomPotion makeCopy() {
        return new DefensiveModePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


