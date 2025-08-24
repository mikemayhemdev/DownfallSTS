package downfall.patches;

import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import hermit.characters.hermit;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;

@SpirePatch(clz = CardLibrary.class, method = "getAnyColorCard", paramtypez = {AbstractCard.CardRarity.class})
public class RestrictCardColor {
    //  Instead of banning non Hero cards like it did previously,
    //  this bans cards that are additionally banned for Snecko. I was very harsh with banning useless
    //  cards from Snecko so it should be fine-ish to play with.
    //  If this still makes prismatic shard bad we can just revert it to the old version again.

    //public static AbstractCard.CardColor[] vanillaColors = new AbstractCard.CardColor[]{AbstractCard.CardColor.RED, AbstractCard.CardColor.GREEN, AbstractCard.CardColor.BLUE, AbstractCard.CardColor.PURPLE, AbstractCard.CardColor.COLORLESS, AbstractCard.CardColor.CURSE, hermit.Enums.COLOR_YELLOW};
    @SpireInsertPatch(rloc = 10, localvars = {"anyCard"})
    public static void Insert(AbstractCard.CardRarity rarity, CardGroup anyCard) {
        CardGroup cardsToRemove = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard card : anyCard.group) {
            boolean available = false;
            if (card.color == AbstractDungeon.player.getCardColor()) {
                available = true;
            }
            else if (!card.hasTag(SneckoMod.BANNEDFORSNECKO) && !card.hasTag(HexaMod.GHOSTWHEELCARD)) {
                available = true;
            }

            //PLEASE WORK
            if (card.hasTag(HexaMod.GHOSTWHEELCARD) && !(AbstractDungeon.player instanceof TheHexaghost)){
                available = false;
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
