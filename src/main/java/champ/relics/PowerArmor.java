package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.powers.CounterPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;
import static hermit.util.Wiz.pwrAmt;

public class PowerArmor extends CustomRelic implements OnReceivePowerRelic {

    public static final String ID = ChampMod.makeID("PowerArmor");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PowerArmor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PowerArmor.png"));

    //Power Armor

    //Vigor / Counter Cap

    public static final int CAP_RESOLVE_ETC = 10;

    //debugging variable
    public static int REMOVED = 0;

    public PowerArmor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public boolean onReceivePower(AbstractPower var1, AbstractCreature var2) {
        if (var1.ID == VigorPower.POWER_ID || var1.ID == CounterPower.POWER_ID) {
            if (pwrAmt(AbstractDungeon.player, var1.ID) >= CAP_RESOLVE_ETC) {
                if (var1.amount > 0) {
                    onTrigger(var1.amount);
                }
                var1.amount = 0;
            }

            return var1.amount != 0;
        }

        return true;
    }

    public void onEquip() {
        REMOVED = 0;
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    //this should NEVER happen outside of Wreath of Flame or cross-mod stuff, but it's been a bug for centuries and
    //I'm tired of seeing the bug reports so yeah
    //I also want to catch any non vigor() applications inside this mod even though I think I just caught all of them
    //including smoking barrel and gremlin wizard collectible
    //also in case someone adds more by accident
    //I have NO idea why trying to make it work via receivepowerrelic doesn't work either but it doesn't, trust me it doesn't work
    //writing a patch for applypoweraction is also annoying

    //edit: I bit the bullet and decided to write a patch.

    //NukeVigorPatch

    //public void onTrigger(int amount) {
    //        if (amount > 0) {
    //                if (amount + pwrAmt(AbstractDungeon.player, VigorPower.POWER_ID) > CAP_RESOLVE_ETC) {
    //                    flash();
    //                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    //                    REMOVED = REMOVED + (CAP_RESOLVE_ETC - pwrAmt(AbstractDungeon.player, VigorPower.POWER_ID));
    //                    //System.out.println("DEBUG: REMOVED: " + REMOVED);
    //                    addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, VigorPower.POWER_ID, - (CAP_RESOLVE_ETC - pwrAmt(AbstractDungeon.player, VigorPower.POWER_ID))));
    //            }
    //        }
    //    }
    //
    //    //this should literally never happen.
    //    public void onTrigger2(int amount) {
    //        if (amount > 0) {
    //            if (amount + pwrAmt(AbstractDungeon.player, CounterPower.POWER_ID) > CAP_RESOLVE_ETC) {
    //                flash();
    //                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    //                REMOVED = REMOVED + (CAP_RESOLVE_ETC - pwrAmt(AbstractDungeon.player, CounterPower.POWER_ID));
    //                //System.out.println("DEBUG: REMOVED: " + REMOVED);
    //                addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, CounterPower.POWER_ID, -(CAP_RESOLVE_ETC - pwrAmt(AbstractDungeon.player, CounterPower.POWER_ID))));
    //            }
    //        }
    //    }

    public void onTrigger (int amount) {
        flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        REMOVED = REMOVED + amount;
        //this.counter = REMOVED;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CAP_RESOLVE_ETC + DESCRIPTIONS[1];
    }

}
