package champ.cards;

import basemod.helpers.VfxBuilder;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;

public class CrownThrowReturn extends AbstractChampCard {

    public final static String ID = makeID("CrownThrowReturn");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    public CrownThrowReturn() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.COMBO);
        isEthereal = true;
        exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        combo();

    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}

