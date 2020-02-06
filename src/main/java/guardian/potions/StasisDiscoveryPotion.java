package guardian.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import guardian.actions.StasisCodexAction;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.characters.GuardianCharacter;

public class StasisDiscoveryPotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:StasisDiscoveryPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public StasisDiscoveryPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPHERE, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = GuardianCharacter.cardRenderColor;
    }


    public void initializeData() {
        this.potency = getPotency();

        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + this.potency + DESCRIPTIONS[2]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("stasis")), GameDictionary.keywords.get("stasis")));

    }

    public void use(AbstractCreature target) {


        if (AbstractDungeon.player.maxOrbs < this.potency ) {

            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.potency - AbstractDungeon.player.maxOrbs));

        }
        for (int i = 0; i < this.potency; i++) {

            AbstractDungeon.actionManager.addToBottom(new StasisCodexAction());
        }

    }


    public CustomPotion makeCopy() {
        return new StasisDiscoveryPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }
}


