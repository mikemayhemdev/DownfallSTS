package awakenedOne.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.function.Predicate;

public class JediUtil {
    public static ArrayList<AbstractCard> cardsCreatedThisTurn = new ArrayList<>();
    public static ArrayList<AbstractCard> cardsCreatedThisCombat = new ArrayList<>();

    public static void onGenerateCardMidcombat(AbstractCard c) {
        AbstractDungeon.player.relics.stream().filter(r -> r instanceof onGenerateCardMidcombatInterface).forEach(r -> ((onGenerateCardMidcombatInterface) r).onCreateCard(c));
        AbstractDungeon.player.powers.stream().filter(r -> r instanceof onGenerateCardMidcombatInterface).forEach(r -> ((onGenerateCardMidcombatInterface) r).onCreateCard(c));
        AbstractDungeon.player.hand.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface) card).onCreateCard(c));
        AbstractDungeon.player.discardPile.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface) card).onCreateCard(c));
        AbstractDungeon.player.drawPile.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface) card).onCreateCard(c));
        AbstractDungeon.getMonsters().monsters.stream().filter(mon -> !mon.isDeadOrEscaped()).forEach(m -> m.powers.stream().filter(pow -> pow instanceof onGenerateCardMidcombatInterface).forEach(pow -> ((onGenerateCardMidcombatInterface) pow).onCreateCard(c)));
        if (c instanceof onGenerateCardMidcombatInterface) {
            ((onGenerateCardMidcombatInterface) c).onCreateThisCard();
        }
        JediUtil.cardsCreatedThisCombat.add(c);
        JediUtil.cardsCreatedThisTurn.add(c);
    }
}
