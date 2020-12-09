package champ.cards;

import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DeathBlow extends AbstractChampCard {

    public final static String ID = makeID("DeathBlow");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = -1;
    private static final int MAGIC = 20;
    private static final int UPG_MAGIC = 5;

    public DeathBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        baseDamage = fatigue(magicNumber);
        calculateCardDamage(null);
        allDmg(AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void applyPowers() {
        baseDamage = Math.min(magicNumber, AbstractDungeon.player.currentHealth - 1);
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 73
        this.initializeDescription();// 74
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);// 86
        this.rawDescription = cardStrings.DESCRIPTION;// 87
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];// 88
        this.initializeDescription();// 89
    }

    @Override
    public void onMoveToDiscard() {

    }

    //TODO: same thing, damage displayer here (additionally, hp cost display also needs dynamic updating at the same time as the damage one)

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}