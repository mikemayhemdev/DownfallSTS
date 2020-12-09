package charbosses.patches;


import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.orbs.AbstractEnemyOrb;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class ApplyFocusrPatch {
    @SpirePatch(
            clz = RemoveSpecificPowerAction.class,
            method = "update"
    )
    public static class ApplyFocusrPatchA {
        @SpireInsertPatch(rloc = 24)
        public static SpireReturn<Void> Insert(RemoveSpecificPowerAction instance) {
            if (AbstractCharBoss.boss != null) {
                for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
                    o.updateDescription();
                }
            }

            return SpireReturn.Continue();

        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "onModifyPower"
    )
    public static class ApplyFocusrPatchB {
        @SpirePrefixPatch
        public static SpireReturn Prefix() {
            if (AbstractCharBoss.boss != null) {
                for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
                    o.updateDescription();
                }
            }

            return SpireReturn.Continue();

        }
    }


}



