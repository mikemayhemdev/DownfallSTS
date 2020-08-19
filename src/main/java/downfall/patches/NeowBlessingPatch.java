package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.trials.CustomTrial;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import downfall.dailymods.ExchangeController;
import downfall.relics.NeowBlessing;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import slimebound.SlimeboundMod;

import java.util.ArrayList;


public class NeowBlessingPatch {
    @SpirePatch(
            clz = CustomModeScreen.class,
            method = "addNonDailyMods"
    )
    public static class CustomModeScreenPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CustomModeScreen _instance,CustomTrial trial, ArrayList<String> modIds) {
            for (String modId : modIds) {
                if(modId.equals(ExchangeController.ID)){
                    trial.setShouldKeepStarterRelic(false);
                }
            }

            return SpireReturn.Continue();
        }
    }


}