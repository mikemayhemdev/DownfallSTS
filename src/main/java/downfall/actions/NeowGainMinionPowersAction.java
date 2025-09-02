package downfall.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import downfall.powers.neowpowers.TrueNeowPower;
import slimebound.SlimeboundMod;

public class NeowGainMinionPowersAction extends AbstractGameAction {
    private AbstractMonster owner;
    private int num;

    public NeowGainMinionPowersAction(AbstractMonster owner, int act) {
        this.owner = owner;
        num = act;
    }

    public boolean checkBossFaced(String id){
        if (downfallMod.Act1BossFaced.equals(id)) return true;
        if (downfallMod.Act2BossFaced.equals(id)) return true;
        if (downfallMod.Act3BossFaced.equals(id)) return true;
        return false;

    }

    @Override
    public void update() {

        this.isDone = true;
       // SlimeboundMod.logger.info(downfallMod.Act1BossFaced);
       // SlimeboundMod.logger.info(downfallMod.Act2BossFaced);
       // SlimeboundMod.logger.info(downfallMod.Act3BossFaced);
       // SlimeboundMod.logger.info("downfall:Ironclad " + checkBossFaced("downfall:Ironclad"));
      //  SlimeboundMod.logger.info("downfall:Silent "+ checkBossFaced("downfall:Silent"));
      //  SlimeboundMod.logger.info("downfall:Defect "+ checkBossFaced("downfall:Defect"));
       // SlimeboundMod.logger.info("downfall:Watcher "+ checkBossFaced("downfall:Watcher"));
       // SlimeboundMod.logger.info("downfall:Hermit "+ checkBossFaced("downfall:Hermit"));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new TrueNeowPower(owner,
                checkBossFaced("downfall:Ironclad"),
                checkBossFaced("downfall:Silent"),
                checkBossFaced("downfall:Defect"),
                checkBossFaced("downfall:Watcher"),
                checkBossFaced("downfall:Hermit")
        )));

        /*
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

         */
        for (int i = 0; i < 3; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }

    }
}