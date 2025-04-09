package champ.cards;

import champ.ChampMod;
import champ.powers.NoBlockNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static champ.ChampMod.loadJokeCardImage;

public class ShieldThrow extends AbstractChampCard {

    public final static String ID = makeID("ShieldThrow");

    //stupid intellij stuff attack, enemy, rare

    public ShieldThrow() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
     //   baseBlock = block = 0;
        baseMagicNumber = magicNumber = 2;
    //    tags.add(ChampMod.FINISHER);
        postInit();
        loadJokeCardImage(this, "ShieldThrow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // blck();
        this.baseDamage = p.currentBlock;
        this.calculateCardDamage(m);
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();

       // if (!dcombo()) applyToSelf(new FrailPower(p,2, false));
       // if (bcombo()) atb(new ReducePowerAction(p,p,FrailPower.POWER_ID,2));
      //  finisher();
    }

    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.currentBlock;
        super.applyPowers();
        //this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = AbstractDungeon.player.currentBlock;
        super.calculateCardDamage(mo);
        //this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}