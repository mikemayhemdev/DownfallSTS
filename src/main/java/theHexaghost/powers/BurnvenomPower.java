package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.actions.BurnAction;
import theHexaghost.util.TextureLoader;

public class BurnvenomPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("BurnvenomPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Burnvenom84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Burnvenom32.png");

    public BurnvenomPower(final int amount) {
        this.name = "Wildfire Weapon";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && target instanceof AbstractMonster && info.type == DamageInfo.DamageType.NORMAL) {// 32
            this.flash();// 33
            this.addToTop(new BurnAction(((AbstractMonster) target), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = "Whenever you deal unblocked attack damage, apply #b" + amount + " #yBurn.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new BurnvenomPower(amount);
    }
}