package downfall.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import downfall.downfallMod;
import downfall.monsters.AbstractTotemMonster;
import theHexaghost.util.TextureLoader;

public class TotemInvulnerablePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("TotemInvulnerable");

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/totemImmunity84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/totemImmunity32.png"));

    public static boolean CANNOT_END = false;

    public TotemInvulnerablePower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.POWER_ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.POWER_ID).NAME;

        this.updateDescription();
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > this.owner.currentHealth) {
            boolean timeToDie = true;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != this.owner && m.currentHealth > 1 && m instanceof AbstractTotemMonster) {
                    timeToDie = false;
                }
            }
            if (timeToDie) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m != this.owner) {
                        m.die();
                    }
                }
            } else {
                this.flash();
                return super.onAttackedToChangeDamage(info, this.owner.currentHealth - 1);
            }
        }


        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TotemInvulnerablePower(owner);
    }
}