package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Endure extends AbstractChampCard {

    public final static String ID = makeID("Endure");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 7;

    public Endure() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        blck();
    }

    public void applyPowersToBlock()
    {
        int realBaseBlock = this.baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        super.applyPowersToBlock();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        int olddamage = this.baseDamage;
        if (AbstractDungeon.player.hasPower("Dexterity")) {
            this.baseDamage += AbstractDungeon.player.getPower("Dexterity").amount * this.magicNumber;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = olddamage;
        this.isDamageModified = (this.baseDamage != this.damage);
    }

    public void upp() {
        upgradeBlock(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}