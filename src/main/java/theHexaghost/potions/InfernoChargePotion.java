package theHexaghost.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import slimebound.characters.SlimeboundCharacter;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;


public class InfernoChargePotion extends CustomPotion {
    public static final String POTION_ID = "hexamod:InfernoChargePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public InfernoChargePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.FAIRY);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = HexaMod.placeholderColor;
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
    }

    public void use(AbstractCreature target) {

        for (int i = 0; i < this.potency; i++) {

            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                        if (gf instanceof InfernoGhostflame) {
                            addToTop(new ChargeAction(gf));
                        }
                    }
                }
            });
        }
       }


    public CustomPotion makeCopy() {
        return new InfernoChargePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


