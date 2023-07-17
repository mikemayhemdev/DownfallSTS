package collector.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class CollectorBottleField {
    public static SpireField<Boolean> inCollectionBottle = new SpireField<>(() -> false);
    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            inCollectionBottle.set(result, inCollectionBottle.get(self)); // Read:
            return result;
        }
    }

}