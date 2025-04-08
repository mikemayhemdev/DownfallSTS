package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import gremlin.GremlinMod;
import gremlin.cards.Ward;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.MadGremlin;
import gremlin.orbs.SneakyGremlin;
import gremlin.powers.ModifiedLoseStrengthPower;
import gremlin.powers.WizPower;

public class CounterStrikeAction extends AbstractGameAction
{
    private AbstractMonster m;
    private boolean isUpgraded;

    public CounterStrikeAction(final AbstractMonster m, final int amount, boolean isUpgraded) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.m = m;
        this.isUpgraded = isUpgraded;
    }

    @Override
    public void update() {
        if (GremlinMod.doesEnemyIntendToAttack(this.m)) {
                String gremlin = "";
                boolean isNob = false;
                if(AbstractDungeon.player instanceof GremlinCharacter) {
                    if(((GremlinCharacter) AbstractDungeon.player).nob){
                        isNob = true;
                    } else {
                        gremlin = ((GremlinCharacter) AbstractDungeon.player).currentGremlin;
                    }
                }

                if(gremlin.equals("shield")){
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 2));
                }

                if(gremlin.equals("angry")){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new StrengthPower(AbstractDungeon.player, MadGremlin.STRENGTH)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new ModifiedLoseStrengthPower(AbstractDungeon.player, MadGremlin.STRENGTH)));
                }

                else if(gremlin.equals("wizard")){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new WizPower(AbstractDungeon.player, 1), 1));
                } else {
                    // AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                    //      this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }

                if(gremlin.equals("fat")){
                    for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!mo.isDeadOrEscaped()) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player,
                                    new WeakPower(mo, 1, false), 1));
                        }
                    }
                }

                if(gremlin.equals("sneak")){
                    AbstractDungeon.actionManager.addToBottom(
                            new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, SneakyGremlin.DAMAGE, DamageInfo.DamageType.THORNS),
                                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }

                if(gremlin.equals("wizard")){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new WizPower(AbstractDungeon.player, 1), 1));
                }
            }
        this.isDone = true;
    }
}

