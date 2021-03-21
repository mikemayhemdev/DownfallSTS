package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.ghostflames.MayhemGhostflame;
import theHexaghost.powers.AgainPower;

@SpirePatch(
        clz = CardGroup.class,
        method = "moveToExhaustPile"
)
public class ExhaustCardTickPatch {
    public static boolean exhaustedLastTurn = false;
    public static boolean exhaustedThisTurn = false;
    public static void Prefix(CardGroup __instance, AbstractCard c) {
        exhaustedThisTurn = true;
    }
}