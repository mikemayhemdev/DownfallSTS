package awakenedOne.cards;

import awakenedOne.cardmods.FlyingModifier;
import awakenedOne.cards.tokens.Feather;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class Bryds extends AbstractAwakenedCard {
    public final static String ID = makeID(Bryds.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 1, , , , 5, 1

    public Bryds() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 1;
        baseMagicNumber = magicNumber = 5;
        CardModifierManager.addModifier(this, new FlyingModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_BYRD_ATTACK_" + MathUtils.random(0, 5)));
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(q -> q.cardID.equals(Feather.ID)).count();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(q -> q.cardID.equals(Feather.ID)).count();
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * count;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}