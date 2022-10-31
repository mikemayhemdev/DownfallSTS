package gremlin.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.actions.NecromancyAction;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;

public class NecromancyPotion extends CustomPotion {
    public static final String POTION_ID = "gremlin:NecromancyPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public NecromancyPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.GHOST, PotionColor.FRUIT);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        float percent = (float)this.potency / 100.0F;
        int healAmt = (int)((float)AbstractDungeon.player.maxHealth * percent);
        if (healAmt < 1) {
            healAmt = 1;
        }

        AbstractDungeon.actionManager.addToTop(new NecromancyAction(healAmt));
    }

    public boolean canUse() {
        if (super.canUse()) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                return deadGrem();
            }
            return false;
        } else {
            return false;
        }
    }

    public CustomPotion makeCopy() {
        return new NecromancyPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 50;
    }

    private boolean deadGrem(){
        int count = 0;
        for(AbstractOrb orb : AbstractDungeon.player.orbs){
            if(orb instanceof GremlinStandby){
                count++;
            }
        }
        int total = 4;
        if(AbstractDungeon.player instanceof GremlinCharacter){
            total -= ((GremlinCharacter) AbstractDungeon.player).mobState.numEnslaved();
        }
        return (count!=total);
    }
}

