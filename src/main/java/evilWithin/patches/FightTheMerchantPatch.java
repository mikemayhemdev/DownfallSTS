package evilWithin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import evilWithin.monsters.FleeingMerchant;
import evilWithin.util.Invisimerchant;

@SpirePatch(
        clz = ShopRoom.class,
        method = "onPlayerEntry"
)

public class FightTheMerchantPatch {
    public static void Prefix(ShopRoom __instance) {
        if (EvilModeCharacterSelect.evilMode)
            if (!FleeingMerchant.DEAD)
                startCombat();
            else
                ((ShopRoom) AbstractDungeon.getCurrRoom()).merchant = new Invisimerchant();
    }

    private static void startCombat() {
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
        AbstractDungeon.lastCombatMetricKey = FleeingMerchant.ID;
        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new FleeingMerchant());

        AbstractDungeon.getCurrRoom().rewards.clear();

        AbstractDungeon.getCurrRoom().monsters.init();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            m.usePreBattleAction();
            m.useUniversalPreBattleAction();
        }
        ((ShopRoom) AbstractDungeon.getCurrRoom()).merchant = new Invisimerchant();
        AbstractRoom.waitTimer = 0.1f;
        AbstractDungeon.player.preBattlePrep();
    }
}
