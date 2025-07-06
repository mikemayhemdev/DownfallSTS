package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static champ.ChampMod.loadJokeCardImage;

public class Backstep extends AbstractChampCard {

    public final static String ID = makeID("Backstep");

    public Backstep() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
       // tags.add(ChampMod.OPENER);

        baseBlock = 7;
        //this.tags.add(ChampMod.OPENERDEFENSIVE);
        //tags.add(ChampMod.COMBO);
        //tags.add(ChampMod.COMBOBERSERKER);
        postInit();
        loadJokeCardImage(this, "Backstep.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
      //  defenseOpen();
        blck();
    }

    @Override
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
            baseBlock += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
        }
        super.applyPowersToBlock();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = block != baseBlock;
    }

    public void upp() {
        upgradeBlock(3);
    }
}