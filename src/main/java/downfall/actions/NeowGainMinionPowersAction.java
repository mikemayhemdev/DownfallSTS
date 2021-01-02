package downfall.actions;


import charbosses.powers.general.PoisonProtectionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.monsters.NeowBoss;
import downfall.powers.neowpowers.*;

public class NeowGainMinionPowersAction extends AbstractGameAction {
    private NeowBoss owner;
    private int num;

    public NeowGainMinionPowersAction(NeowBoss owner, int act) {
        this.owner = owner;
        num = act;
    }

    @Override
    public void update() {

        this.isDone = true;
            switch (num) {
                case 1: {
                    if (owner.bossesRezzed.size() >= 1) {
                        switch (owner.bossesRezzed.get(0)) {
                            case "downfall:Ironclad": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new Syncronize(owner)));
                                break;
                            }
                            case "downfall:Silent": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new BagOfKnives(owner)));
                                break;
                            }
                            case "downfall:Defect": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new EnergyThief(owner)));
                                break;
                            }
                            case "downfall:Watcher": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new UnbridledRage(owner)));
                                break;
                            }
                        }
                    }
                    break;
                }

                case 2: {
                    if (owner.bossesRezzed.size() >= 2) {
                        switch (owner.bossesRezzed.get(1)) {
                            case "downfall:Ironclad": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new FeedingFrenzy(owner)));
                                break;
                            }
                            case "downfall:Silent": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SeeingDouble(owner)));
                                //TODO - Figure out how to make this work.  Or come up with a different plan.
                                //TODO - Current plan: 50% chance for the first attack used each turn to fail.
                                break;
                            }
                            case "downfall:Defect": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new AncientConstruct(owner, 3)));
                                break;
                            }
                            case "downfall:Watcher": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new BlasphemersDemise(owner, 150)));
                                break;
                            }
                        }
                    }
                    break;
                }


                case 3: {
                    if (owner.bossesRezzed.size() >= 3) {
                        switch (owner.bossesRezzed.get(2)) {
                            case "downfall:Ironclad": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new Bastion(owner, 10)));
                                break;
                            }
                            case "downfall:Silent": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new HighlyToxic(owner, 10)));
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PoisonProtectionPower(AbstractDungeon.player)));

                                break;
                            }
                            case "downfall:Defect": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new UnbiasedCognition(owner)));
                                int num = 0;
                                for (AbstractCard c:AbstractDungeon.actionManager.cardsPlayedThisCombat){
                                    if (c.type == AbstractCard.CardType.POWER){
                                        num++;
                                    }
                                }
                                if (num > 0){
                                    addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, num), num));
                                }
                                break;
                            }
                            case "downfall:Watcher": {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new FleetingFaith(owner)));
                                break;
                            }
                        }
                    }
                }
            }
        for (int i = 0; i < 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }

    }
}