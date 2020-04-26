package evilWithin.actions;


import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.CharBossIronclad;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import evilWithin.monsters.NeowBoss;
import evilWithin.vfx.ThrowGoldEffect;

public class NeowRezAction extends AbstractGameAction
{
    private NeowBoss owner;
    private boolean instructedMove;

    public NeowRezAction(NeowBoss owner)
    {
        this.owner = owner;
    }

    @Override
    public void update()
    {
        if (!instructedMove){
            owner.moveForRez();
        }
        AbstractCharBoss cB = new CharBossIronclad();
        cB.init();
        AbstractDungeon.getCurrRoom().monsters.add(cB);

        this.isDone = true;
    }
}