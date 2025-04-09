package collector.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.BlurPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class VoidArmor extends AbstractCollectorCard {
    public final static String ID = makeID(VoidArmor.class.getSimpleName());
    // intellij stuff skill, all, uncommon, , , 13, 3, , 

    public VoidArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseBlock = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new BlurPower(p, 1));
        forAllMonstersLiving(q -> atb(new GainBlockAction(q, block)));
        forAllMonstersLiving(q -> applyToEnemy(q, new BlurPower(q, 1)));
    }

    public void upp() {
        upgradeBlock(3);
    }

    @SpirePatch(clz= MonsterGroup.class, method="applyPreTurnLogic")
    public static class MakeEnemiesUseBlurPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall methodCall) throws CannotCompileException {
                    if (methodCall.getClassName().equals(AbstractMonster.class.getName()) && methodCall.getMethodName().equals("hasPower"))
                        methodCall.replace("$_ = ($proceed($$) || $proceed(\"" + BlurPower.POWER_ID + "\"));");
                }
            };
        }
    }
}