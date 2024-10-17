package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;

public class TailWhip extends AbstractSneckoCard {

    public final static String ID = makeID("TailWhip");

    //stupid intellij stuff ATTACK, ENEMY, BASIC

    private static final int DAMAGE = 10;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public TailWhip() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "TailWhip.png");
        this.tags.add(SneckoMod.RNG);
        this.tags.add(SneckoMod.OVERFLOW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        int x = getRandomNum(magicNumber, 2, this);
        // this is probably bad practice but it works
        if (isOverflowActive()) {
            x = 2;
        }
        if (x > 0)
            applyToEnemy(m, autoWeak(m, x));
        int y = getRandomNum(magicNumber, 2, this);
        if (isOverflowActive()){
            y = 2;
        }
        if (y > 0)
            applyToEnemy(m, autoVuln(m, y));
        // atb(new MuddleHandAction());
    }

    //Glowverflow - make the card glow if overflow is active~
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isOverflowActive()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}