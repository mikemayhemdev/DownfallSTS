package evilWithin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import evilWithin.EvilWithinMod;
import evilWithin.events.WomanInBlue_Evil;
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
                                "currentRoom.event instanceof evilWithin.events.WomanInBlue_Evil || " +
                                "currentRoom.event instanceof evilWithin.events.GremlinMatchGame_Evil || " +
                                "currentRoom.event instanceof evilWithin.events.GremlinWheelGame_Evil || " +
                                "currentRoom.event instanceof evilWithin.events.LivingWall_Evil || " +
                                "currentRoom.event instanceof evilWithin.events.Augmenter_Evil || " +
                                "currentRoom.event instanceof evilWithin.events.FaceTrader_Evil || " +
                                "currentRoom.event instanceof evilWithin.events.Beggar_Evil;");
                    }
                } catch (NotFoundException e) {
                    SlimeboundMod.logger.error("Combat proceed button patch broken.", e);
                }
            }
        };
    }
}