package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.loadJokeCardImage;

public class ShieldWall extends AbstractChampCard {

    public final static String ID = makeID("ShieldWall");

    public ShieldWall() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 10;
        baseMagicNumber = magicNumber = 2;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

        //TODO - new Power: For each hit fully Blocked this turn, gain 2 Vigor.
    }


    public void upp() {
        upgradeBlock(3);
    }
}