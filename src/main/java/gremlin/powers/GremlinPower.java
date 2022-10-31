package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;
import gremlin.GremlinMod;

public abstract class GremlinPower extends AbstractGremlinPower {
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/gremlinPower.png"));

    protected int pot = 0;

    public GremlinPower() {
        this.owner = AbstractDungeon.player;
        this.img = IMG;
        this.amount = -1;

        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void onInitialApplication() {
        for(int i=0;i<owner.powers.size();i++){
            AbstractPower p = owner.powers.get(i);
            if(p instanceof GremlinPower && !p.ID.equals(this.ID)){
                AbstractDungeon.effectList.add(new PowerExpireTextEffect(owner.hb.cX - owner.animX, owner.hb.cY + owner.hb.height / 2.0F, p.name, p.region128));
                p.onRemove();
                owner.powers.remove(p);
                AbstractDungeon.onModifyPower();
            }
        }
    }
}
