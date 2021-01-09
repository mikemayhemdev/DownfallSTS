package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import slimebound.SlimeboundMod;

@SpirePatch(clz = ProceedButton.class, method = "update")

public class CombatEventPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(Instanceof i) throws CannotCompileException {
                try {
                    if (i.getType().getName().equals(Mushrooms.class.getName())) {
                        i.replace("$_ = $proceed($$) || " +
                                "currentRoom.event instanceof downfall.events.WomanInBlue_Evil || " +
                             //   "currentRoom.event instanceof guardian.events.GemMine || " +
                                "currentRoom.event instanceof downfall.events.GremlinMatchGame_Evil || " +
                                "currentRoom.event instanceof downfall.events.GremlinWheelGame_Evil || " +
                                "currentRoom.event instanceof downfall.events.LivingWall_Evil || " +
                                "currentRoom.event instanceof downfall.events.Augmenter_Evil || " +
                                "currentRoom.event instanceof downfall.events.FaceTrader_Evil || " +
                                "currentRoom.event instanceof downfall.events.Beggar_Evil || " +
                                "currentRoom.event instanceof downfall.events.Colosseum_Evil || " +
                                "currentRoom.event instanceof champ.events.Colosseum_Evil_Champ || " +
                                "currentRoom.event instanceof champ.events.MinorLeagueArena || " +
                                "currentRoom.event instanceof downfall.events.MindBloom_Evil || " +
                                "currentRoom.event instanceof automaton.events.ShapeFactory || " +
                                "currentRoom.event instanceof automaton.events.AncientFactory;");
                    }
                } catch (NotFoundException e) {
                    SlimeboundMod.logger.error("Combat proceed button patch broken.", e);
                }
            }
        };
    }
}