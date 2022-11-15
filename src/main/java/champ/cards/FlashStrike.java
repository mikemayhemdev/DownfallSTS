package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.gladiatorOpen;
import static champ.ChampMod.loadJokeCardImage;

public class FlashStrike extends AbstractChampCard {

    public final static String ID = makeID("FlashStrike");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;


    public FlashStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERGLADIATOR);
        tags.add(CardTags.STRIKE);
       
        loadJokeCardImage(this, "FlashCut.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        gladiatorOpen();

    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}