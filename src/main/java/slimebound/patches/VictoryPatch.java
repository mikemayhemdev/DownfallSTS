
        package slimebound.patches;

        import basemod.BaseMod;
        import basemod.ReflectionHacks;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.math.MathUtils;
        import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.events.AbstractEvent;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.cutscenes.Cutscene;
        import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
        import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
        import com.megacrit.cardcrawl.helpers.ImageMaster;
        import com.megacrit.cardcrawl.screens.VictoryScreen;
        import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
        import javassist.CannotCompileException;
        import javassist.expr.ExprEditor;
        import javassist.expr.MethodCall;

        import java.util.ArrayList;
/*
public class VictoryPatch {
    @SpirePatch(clz = Cutscene.class, method = SpirePatch.CONSTRUCTOR)
    public static class addHeartPanels {
        public static void Postfix(Cutscene self, PlayerClass chosenClass) {
            if (chosenClass == SlimeboundEnum.SLIMEBOUND) {
                ArrayList<CutscenePanel> panels = (ArrayList<CutscenePanel>) ReflectionHacks.getPrivate(self, Cutscene.class, "panels");
                panels.clear();

                ReflectionHacks.setPrivate(self, Cutscene.class, "bgImg", ImageMaster.loadImage("images/scenes/greenBg.jpg"));

                panels.add(new CutscenePanel("slimeboundResources/SlimeboundImages/scenes/slimebound1.png", "VO_SLIMEBOSS_1A"));
                panels.add(new CutscenePanel("slimeboundResources/SlimeboundImages/scenes/slimebound2.png"));
                panels.add(new CutscenePanel("slimeboundResources/SlimeboundImages/scenes/slimebound3.png"));


            }
        }
    }
}
*/

