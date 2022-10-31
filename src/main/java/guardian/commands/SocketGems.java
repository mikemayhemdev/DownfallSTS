package guardian.commands;

import basemod.devcommands.ConsoleCommand;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

public class SocketGems extends ConsoleCommand {
    // Fills all empty sockets with random gems, for testing purposes
    @Override
    protected void execute(String[] strings, int i) {
        socketGems(AbstractDungeon.player.hand);
        socketGems(AbstractDungeon.player.drawPile);
        socketGems(AbstractDungeon.player.discardPile);

    }

    private void socketGems(CardGroup group) {
        for (AbstractCard card : group.group) {
            if(card instanceof AbstractGuardianCard) {
                AbstractGuardianCard gc = (AbstractGuardianCard) card;
                gc.sockets.clear();
                while(gc.socketCount > gc.sockets.size()) {
                    gc.sockets.add(GuardianMod.socketTypes.values()[MathUtils.random(GuardianMod.socketTypes.values().length-1)]);
                }
                gc.updateDescription();
            }
        }
    }
}
