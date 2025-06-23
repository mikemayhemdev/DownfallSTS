package awakenedOne.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class BirdsEye extends AbstractAwakenedCard {
    public final static String ID = makeID(BirdsEye.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public BirdsEye() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        atb(new FetchAction(AbstractDungeon.player.drawPile, c -> (c.type == CardType.POWER)));
    }

    public void upp() {
        this.exhaust = false;
    }
}