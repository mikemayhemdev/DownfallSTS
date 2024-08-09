//package downfall.patches;
//
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.potions.BlockPotion;
//import com.megacrit.cardcrawl.potions.SmokeBomb;
//import com.megacrit.cardcrawl.vfx.ThoughtBubble;
//import downfall.monsters.NeowBoss;
//
//import java.util.Objects;
//
//@SpirePatch(        //TODO Finish this
//        clz= SmokeBomb.class,
//        method="canUse"
//)
//public class WhetherCanEscapeGauntletPatch {
//    // allow the player to escape gauntlet when there's only a single hero left, otherwise no
//    public static void Prefix(SmokeBomb __instance)
//    {
//        BlockPotion fp = new BlockPotion();
//        if(fp.canUse()) { // only check when you can actually use a potion
//            boolean has_helper_neow = false;
//            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                if (Objects.equals(m.id, NeowBoss.ID)) {
//                    has_helper_neow = true;
//                }
//            }
//            if (has_helper_neow) {
//                int count_hero = 0;
//                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                    if (!Objects.equals(m.id, NeowBoss.ID)) {
//                        count_hero++;
//                    }
//                }
//                if (count_hero >= 2) {
//                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, CardCrawlGame.languagePack.getUIString("downfall:CantEscapeGauntlet").TEXT[0], true));
//                    SpireReturn.Return(false);
//                } else {
//                    SpireReturn.Return(true);
//                }
//            } else {
//                SpireReturn.Continue();
//            }
//        }else{
//            SpireReturn.Continue();
//        }
//    }
//}
