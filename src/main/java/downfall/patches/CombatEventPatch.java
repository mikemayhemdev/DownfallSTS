package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import downfall.downfallMod;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;

@SpirePatch(clz = ProceedButton.class, method = "update")

public class CombatEventPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(Instanceof i) throws CannotCompileException {
                try {
                    if (i.getType().getName().equals(Mushrooms.class.getName())) {
                        i.replace("$_ = $proceed($$) || " +
                                "currentRoom.event instanceof downfall.events.CombatEvent;");
                    }
                } catch (NotFoundException e) {
                    downfallMod.logger.error("Combat proceed button patch broken.", e);
                }
            }
        };
    }
}