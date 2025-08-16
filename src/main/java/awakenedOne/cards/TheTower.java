package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.patches.OnCreateCardSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class TheTower extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(TheTower.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public TheTower() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 4;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
        loadJokeCardImage(this, makeBetaCardPath(TheTower.class.getSimpleName() + ".png"));
    }

    public static int countCards() {
        return OnCreateCardSubscriber.CardsCreatedThisCombat;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while (var2.hasNext()) {
            AbstractMonster mo = (AbstractMonster) var2.next();
            if (!mo.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new ExplosionSmallEffect(mo.hb.cX, mo.hb.cY), 0.1F));
            }
        }

        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }


    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}