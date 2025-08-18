package charbosses.powers.bossmechanicpowers;

import basemod.interfaces.CloneablePowerInterface;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Defect.Simpler.ArchetypeAct1VoidsSimple;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import hermit.util.Wiz;

public class VoidOrbPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("VoidOrbPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/FairyPotion84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/FairyPotion32.png"));

    public static boolean CANNOT_END = false;

    public VoidOrbPower(final AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();


    }

    private void healUndead(AbstractCreature m, int healAmount) {
        if (m.isDying) m.isDying = false;
        if (AbstractDungeon.ascensionLevel >= 19) m.maxHealth += 5;
        for (AbstractPower p : m.powers) {
            p.onHeal(healAmount);
        }

        m.currentHealth += healAmount;
        if (m.currentHealth > m.maxHealth) {
            m.currentHealth = m.maxHealth;
        }


        if (healAmount > 0) {
            m.healthBarUpdatedEvent();
        }

    }

    @Override
    public void onDeath() {
        CANNOT_END = true;
        super.onDeath();
        healUndead(owner, 9999);
        AbstractDungeon.effectsQueue.add(new HealEffect(owner.hb.cX, owner.hb.cY, owner.maxHealth));
        if (AbstractCharBoss.boss != null){
            if (AbstractCharBoss.boss.chosenArchetype != null){
                ((ArchetypeAct1VoidsSimple)AbstractCharBoss.boss.chosenArchetype).minionDestroyedThisTurn = true;
                if (!AbstractCharBoss.boss.orbs.isEmpty()) {
                    AbstractCharBoss.boss.removeNextOrb();
                }
                ((CharBossDefect)AbstractCharBoss.boss).refreshFromA1MinionDeath();

            }
        }
        //Wiz.applyToSelf(new VoidProtectionPower(AbstractDungeon.player));
        CANNOT_END = false;
    }

    @Override
    public void updateDescription() {
        if (AbstractDungeon.ascensionLevel >= 19){
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new VoidOrbPower(owner);
    }
}