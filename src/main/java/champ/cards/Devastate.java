package champ.cards;

import champ.ChampMod;
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
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOGLADIATOR);
        tags.add(ChampMod.COMBODEFENSIVE);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (!(p.stance instanceof NeutralStance)) {
            atb(new IncreaseMiscDamageAction(this.uuid, this.misc, this.magicNumber));
            exhaust = true;
        }
        finisher();
    }

    public void applyPowers() {
        baseDamage = misc;
        super.applyPowers();
        //applyPowers();
        initializeDescription();
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}