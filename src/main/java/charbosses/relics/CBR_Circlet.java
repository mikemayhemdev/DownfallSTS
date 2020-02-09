package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_Circlet extends AbstractCharbossRelic
{

    public CBR_Circlet() {
        super(new Circlet());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Circlet();
    }
}
