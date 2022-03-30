package downfall.actions;


import charbosses.cards.other.Antidote;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import downfall.monsters.NeowBossFinal;
import downfall.powers.neowpowers.*;

public class NeowGainMinionPowersAction extends AbstractGameAction {
    private NeowBossFinal owner;
    private int num;

    public NeowGainMinionPowersAction(NeowBossFinal owner, int act) {
        this.owner = owner;
        num = act;
    }

    @Override
    public void update() {

        this.isDone = true;

        switch (num) {
            case 1: {
                switch (downfallMod.Act1BossFaced) {
                    case "downfall:Ironclad": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new Syncronize(owner)));
                        break;
                    }
                    case "downfall:Silent": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new HighlyToxic(owner, 5)));
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Antidote(), 2, true, true));
//                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PoisonProtectionPower(AbstractDungeon.player)));
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
                    default:
                    case "downfall:Hermit": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new Distracting(owner, 1)));
                        break;
                    }
                }
                break;
            }

            case 2: {
                switch (downfallMod.Act2BossFaced) {
                    case "downfall:Ironclad": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new FeedingFrenzy(owner)));
                        break;
                    }
                    case "downfall:Silent": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SeeingDouble(owner)));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SeeingDoubleProduct(owner)));

                        break;
                    }
                    case "downfall:Defect": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new AncientConstruct(owner, 2)));
                        break;
                    }
                    case "downfall:Watcher": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new BlasphemersDemise(owner, 150)));
                        break;
                    }
                    default:
                    case "downfall:Hermit": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new WheelOfDeath(owner, 13)));
                        break;
                    }
                }
                break;
            }


            case 3: {
                switch (downfallMod.Act3BossFaced) {
                    case "downfall:Ironclad": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new Bastion(owner, 10)));
                        break;
                    }
                    case "downfall:Silent": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new BagOfKnives(owner)));
                        break;
                    }
                    case "downfall:Defect": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new UnbiasedCognition(owner)));

                        break;
                    }
                    case "downfall:Watcher": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new FleetingFaith(owner)));
                        break;
                    }
                    default:
                    case "downfall:Hermit": {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new Eclipse(owner)));
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }

    }
}