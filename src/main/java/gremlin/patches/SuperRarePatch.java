package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.GremlinMod;

@SpirePatch(
        clz= AbstractDungeon.class,
        method="getCard",
        paramtypez={AbstractCard.CardRarity.class}
)
public class SuperRarePatch {
    public static AbstractCard Postfix(AbstractCard __result, AbstractCard.CardRarity rarity){
        if(__result instanceof SuperRare) {
            GremlinMod.logger.debug("Super Rare card.");
            if(AbstractDungeon.cardRandomRng.randomBoolean()){
                GremlinMod.logger.debug("Bad luck, no Super Rare.");
                return AbstractDungeon.getCard(rarity);
            }
        }
        return __result;
    }
}
