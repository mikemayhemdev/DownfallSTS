package downfall.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import downfall.downfallMod;
import downfall.util.TextureLoader;

public class FairyPotionPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("FairyPotion");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/FairyPotion84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/FairyPotion32.png"));

    public static boolean CANNOT_END = false;

    public FairyPotionPower(final AbstractCreature owner, final int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();
    }

    private void healUndead(AbstractCreature m, int healAmount) {
        if (m.isDying) m.isDying = false;
        for (AbstractPower p : m.powers) {
            p.onHeal(healAmount);
        }

        m.currentHealth += healAmount;// 451
        if (m.currentHealth > m.maxHealth) {// 452
            m.currentHealth = m.maxHealth;// 453
        }


        if (healAmount > 0) {// 465
            m.healthBarUpdatedEvent();// 470
        }

    }

    @Override
    public void onDeath() {
        CANNOT_END = true;
        super.onDeath();
        int healAmt = (int) (owner.maxHealth * 0.3F);
        healUndead(owner, healAmt);
        AbstractDungeon.effectsQueue.add(new HealEffect(owner.hb.cX, owner.hb.cY, healAmt));
        addToTop(new RemoveSpecificPowerAction(owner, owner, FairyPotionPower.POWER_ID));
        addToTop(new TalkAction(owner, DESCRIPTIONS[1]));
        CANNOT_END = false;
    }

    /*


     */

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FairyPotionPower(owner, amount);
    }
}