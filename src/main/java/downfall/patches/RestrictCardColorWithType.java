package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import hermit.characters.hermit;

import static downfall.patches.RestrictCardColor.vanillaColors;

@SpirePatch(clz = CardLibrary.class, method = "getAnyColorCard", paramtypez = {AbstractCard.CardType.class, AbstractCard.CardRarity.class})
public class RestrictCardColorWithType {

  @SpireInsertPatch(rloc = 11, localvars = {"anyCard"})
  public static void Insert(AbstractCard.CardType type, AbstractCard.CardRarity rarity, CardGroup anyCard) {
    CardGroup cardsToRemove = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    for (AbstractCard card : anyCard.group) {
      boolean available = false;
      if (card.color == AbstractDungeon.player.getCardColor()) {
        available = true;
      } else {
        for (AbstractCard.CardColor color : vanillaColors) {
          if (card.color == color) {
            available = true;
            break;
          } 
        } 
      } 
      if (!available)
        cardsToRemove.addToBottom(card); 
    } 
    for (AbstractCard card : cardsToRemove.group)
      anyCard.removeCard(card); 
  }
}
