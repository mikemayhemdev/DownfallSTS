package collector.cards;

import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class MysteryWeaving extends AbstractCollectorCard {
    public final static String ID = makeID(MysteryWeaving.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 10, 3, , 

    public MysteryWeaving() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        int modifier = (int) AbstractDungeon.player.hand.group.stream().filter(c -> c != this).count() * 2;
        baseBlock -= modifier;
        super.applyPowersToBlock();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }


    public void upp() {
        upgradeBlock(3);
    }
}