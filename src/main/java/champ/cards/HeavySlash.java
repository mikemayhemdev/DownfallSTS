package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HeavySlash extends AbstractChampCard {

    public final static String ID = makeID("HeavySlash");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 20;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public HeavySlash() {
        super(ID, 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        finisher();
    }

    public int upgradeAmount() {
        int x = 0;
        for (AbstractCard q : AbstractDungeon.player.masterDeck.group) {
            if (q.upgraded) x++;
        }
        return x;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * upgradeAmount();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * upgradeAmount();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}