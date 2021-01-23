package champ.cards;

import champ.ChampMod;
import champ.vfx.ExecuteEffect;
import champ.vfx.StanceDanceEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;

import static champ.ChampMod.fatigue;

public class Execute extends AbstractChampCard {

    public final static String ID = makeID("Execute");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 6;
    //private static final int UPG_DAMAGE = 2;
    private static final int HP_LOSS = 10;

    public Execute() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = HP_LOSS;
        baseCool = cool = 2;
        myHpLossCost = magicNumber;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        AbstractDungeon.player.useJumpAnimation();
        atb(new VFXAction(new GoldenSlashEffect(m.hb.cX + 30.0F * Settings.scale, m.hb.cY, true), 0.1F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        atb(new VFXAction(new GoldenSlashEffect(m.hb.cX - 30.0F * Settings.scale, m.hb.cY, true), 0.1F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        fatigue(magicNumber);
        finisher();
    }

    public void upp() {
        upgradeDamage(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}