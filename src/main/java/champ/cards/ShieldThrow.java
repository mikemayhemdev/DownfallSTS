package champ.cards;

import champ.ChampMod;
import champ.powers.NoBlockNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;

public class ShieldThrow extends AbstractChampCard {

    public final static String ID = makeID("ShieldThrow");

    //stupid intellij stuff attack, enemy, rare

    public ShieldThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
      //  tags.add(ChampMod.FINISHER);
        baseBlock = block = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        blck();
        this.baseDamage = p.currentBlock + block;
        this.calculateCardDamage(m);
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
        if (!dcombo()) {
            applyToSelf(new NoBlockNextTurnPower(1));
        }
      //  finisher();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.currentBlock;
        super.applyPowers();
        this.baseDamage = this.baseDamage + block;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBlock(4);
    }
}