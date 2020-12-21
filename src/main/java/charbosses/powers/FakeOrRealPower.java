package charbosses.powers;

import basemod.interfaces.CloneablePowerInterface;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.monsters.MirrorImageSilent;
import charbosses.vfx.QuietSpecialSmokeBombEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.SmokeBlurEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import downfall.downfallMod;
import theHexaghost.util.TextureLoader;

public class FakeOrRealPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("FakeOrRealPower");

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/InverseBias84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/InverseBias32.png"));

    float particleTimer = 0.025f;

    public FakeOrRealPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = "???";
        this.amount = 5;

        this.updateDescription();
    }


    public void updateDescription() {
        this.description = "You should never see this.";
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (q instanceof MirrorImageSilent) {
                q.currentHealth = 0;
                q.isDead = true;
            }
            addToTop(new RemoveSpecificPowerAction(q, q, this.ID));
        }
        CharBossSilent.foggy = false;
        return damageAmount;
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer <= 0) {
            particleTimer = 0.025f;
            AbstractDungeon.effectsQueue.add(new QuietSpecialSmokeBombEffect(AbstractDungeon.cardRandomRng.random(owner.healthHb.x, owner.healthHb.x + owner.hb.width), owner.hb.y));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FakeOrRealPower(owner);
    }
}
