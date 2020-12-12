package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlashCut extends AbstractChampCard {

    public final static String ID = makeID("FlashCut");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 8;
    private static final int UPG_MAGIC = 3;

    public FlashCut() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.OPENER);
        tags.add(CardTags.STRIKE);
        this.tags.add(ChampMod.OPENERGLADIATOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        gladOpen();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (dcombo()) applyToSelf(new CounterPower(magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}