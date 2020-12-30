package collector;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CollectorCollection implements OnStartBattleSubscriber , PostBattleSubscriber {
    public static CardGroup Collection;
    public static CardGroup BattleCollection;
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        BattleCollection = Collection;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        BattleCollection.clear();
    }
}
