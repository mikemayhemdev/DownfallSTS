package champ.cards;

import champ.ChampMod;
import champ.actions.DevastateAction;
import champ.actions.IncreaseMiscDamageAction;
import champ.stances.GladiatorStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import sneckomod.SneckoMod;

import java.util.Iterator;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 24;

    public Devastate() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);


        this.misc = DAMAGE;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = this.misc;
        tags.add(ChampMod.FINISHER);
        exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DevastateAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber, this.uuid));
        finisher();
    }

    public void applyPowers() {
        baseDamage = misc;
        super.applyPowers();
        //genPreview();
        initializeDescription();
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}