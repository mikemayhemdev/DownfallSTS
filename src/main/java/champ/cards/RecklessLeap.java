package champ.cards;

import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class RecklessLeap extends AbstractChampCard {

    public final static String ID = makeID("RecklessLeap");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 15;
    private static final int UPG_MAGIC = 5;

    public RecklessLeap() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        myHpLossCost = MAGIC;
     //   tags.add(ChampMod.TECHNIQUE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) ||AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID))) {
            cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        fatigue(magicNumber);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
        myHpLossCost = magicNumber;
    }
}