package gremlin.actions;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.relics.FragmentationGrenade;

public class DamageRandomEnemyActionButItsRelicRng extends AbstractGameAction {
    private DamageInfo info;

    public DamageRandomEnemyActionButItsRelicRng(DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {

        if (AbstractDungeon.player.hasRelic(FragmentationGrenade.ID)) {
            AbstractDungeon.player.getRelic(FragmentationGrenade.ID).flash();
            DamageInfo old = this.info;
            this.info = new DamageInfo(old.owner, old.base + FragmentationGrenade.OOMPH, old.type);
        }

        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.relicRng);
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }

        this.isDone = true;
    }
}
