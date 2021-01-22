package champ.cards;

import champ.ChampMod;
import champ.stances.AbstractChampStance;
import champ.stances.BerserkerStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class RecklessLeap extends AbstractChampCard {

    public final static String ID = makeID("RecklessLeap");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public RecklessLeap() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        //myHpLossCost = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(CardTags.STRIKE);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        for (int i = 0; i < magicNumber; i++) {
            if (AbstractDungeon.player.stance instanceof AbstractChampStance)
                ((AbstractChampStance) AbstractDungeon.player.stance).techique();
            //addToTop(new ApplyPowerAction(p, p, new CounterPower(boom), boom));
        }
        fatigue(magicNumber);
        techique();
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
       // myHpLossCost = magicNumber;
    }
}