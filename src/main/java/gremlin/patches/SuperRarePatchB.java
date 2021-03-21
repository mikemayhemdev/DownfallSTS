package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import gremlin.GremlinMod;

@SpirePatch(
        clz= AbstractDungeon.class,
        method="getCard",
        paramtypez={AbstractCard.CardRarity.class,
                com.megacrit.cardcrawl.random.Random.class}
)
public class SuperRarePatchB {
    public static AbstractCard Postfix(AbstractCard __result, AbstractCard.CardRarity rarity, Random rng){
        if(__result instanceof SuperRare) {
            GremlinMod.logger.debug("Super Rare card.");
            if(rng.randomBoolean()){
                GremlinMod.logger.debug("Bad luck, no Super Rare.");
                return AbstractDungeon.getCard(rarity, rng);
            }
        }
        return __result;
    }
}
