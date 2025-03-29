package downfall.patches;

import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;

@SpirePatch(clz = CardLibrary.class, method = "getAnyColorCard", paramtypez = {AbstractCard.CardType.class, AbstractCard.CardRarity.class})
public class RestrictCardColorWithType {

  // Instead of banning non Hero cards like it did previously,
  //  this bans cards that are additionally banned for Snecko. I was very harsh with banning useless
  //  cards from Snecko so it should be fine-ish to play with.
  //  If this still makes prismatic shard bad we can just revert it to the old version again.

  @SpireInsertPatch(rloc = 11, localvars = {"anyCard"})
  public static void Insert(AbstractCard.CardType type, AbstractCard.CardRarity rarity, CardGroup anyCard) {
    CardGroup cardsToRemove = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    for (AbstractCard card : anyCard.group) {
      boolean available = false;
      if (card.color == AbstractDungeon.player.getCardColor()) {
        available = true;
      }
      else if (!card.hasTag(SneckoMod.BANNEDFORSNECKO) && !card.hasTag(HexaMod.GHOSTWHEELCARD)) {
        available = true;
      }

      if (card.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE){
        available = false;
      }

      if (!available) {
        cardsToRemove.addToBottom(card);
      }
    }
    for (AbstractCard card : cardsToRemove.group) {
      anyCard.removeCard(card);
    }
  }
}
