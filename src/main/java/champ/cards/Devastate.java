package champ.cards;

import champ.ChampMod;
import champ.stances.GladiatorStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public Devastate() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = ChampMod.finishersThisCombat;
        //finisher();
        for (int i = 0; i < x; i++) dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        //finisher();
    }

    public void applyPowers() {
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + ChampMod.finishersThisCombat;
        if (ChampMod.finishersThisCombat == 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}