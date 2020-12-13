package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.util.OnAdvanceOrRetractSubscriber;

public class TimeWarp extends AbstractHexaCard implements OnAdvanceOrRetractSubscriber {

    public final static String ID = makeID("TimeWarp");

    //stupid intellij stuff ATTACK, ALL_ENEMY, COMMON

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public TimeWarp() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.LIGHTNING);
    }

    @Override
    public void onAdvanceOrRetract(boolean endTurn) {
        if (!endTurn)
            this.addToBot(new DiscardToHandAction(this));// 40
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}