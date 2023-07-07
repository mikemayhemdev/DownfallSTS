package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class ShadowDaggers extends AbstractCollectorCard {
    public final static String ID = makeID(ShadowDaggers.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 4, 2, , , , 

    public ShadowDaggers() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 4;
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * bonus();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * bonus();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    private static int bonus() {
        return (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> CardModifierManager.hasModifier(q, CollectedCardMod.ID)).count();
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}