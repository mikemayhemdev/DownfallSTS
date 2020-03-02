package charbosses.patches;


import charbosses.bosses.AbstractCharBoss;
import charbosses.relics.AbstractCharbossRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

@SpirePatch(clz = AbstractCreature.class, method = "brokeBlock")

public class BrokeBlockPatch {

    @SpirePrefixPatch
    public static void Prefix(AbstractCreature instance) {

        if (instance instanceof AbstractPlayer) {
            if (AbstractDungeon.getMonsters().monsters.size() > 0){
                if (AbstractDungeon.getMonsters().monsters.get(0) instanceof AbstractCharBoss) {
                    AbstractCharBoss cB = (AbstractCharBoss)AbstractDungeon.getMonsters().monsters.get(0);

                    for (AbstractCharbossRelic abstractCharbossRelic : cB.relics) {
                        AbstractRelic r = (AbstractRelic) abstractCharbossRelic;
                        r.onBlockBroken(instance);
                    }
                }
            }

        }
    }
}

