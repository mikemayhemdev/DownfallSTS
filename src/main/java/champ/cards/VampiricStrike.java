package champ.cards;

import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class VampiricStrike extends AbstractChampCard {

    public final static String ID = makeID("VampiricStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 6;

    public VampiricStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
       // tags.add(ChampMod.COMBO);
      //  tags.add(ChampMod.COMBOBERSERKER);
      //  tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(CardTags.STRIKE);
        postInit();
        loadJokeCardImage(this, "VampiricStrike.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new WallopAction(m, makeInfo()));
    }

    /*
    @Override
    public void triggerOnGlowCheck() {
        glowColor = (dcombo() || bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

     */

    public void upp() {
        upgradeDamage(3);
    }
}