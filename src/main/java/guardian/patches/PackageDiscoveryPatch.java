package guardian.patches;


import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import guardian.GuardianMod;
import guardian.cards.*;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(clz=CardRewardScreen.class, method = "discoveryOpen", paramtypez = {})
public class PackageDiscoveryPatch {


    @SpireInsertPatch(locator = Locator.class, localvars = {"derp"})
    public static void Insert(CardRewardScreen __instance, ArrayList<AbstractCard> derp){
        GuardianMod.logger.info("Discovery patch hit");


        if (GuardianMod.discoveryOverride) {
            GuardianMod.discoveryOverride = false;
            GuardianMod.logger.info("Discovery patch override hit");
            derp.clear();

            ArrayList startingList = new ArrayList();
            ArrayList discoveryList = new ArrayList();

            int rando;
            AbstractCard tmp;

            startingList.add("SHAPES");
            startingList.add("SENTRY");
            startingList.add("SPHERE");
            startingList.add("DONUDECA");
            startingList.add("AUTOMATON");
            startingList.add("WALKER");
            startingList.add("DEFECT");
            if (Loader.isModLoaded("constructmod")) startingList.add("CONSTRUCT");
            if (Loader.isModLoaded("infinitespire")) startingList.add("MASS");

            for (int i = 0; i < 3; i++) {
                rando = AbstractDungeon.cardRng.random(0, startingList.size() - 1);
                discoveryList.add(startingList.get(rando));
                startingList.remove(rando);
            }

            for (int i = 0; i < 3; i++) {
                switch ((String) discoveryList.get(i)) {
                    case "SHAPES":
                        tmp = new PackageShapes();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into SHAPES");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "SENTRY":
                        tmp = new PackageSentry();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into SENTRY");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "SPHERE":
                        tmp = new PackageSphere();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into SPHERE");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "DONUDECA":
                        tmp = new PackageDonuDeca();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into DONUDECA");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "AUTOMATON":
                        tmp = new PackageAutomaton();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into AUTOMATON");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "WALKER":
                        tmp = new PackageWalker();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into WALKER");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "DEFECT":
                        tmp = new PackageDefect();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into DEFECT");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "CONSTRUCT":
                        tmp = new PackageConstruct();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into CONSTRUCT");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                    case "MASS":
                        tmp = new PackageMass();
                        if (GuardianMod.discoveryOverrideUpgrade) tmp.upgrade();
                        GuardianMod.logger.info("randomed into MASS");
                        derp.add(tmp.makeStatEquivalentCopy());
                        break;
                }
            }
                GuardianMod.discoveryOverrideUpgrade = false;

                GuardianMod.logger.info(derp.get(0));
                GuardianMod.logger.info(derp.get(1));
                GuardianMod.logger.info(derp.get(2));
                GuardianMod.logger.info(derp.size());

                __instance.rewardGroup = derp;

            GuardianMod.logger.info(__instance.rewardGroup.get(0));
            GuardianMod.logger.info(__instance.rewardGroup.get(1));
            GuardianMod.logger.info(__instance.rewardGroup.get(2));
            GuardianMod.logger.info(__instance.rewardGroup.size());
            }
        
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardRewardScreen.class, "rewardGroup");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

}