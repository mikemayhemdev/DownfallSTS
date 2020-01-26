package slimebound.actions;


import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.orbs.HexSlime;


public class CheckForSixHexAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractPlayer p;

    public CheckForSixHexAction(AbstractPlayer p) {
        this.p = p;


    }


    public void update() {

        int hexslimecount = 0;
        AbstractOrb[] hexesToAbsorb = new AbstractOrb[10];
        for (AbstractOrb o : AbstractDungeon.player.orbs) {

            if (o instanceof HexSlime) {
                hexesToAbsorb[hexslimecount] = o;
                hexslimecount++;


            }
        }
        if (hexslimecount > 5) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            for (AbstractOrb o : hexesToAbsorb) {

                    AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o));



            }
        }


        this.isDone = true;
    }

}



