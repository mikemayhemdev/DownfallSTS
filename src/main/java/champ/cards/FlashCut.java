package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlashCut extends AbstractChampCard {

    public final static String ID = makeID("FlashCut");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public FlashCut() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.OPENER);
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (dcombo()) applyToSelf(new CounterPower(magicNumber));
        if (dcombo()) blck();
        defenseOpen();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
        upgradeBlock(UPG_BLOCK);
    }
}