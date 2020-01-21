package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theHexaghost.HexaMod;
import theHexaghost.actions.BurnAction;
import theHexaghost.util.OnChargeSubscriber;
import theHexaghost.util.TextureLoader;

public class RadiantPower extends AbstractPower implements CloneablePowerInterface, OnChargeSubscriber {

    public static final String POWER_ID = HexaMod.makeID("RadiantPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public RadiantPower(final int amount) {
        this.name = "Radiant Flame";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onCharge() {
        flash();
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (!m.isDead && !m.isDying) {
            addToBot(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.5F));// 173
            addToBot(new BurnAction(m, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = "Whenever a Ghostflame becomes Charged, apply #b" + amount + " #yBurn to a random enemy.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new RadiantPower(amount);
    }
}