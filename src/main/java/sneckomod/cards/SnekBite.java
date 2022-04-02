package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.actions.MuddleRandomCardAction;

public class SnekBite extends AbstractSneckoCard {

    public final static String ID = makeID("SnekBite");

    public SnekBite() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        atb(new MuddleRandomCardAction(magicNumber, true));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}