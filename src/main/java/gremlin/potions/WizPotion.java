package gremlin.potions;

import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import gremlin.powers.WizPower;

import static champ.ChampMod.vigor;

public class WizPotion extends CustomPotion {
    public static final String POTION_ID = "gremlin:WizPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public WizPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.POWER);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        if (Settings.language == Settings.GameLanguage.ENG) {
            this.tips.add(new PowerTip(

                    com.megacrit.cardcrawl.helpers.TipHelper.capitalize(GameDictionary.VIGOR.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.VIGOR.NAMES[0])));
        }
    }


    public void use(AbstractCreature target) {
      //  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                //new VigorPower(AbstractDungeon.player, this.potency), this.potency));
        vigor(this.potency);
    }

    public CustomPotion makeCopy() {
        return new WizPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 10;
    }
}


