package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Feint extends AbstractChampCard {

    public final static String ID = makeID("Feint");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Feint() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        blck();
        if (bcombo()) {
            if (p.hasPower(StrengthPower.POWER_ID)) {
                int x = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount * magicNumber;
                baseBlock = x;
                applyPowers();
                blck();
            }
        }
    }

    //TODO: Do the same thing with the extended description to show block gained from str after modifiers

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}